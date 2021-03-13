package kakaopay.service;

import kakaopay.entity.Invest;
import kakaopay.entity.InvestParamter;

import java.util.List;

public interface InvestService {

    public String create(long X_USER_ID , InvestParamter investParamter) throws Exception;

    public List<Invest> get(long xUserId) throws Exception;


}
