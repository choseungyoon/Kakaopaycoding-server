package kakaopay.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="product")
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY, optional = true)
    private Stock stock;

    @Column(nullable = false,name = "started_at")
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(nullable = false)
    private boolean deletedYn = false;

    public static Product of(final String title, final Stock stock , final LocalDateTime startedAt,
                             final LocalDateTime finishedAt){
        Product product = new Product();
        product.title = title;
        product.stock = stock;
        product.startedAt = startedAt;
        product.finishedAt = finishedAt;
        return product;
    }
}
