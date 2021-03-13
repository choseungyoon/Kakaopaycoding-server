package kakaopay.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class InvestParamter {

    @NotNull
    private long productId;

    @NotNull
    @Min(1)
    private long investAmount;

}
