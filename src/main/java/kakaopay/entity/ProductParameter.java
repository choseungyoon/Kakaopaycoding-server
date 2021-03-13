package kakaopay.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ProductParameter {
    @NotBlank
    private String title;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    @Valid @NotNull
    private StockParameter stock;
}
