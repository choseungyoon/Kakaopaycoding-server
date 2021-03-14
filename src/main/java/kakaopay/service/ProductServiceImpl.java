package kakaopay.service;

import kakaopay.entity.*;
import kakaopay.repository.ProductRepository;
import kakaopay.repository.StockRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StockRedisRepository stockRedisRepository;

    public ProductServiceImpl (ProductRepository productRepository , StockRedisRepository stockRedisRepository){
        this.productRepository = productRepository;
        this.stockRedisRepository = stockRedisRepository;
    }

    @Transactional
    @Override
    public Product create(ProductParameter productParameter) throws Exception{
        Product newProduct = Product.of(
                productParameter.getTitle(),
                toStock(productParameter.getStock()),
                productParameter.getStartedAt(),
                productParameter.getFinishedAt());

        Product returnValue = productRepository.save(newProduct);

        RedisStock redisStock = new RedisStock();
        redisStock.setId(returnValue.getId());
        redisStock.setRemain(returnValue.getStock().getRemain());
        redisStock.setInvesters(0);
        this.stockRedisRepository.save(redisStock);

        return returnValue;
    }

    @Override
    public JSONArray get() {

        JSONArray jsonArray = new JSONArray();

        List<Product> products =  productRepository.findByStartedAtBeforeAndFinishedAtAfter(LocalDateTime.now(),LocalDateTime.now());

        if(products.size() == 0){
            throw new RuntimeException("No Such Product!");
        }

        for (Product product:
             products) {

            JSONObject jsonObject = new JSONObject();
            Stock stock = product.getStock();
            Long pid = product.getId();
            Optional<RedisStock> getStock= this.stockRedisRepository.findById(pid.toString());

            if(getStock.isPresent()) {
                RedisStock redisStock = getStock.get();
                stock.setRemain(stock.getTotal()-redisStock.getRemain());
                stock.setInvestors(redisStock.getInvesters());
                product.setStock(stock);
            }

            jsonObject.put("product_id",pid);
            jsonObject.put("title",product.getTitle());
            jsonObject.put("total_investing_amount" , product.getStock().getTotal());
            jsonObject.put("current_investing_amount",product.getStock().getRemain());
            jsonObject.put("investers",product.getStock().getInvestors());
            jsonObject.put("status", product.getStock().getRemain() >=  product.getStock().getTotal()  || LocalDateTime.now().isAfter(product.getFinishedAt()) ? "모집 완료" : "모집중");
            jsonObject.put("duration", product.getStartedAt() + " ~ " + product.getFinishedAt());
            jsonArray.add(jsonObject);
        }

        return jsonArray;

    }

    private Stock toStock(final StockParameter stockParameter){
        if(isNull(stockParameter)) return null;
        return Stock.of(stockParameter.getTotal());
    }


}
