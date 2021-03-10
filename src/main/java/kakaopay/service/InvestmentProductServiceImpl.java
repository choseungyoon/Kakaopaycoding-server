package kakaopay.service;

import kakaopay.entity.InvestmentProductEntity;
import kakaopay.repository.InvestmentProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class InvestmentProductServiceImpl implements  InvestmentProductService{

    @Autowired
    private InvestmentProductRepository investmentProductRepository;

    @Override
    public List<InvestmentProductEntity> getInverstmentProduct(String started_at, String finished_at) throws  Exception {
        log.debug("select product : getInverstmentProduct Service");

        LocalDateTime from = LocalDateTime.parse(started_at, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime to = LocalDateTime.parse(finished_at, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return investmentProductRepository.findByStartedAtAfterAndFinishedAtBefore(from,to);
    }

    @Override
    public String saveInvestmentProduct(InvestmentProductEntity investmentProductEntity) throws Exception{
        log.debug("Insert product : Service");
        investmentProductRepository.save(investmentProductEntity);
        return "success";
    }


}
