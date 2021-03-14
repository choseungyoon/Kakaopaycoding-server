package kakaopay.repository;

import kakaopay.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByStartedAtBeforeAndFinishedAtAfter(LocalDateTime form,LocalDateTime to);

    Product findById(long Id);

}
