package kakaopay.controller;

import kakaopay.entity.Invest;
import kakaopay.entity.InvestParamter;
import kakaopay.service.InvestService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class InvestController {

    private InvestService investService;

    public InvestController (InvestService investService){
        this.investService = investService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/invest")
    public JSONArray get(@RequestHeader(value = "X_USER_ID") long X_USER_ID) throws Exception{
        return investService.get(X_USER_ID);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/invest")
    public Invest create(@RequestHeader(value = "X_USER_ID") long X_USER_ID ,
                            @RequestBody InvestParamter investParamter) throws Exception{
        return investService.create(X_USER_ID,investParamter);
    }
}
