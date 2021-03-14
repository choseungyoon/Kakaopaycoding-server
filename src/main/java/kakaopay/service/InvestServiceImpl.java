package kakaopay.service;

import kakaopay.entity.Invest;
import kakaopay.entity.InvestParamter;
import kakaopay.entity.RedisStock;
import kakaopay.repository.InvestRepository;
import kakaopay.repository.StockRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(propagation = Propagation.NESTED)
public class InvestServiceImpl implements InvestService {

    private final StockRedisRepository stockRedisRepository;
    private final InvestRepository investRepository;

    public InvestServiceImpl (StockRedisRepository stockRedisRepository, InvestRepository investRepository){
        this.stockRedisRepository = stockRedisRepository;
        this.investRepository = investRepository;
    }

    @Override
    @Transactional
    public JSONObject create(long X_USER_ID , InvestParamter investParamter) throws Exception{

        JSONObject jsonObject = new JSONObject();

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
                    redisStock.setInvesters(redisStock.getInvesters()+1);
                }
                else{
                    investObject.setInvestAmount(investObject.getInvestAmount() + investParamter.getInvestAmount());
                }
                //Update redis stock remain
                redisStock.setRemain(redisStock.getRemain()-investParamter.getInvestAmount());
                this.stockRedisRepository.save(redisStock);
                this.investRepository.save(investObject);
                jsonObject.put("result" , "SUCCESS");
                return jsonObject;
            }
            else{
                investObject = new Invest();
                investObject.setUserId(X_USER_ID);
                investObject.setInvestAmount(investParamter.getInvestAmount());
                investObject.setProductId(investParamter.getProductId());
                investObject.setInvest_at(LocalDateTime.now());
                jsonObject.put("result" , "SOLD OUT");
                return jsonObject;
            }
        }
        else{
            return null;
        }
    }

    @Override
    public JSONArray get(long xUserId) {
        JSONArray jsonArray = new JSONArray();
        List<Invest> invests = this.investRepository.findByUserId(xUserId);
        for (Invest item:
             invests) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId" , item.getProductId());
            jsonObject.put("title" , item.getProduct().getTitle());
            jsonObject.put(("Total_investing_amount"), item.getProduct().getStock().getTotal());
            jsonObject.put("My_investing_amount",item.getInvestAmount());
            jsonObject.put("InvestAt",item.getInvest_at());
            jsonArray.add(jsonObject);
        }

        if(invests.size() == 0){
            throw new RuntimeException("No Such User!");
        }
        return jsonArray;
    }
}
