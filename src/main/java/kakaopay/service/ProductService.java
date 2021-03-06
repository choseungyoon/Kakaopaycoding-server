package kakaopay.service;

import kakaopay.entity.Product;
import kakaopay.entity.ProductParameter;
import org.json.simple.JSONArray;

public interface ProductService {

    //Create
    Product create(ProductParameter productParameter) throws Exception;

    //Read
    JSONArray get() throws  Exception;

}
