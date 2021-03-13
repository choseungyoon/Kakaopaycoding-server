package kakaopay.controller;

import kakaopay.entity.Invest;
import kakaopay.entity.InvestParamter;
import kakaopay.service.InvestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class InvestController {

    private InvestService investService;

    public InvestController (InvestService investService){
        this.investService = investService;
    }

    @GetMapping("/invest")
    public List<Invest> get(@RequestHeader(value = "X_USER_ID") long X_USER_ID) throws Exception{
        return investService.get(X_USER_ID);
    }

    @PostMapping("/invest")
    public String create(@RequestHeader(value = "X_USER_ID") long X_USER_ID ,
                            @RequestBody InvestParamter investParamter) throws Exception{
        return investService.create(X_USER_ID,investParamter);
    }
}
