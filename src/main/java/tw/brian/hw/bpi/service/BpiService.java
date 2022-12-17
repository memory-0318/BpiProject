package tw.brian.hw.bpi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tw.brian.hw.bpi.dto.in.BpiInputDTO;
import tw.brian.hw.bpi.dto.in.SingleBpiInCurrencyDTO;
import tw.brian.hw.bpi.dto.out.BpiResponseDTO;
import tw.brian.hw.bpi.dto.out.SingleBpiCurrencyMappingDTO;
import tw.brian.hw.bpi.exception.JsonParsingException;
import tw.brian.hw.bpi.fetcher.BpiFetcher;
import tw.brian.hw.bpi.fetcher.CoindeskBpiFetcher;
import tw.brian.hw.bpi.formatter.OutputDateTimeFormatter;
import tw.brian.hw.currency_mapping.dto.out.CurrencyMappingOutputDTO;
import tw.brian.hw.currency_mapping.service.CurrencyMappingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Service
public class BpiService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyMappingService currencyMappingService;
    @Autowired
    private OutputDateTimeFormatter outputDateTimeFormatter;
    @Value("${coindesk.url}")
    private String coindeskApiUrl;

    public BpiResponseDTO getCurrentBpi() {
        BpiFetcher fetcher = new CoindeskBpiFetcher(this.coindeskApiUrl, this.restTemplate);
        BpiInputDTO bpiDTO = this.parseBpiResponse(fetcher.fetchCurrentPrice());

        Map<String, SingleBpiInCurrencyDTO> bpiCurrencyDTOMap = bpiDTO.getBpi();
        Map<String, SingleBpiCurrencyMappingDTO> output = new HashMap<>(bpiCurrencyDTOMap.size());

        List<CurrencyMappingOutputDTO> currencyMappingResponses = this.currencyMappingService.listAllCurrencyMappings();
        Map<String, CurrencyMappingOutputDTO> codeToCurrencyMappingMap = currencyMappingResponses.stream()
            .collect(Collectors.toMap(CurrencyMappingOutputDTO::getCurrencyCode, Function.identity()));

        for (Map.Entry<String, SingleBpiInCurrencyDTO> entry : bpiCurrencyDTOMap.entrySet()) {
            SingleBpiInCurrencyDTO source = entry.getValue();
            String currencyNameInZhTw = "";
            if (codeToCurrencyMappingMap.containsKey(entry.getKey())) {
                currencyNameInZhTw = codeToCurrencyMappingMap.get(entry.getKey()).getCurrencyNameInZhTw();
            }

            output.put(
                entry.getKey(),
                SingleBpiCurrencyMappingDTO.builder()
                    .setCode(source.getCode())
                    .setCurrencyNameInZhTw(currencyNameInZhTw)
                    .setRateFloat(source.getRateFloat())
                    .build()
            );
        }

        return BpiResponseDTO.builder()
            .setUpdatedDateTime(
                this.outputDateTimeFormatter.formatDateTimeWithTimeZone(bpiDTO.getTime().getUpdatedISO()))
            .setBpi(output)
            .build();
    }

    private BpiInputDTO parseBpiResponse(String responseStr) {
        try {
            return this.objectMapper.readValue(responseStr, BpiInputDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(
                "Exception occurred when parsing json-formatted response of bitcoind index price, probably api spec has changed",
                e);
        }
    }
}
