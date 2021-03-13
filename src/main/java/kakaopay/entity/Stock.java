package kakaopay.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@EqualsAndHashCode(of="id",callSuper = false)
@Entity
@Data
public final class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long total;

    @NotNull
    @Column(nullable = false)
    private Long remain;

    @NotNull
    @Column(nullable = false)
    private int investors;

    Stock() {}

    public static Stock of(final long total){

        final Stock stock = new Stock();
        stock.total = total;
        stock.remain = total;
        stock.investors = 0;
        return stock;
    }

    public final boolean invest(final long value){
        if(this.remain < value) return false;

        this.remain -= value;
        this.investors +=1;
        return true;
    }

    public final void increase(final long value){

        final long nextTotal =total + value;
        final long nextRemain = remain + value;
        final int nextInvesters = investors + 1;

        this.total = nextTotal;
        this.remain = nextRemain;
        this.investors = nextInvesters + 1;

    }

    public final void decrease(final long value){

        final long nextTotal =total - value;
        final long nextRemain = remain - value;
        final int nextInvesters = investors - 1;


        this.total = nextTotal;
        this.remain = nextRemain;
        this.investors = nextInvesters < 0 ? 0 : nextInvesters;

    }

    public final void syncCurrent(final long value) {
        this.remain = value;
    }

}
