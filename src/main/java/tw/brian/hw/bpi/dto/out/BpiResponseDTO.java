package tw.brian.hw.bpi.dto.out;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Data
@Builder(setterPrefix = "set")
public class BpiResponseDTO {
    private String updatedDateTime;
    private Map<String, SingleBpiCurrencyMappingDTO> bpi;
}
