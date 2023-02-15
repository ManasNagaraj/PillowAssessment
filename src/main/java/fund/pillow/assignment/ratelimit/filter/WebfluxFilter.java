package fund.pillow.assignment.ratelimit.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fund.pillow.assignment.ratelimit.exception.ApiError;
import fund.pillow.assignment.ratelimit.exception.PillowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class WebfluxFilter implements WebFilter {

    private final ReactiveRedisTemplate<String, Long> redisTemplate;

    @Value("${rate.limit.time.window}")
    private static final Long TIME_WINDOW = 60L;

    @Value("${rate.limit.max.request}")
    private static final Long MAX_REQUEST = 10L;

    //Token bucket algorithm implementation to limit the transaction per second although it
    //it is not a complete implementation with variable rate base on the and idk if this is correct
    private final TBLimiter tbLimiter = new TBLimiter(1000.0);

    public WebfluxFilter(ReactiveRedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getMinuteKey(String clientIp) {
        LocalDateTime now = LocalDateTime.now();
        return String.format("rate_limiter:%s:%d:%d", clientIp, now.getHour(), now.getMinute());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String clientIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        String key = getMinuteKey(clientIp);

        Mono<Long> increment = redisTemplate.opsForValue().increment(key);
        Mono<Boolean> expire = redisTemplate.expire(key, Duration.ofSeconds(TIME_WINDOW));

        return increment.zipWith(expire).flatMap(tuple -> {
            Long count = tuple.getT1();
            if (count >= MAX_REQUEST) {
                return Mono.error(new PillowException("PE23", "Too many request", HttpStatus.TOO_MANY_REQUESTS));
            }
            tbLimiter.acquire();
            exchange.getRequest().mutate().header("r_count", count.toString()).build();
            return chain.filter(exchange);
        }).onErrorResume(e -> {
            ServerHttpResponse response = exchange.getResponse();
            if (e instanceof PillowException pillowException) {
                response.setStatusCode(pillowException.getHttpStatus());
                ApiError error = ApiError.builder().code(pillowException.getCode()).message(pillowException.getMessage()).build();
                try {
                    response.getHeaders().add("X-WAIT-LIMIT", ""+(tbLimiter.timeWait()==0?TIME_WINDOW:tbLimiter.timeWait()));
                    response.getHeaders().add("X-MAX-LIMIT", MAX_REQUEST.toString());
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return response.writeWith(Mono.just(new DefaultDataBufferFactory().wrap(new ObjectMapper().writeValueAsBytes(error))));
                } catch (JsonProcessingException ex) {
                //fall through
                }
            }
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.setComplete();
        });

    }
}