package kakaopay.repository;

import kakaopay.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByStartedAtAfterAndFinishedAtBefore(LocalDateTime started_at, LocalDateTime finished_at);

    Product findById(long Id);

}
