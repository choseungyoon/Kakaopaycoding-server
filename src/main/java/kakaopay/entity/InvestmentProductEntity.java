package kakaopay.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="investmentProduct")
@NoArgsConstructor
@Data
public class InvestmentProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long product_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)

    private long total_investing_amount;

    @Column(nullable = false)
    private long current_investing_amount = 0;

    @Column(nullable = false)
    private int number_of_investor = 0;

    @Column(nullable = false,name = "started_at")
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(nullable = false)
    private boolean deletedYn = false;
}
