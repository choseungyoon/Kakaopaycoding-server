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



}
