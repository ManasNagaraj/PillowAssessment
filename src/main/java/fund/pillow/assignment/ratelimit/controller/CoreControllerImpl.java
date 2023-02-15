package fund.pillow.assignment.ratelimit.controller;


import fund.pillow.assignment.ratelimit.model.RateResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CoreControllerImpl implements CoreController {
    @Override
    public Mono<RateResponseModel> getRequestCount(String test) {
        RateResponseModel rateResponseModel = new RateResponseModel();
        rateResponseModel.setRequestCount(test);
        return Mono.just(rateResponseModel);
    }

    @Override
    public Mono<RateResponseModel> getRequestCountTest() {
        return null;
    }
}
