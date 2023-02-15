package fund.pillow.assignment.ratelimit;


import fund.pillow.assignment.ratelimit.model.RateResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableWebFlux
@ComponentScan(basePackages = {"fund.pillow.assignment.ratelimit.**"})
@EntityScan(basePackages = {"fund.pillow.assignment.ratelimit.**"})
public class RatelimitApplication {

//    @Autowired
//    private ReactiveRedisTemplate<String, Long> redisTemplate;
//
//    @Bean
//    RouterFunction<ServerResponse> routes() {
//        RateResponseModel model = new RateResponseModel();
//        model.setRequestCount(1000);
//
//
//        return route() //
//                .GET("/fund/pillow/count/test" + "", request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(model))).filter(new WebfluxFilter(redisTemplate)).build();
//    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(RatelimitApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
        springApplication.run(args);


    }

}
