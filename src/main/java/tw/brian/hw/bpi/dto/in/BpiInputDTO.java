package tw.brian.hw.bpi.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Data
public class BpiInputDTO {
    public static final String FIELD_TIME = "time";
    public static final String FIELD_DISCLAIMER = "disclaimer";
    public static final String FIELD_CHART_NAME = "chartName";
    public static final String FIELD_BPI = "bpi";

    private UpdatedTimeDTO time;
    private String disclaimer;
    private String chartName;
    private Map<String, SingleBpiInCurrencyDTO> bpi;

    @JsonProperty(FIELD_TIME)
    private void unpackTimeField(Map<String, String> timeMap) {
        this.time = UpdatedTimeDTO.builder()
            .setUpdated(timeMap.getOrDefault(UpdatedTimeDTO.FIELD_UPDATED, null))
            .setUpdatedISO(timeMap.getOrDefault(UpdatedTimeDTO.FIELD_UPDATED_ISO, null))
            .setUpdateUK(timeMap.getOrDefault(UpdatedTimeDTO.FIELD_UPDATED_UK, null))
            .build();
    }

    @JsonProperty(FIELD_BPI)
    private void unpackBpiField(Map<String, Map<String, String>> bpiMap) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        // TODO: need check the parsing format
        DecimalFormat format = new DecimalFormat("#0.0#");
        format.setParseBigDecimal(true);

        this.bpi = bpiMap.values().stream()
            .map(currencyMap -> {

                // TODO: need to wrap the exception to self-defined exception
                try {
                    return SingleBpiInCurrencyDTO.builder()
                        .setCode(currencyMap.getOrDefault(SingleBpiInCurrencyDTO.FIELD_CODE, null))
                        .setSymbol(currencyMap.getOrDefault(SingleBpiInCurrencyDTO.FIELD_SYMBOL, null))
                        .setRate(currencyMap.getOrDefault(SingleBpiInCurrencyDTO.FIELD_RATE, null))
                        .setDescription(currencyMap.getOrDefault(SingleBpiInCurrencyDTO.FIELD_DESCRIPTION, null))
                        .setRateFloat((BigDecimal) format.parse(currencyMap.get(SingleBpiInCurrencyDTO.FIELD_RATE_FLOAT)))
                        .build();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toMap(SingleBpiInCurrencyDTO::getCode, bpiCurrencyDTO -> bpiCurrencyDTO));
    }
}
