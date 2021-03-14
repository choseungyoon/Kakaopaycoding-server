package kakaopay.service;

import kakaopay.entity.InvestParamter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface InvestService {

    public JSONObject create(long X_USER_ID , InvestParamter investParamter) throws Exception;

    public JSONArray get(long xUserId) throws Exception;


}
