package tw.brian.hw.currency_mapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import tw.brian.hw.currency_mapping.dto.in.CurrencyMappingInputDTO;
import tw.brian.hw.currency_mapping.dto.out.CurrencyMappingOutputDTO;
import tw.brian.hw.currency_mapping.service.CurrencyMappingService;
import tw.brian.hw.general.model.ResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@RestController
@RequestMapping(value = CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL)
public class CurrencyMappingController {
    public static final String CURRENCY_MAPPING_REQUEST_URL = "/currency_mapping";

    @Autowired
    private CurrencyMappingService currencyMappingService;

    @PostMapping
    public ResponseDTO<CurrencyMappingOutputDTO> addCurrencyMapping(@RequestBody CurrencyMappingInputDTO inputDTO) {
        return ResponseDTO.createSuccessResponse(this.currencyMappingService.addCurrencyMapping(inputDTO));
    }

    @GetMapping("/{id}")
    public ResponseDTO<CurrencyMappingOutputDTO> getCurrencyMapping(@PathVariable("id") String hashId) {
        return ResponseDTO.createSuccessResponse(this.currencyMappingService.getCurrencyMapping(hashId));
    }

    @GetMapping
    public ResponseDTO<List<CurrencyMappingOutputDTO>> listAllCurrencyMappings() {
        return ResponseDTO.createSuccessResponse(this.currencyMappingService.listAllCurrencyMappings());
    }

    @PutMapping(value = "/{id}")
    public ResponseDTO<CurrencyMappingOutputDTO> updateCurrencyMapping(
        @PathVariable("id") String hashId,
        @RequestBody CurrencyMappingInputDTO inputDTO) {
        return ResponseDTO.createSuccessResponse(this.currencyMappingService.updateCurrencyMapping(hashId, inputDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<CurrencyMappingOutputDTO> removeCurrencyMapping(@PathVariable("id") String hashId) {
        return ResponseDTO.createSuccessResponse(this.currencyMappingService.removeCurrencyMapping(hashId));
    }
}
