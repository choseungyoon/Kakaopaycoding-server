package kakaopay.service;

import kakaopay.entity.InvestmentProductEntity;

import java.util.List;

public interface InvestmentProductService {

    //Create
    String saveInvestmentProduct(InvestmentProductEntity investmentProductEntity) throws Exception;

    //Read
    List<InvestmentProductEntity> getInverstmentProduct(String started_at, String finished_at) throws  Exception;

    //Update

    //Delete

}
