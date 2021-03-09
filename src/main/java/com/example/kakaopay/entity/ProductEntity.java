package com.example.kakaopay.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String product_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private long total_investing_amount;

    @Column(nullable = false)
    private long current_investing_amount = 0;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int number_of_investor;

    @Column(nullable = false)
    private LocalDateTime started_at = LocalDateTime.now();

    private LocalDateTime finished_at;
}
