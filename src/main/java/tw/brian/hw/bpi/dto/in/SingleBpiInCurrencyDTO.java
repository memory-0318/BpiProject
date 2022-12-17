package tw.brian.hw.bpi.dto.in;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Data
@Builder(setterPrefix = "set")
public class SingleBpiInCurrencyDTO {
    public static final String FIELD_CODE = "code";
    public static final String FIELD_SYMBOL = "symbol";
    public static final String FIELD_RATE = "rate";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_RATE_FLOAT = "rate_float";

    private String code;
    private String symbol;
    private String rate;
    private String description;
    private BigDecimal rateFloat;
}
