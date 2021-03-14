package kakaopay.controller;

import kakaopay.entity.InvestParamter;
import kakaopay.service.InvestService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/invest")
    public JSONObject create(@RequestHeader(value = "X_USER_ID") long X_USER_ID ,
                             @RequestBody InvestParamter investParamter) throws Exception{
        return investService.create(X_USER_ID,investParamter);
    }
}
