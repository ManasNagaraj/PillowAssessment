package fund.pillow.assignment.ratelimit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;


@Data
public class RateResponseModel implements ServerResponse {



    @JsonProperty(value = "request_count")
    private String requestCount;


    @Override
    public HttpStatusCode statusCode() {
        return null;
    }

    @Override
    public int rawStatusCode() {
        return 0;
    }

    @Override
    public HttpHeaders headers() {
        return null;
    }

    @Override
    public MultiValueMap<String, ResponseCookie> cookies() {
        return null;
    }

    @Override
    public Mono<Void> writeTo(ServerWebExchange exchange, Context context) {
        return null;
    }
}
