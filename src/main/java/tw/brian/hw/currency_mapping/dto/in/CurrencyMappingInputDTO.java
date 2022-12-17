package tw.brian.hw.currency_mapping.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class CurrencyMappingInputDTO {
    private String currencyCode;
    private String currencyNameInZhTw;
}
