package kakaopay.repository;

import kakaopay.entity.Invest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvestRepository extends CrudRepository<Invest,Integer> {

    Invest findByProductIdAndUserId(long productId, long xUserId);

    List<Invest> findByUserId(long xUserId);

}
