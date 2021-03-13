package kakaopay.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Data
@RedisHash("stock")
public class RedisStock {

    @Id
    @Indexed
    private Long id;

    private Long remain;

    private int investers;

    public final boolean invest(final long value){
        if(this.remain < value) return false;

        this.remain -= value;
        this.investers +=1;
        return true;
    }

}
