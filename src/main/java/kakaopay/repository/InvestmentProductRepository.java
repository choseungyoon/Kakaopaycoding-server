package kakaopay.repository;

import kakaopay.entity.InvestmentProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InvestmentProductRepository extends CrudRepository<InvestmentProductEntity, Integer> {

    List<InvestmentProductEntity> findByStartedAtAfterAndFinishedAtBefore(LocalDateTime started_at,LocalDateTime finished_at);
}
