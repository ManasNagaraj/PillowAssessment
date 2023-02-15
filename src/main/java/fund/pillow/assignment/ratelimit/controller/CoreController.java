package fund.pillow.assignment.ratelimit.controller;


import fund.pillow.assignment.ratelimit.model.RateResponseModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping(path = {"/fund/pillow"})
public interface CoreController {
    @GetMapping(path = {"/count"}, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<RateResponseModel> getRequestCount(@RequestHeader("r_count") String test);

    @GetMapping(path = {"/count/test"}, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<RateResponseModel> getRequestCountTest();

}
