package kakaopay.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invest")
@NoArgsConstructor
@Data
public class Invest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    private long productId;

    private long investAmount;

    private LocalDateTime invest_at;

    private String result;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "productId", insertable = false,updatable = false)
    private Product product;

    public Product getProduct(){
        return product;
    }

}
