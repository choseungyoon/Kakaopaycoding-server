package kakaopay.service;

import kakaopay.entity.Invest;
import kakaopay.entity.InvestParamter;
import org.json.simple.JSONArray;

public interface InvestService {

    public Invest create(long X_USER_ID , InvestParamter investParamter) throws Exception;

    public JSONArray get(long xUserId) throws Exception;


}
