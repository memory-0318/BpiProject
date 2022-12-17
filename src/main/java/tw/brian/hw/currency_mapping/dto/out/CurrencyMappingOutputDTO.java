package tw.brian.hw.currency_mapping.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Data
@Builder(setterPrefix = "set")
public class CurrencyMappingOutputDTO {
    @JsonProperty("id")
    private String hashId;
    private String currencyCode;
    private String currencyNameInZhTw;
}
