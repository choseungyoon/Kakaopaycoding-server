package kakaopay.service;

import kakaopay.entity.*;
import kakaopay.repository.ProductRepository;
import kakaopay.repository.StockRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Product> getInverstmentProduct(String started_at, String finished_at) throws  Exception {
        LocalDateTime from = LocalDateTime.parse(started_at, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime to = LocalDateTime.parse(finished_at, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Product> products =  productRepository.findByStartedAtAfterAndFinishedAtBefore(from,to);

        for (Product product:
             products) {
            Stock stock = product.getStock();
            Long pid = product.getId();

            Optional<RedisStock> getStock= this.stockRedisRepository.findById(pid.toString());
            if(getStock.isPresent()) {
                RedisStock redisStock = getStock.get();
                stock.setRemain(redisStock.getRemain());
                stock.setInvestors(redisStock.getInvesters());
                product.setStock(stock);
            }
        }
        return products;
    }

    private Stock toStock(final StockParameter stockParameter){
        if(isNull(stockParameter)) return null;
        return Stock.of(stockParameter.getTotal());
    }


}
