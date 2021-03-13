package kakaopay.repository;

import kakaopay.entity.RedisStock;
import org.springframework.data.repository.CrudRepository;

public interface StockRedisRepository extends CrudRepository<RedisStock, String> {
}
