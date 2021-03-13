package kakaopay.service;

import kakaopay.entity.Invest;
import kakaopay.entity.InvestParamter;
import kakaopay.entity.RedisStock;
import kakaopay.error.exception.NotExistUserInvestException;
import kakaopay.repository.InvestRepository;
import kakaopay.repository.StockRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvestServiceImpl implements InvestService {

    private final StockRedisRepository stockRedisRepository;
    private final InvestRepository investRepository;

    public InvestServiceImpl (StockRedisRepository stockRedisRepository, InvestRepository investRepository){
        this.stockRedisRepository = stockRedisRepository;
        this.investRepository = investRepository;
    }

    @Transactional
    @Override
    public Invest create(long X_USER_ID , InvestParamter investParamter) throws Exception{

        Long pid = investParamter.getProductId();

        Optional<RedisStock> getStock= this.stockRedisRepository.findById(pid.toString());
        Invest investObject = this.investRepository.findByProductIdAndUserId(investParamter.getProductId(),X_USER_ID);

        if(getStock.isPresent()){
            RedisStock redisStock = getStock.get();
            if(redisStock.getRemain() >= investParamter.getInvestAmount()){

                //Update user info
                if(investObject == null){
                    investObject = new Invest();
                    investObject.setUserId(X_USER_ID);
                    investObject.setInvestAmount(investParamter.getInvestAmount());
                    investObject.setProductId(investParamter.getProductId());
                    investObject.setInvest_at(LocalDateTime.now());
                    investObject.setResult("SUCCESS");
                    redisStock.setInvesters(redisStock.getInvesters()+1);
                }
                else{
                    investObject.setInvestAmount(investObject.getInvestAmount() + investParamter.getInvestAmount());
                }

                //Update redis stock remain
                redisStock.setRemain(redisStock.getRemain()-investParamter.getInvestAmount());
                this.stockRedisRepository.save(redisStock);

                return this.investRepository.save(investObject);

            }
            else{
                investObject = new Invest();
                investObject.setUserId(X_USER_ID);
                investObject.setInvestAmount(investParamter.getInvestAmount());
                investObject.setProductId(investParamter.getProductId());
                investObject.setInvest_at(LocalDateTime.now());
                investObject.setResult("SOLD OUT");

                return investObject;
            }
        }
        else{
            return null;
        }
    }

    @Override
    public List<Invest> get(long xUserId) {
        List<Invest> invests = this.investRepository.findByUserId(xUserId);

        if(invests.size() == 0){
            throw new NotExistUserInvestException("Not exist user invest with");
        }
        return this.investRepository.findByUserId(xUserId);
    }
}
