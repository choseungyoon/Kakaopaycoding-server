package kakaopay.service;

import kakaopay.entity.Product;
import kakaopay.entity.ProductParameter;

import java.util.List;

public interface ProductService {

    //Create
    Product create(ProductParameter productParameter) throws Exception;

    //Read
    List<Product> getInverstmentProduct(String started_at, String finished_at) throws  Exception;

    //Update

    //Delete

}
