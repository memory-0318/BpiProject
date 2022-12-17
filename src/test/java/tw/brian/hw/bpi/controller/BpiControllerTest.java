package tw.brian.hw.bpi.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tw.brian.hw.bpi.dto.out.BpiResponseDTO;
import tw.brian.hw.bpi.dto.out.SingleBpiCurrencyMappingDTO;
import tw.brian.hw.bpi.exception.BpiSourceConnectionException;
import tw.brian.hw.bpi.exception.JsonParsingException;
import tw.brian.hw.bpi.service.BpiService;
import tw.brian.hw.general.model.ResponseDTO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@WebMvcTest(BpiController.class)
class BpiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BpiService mockBpiService;

    @Test
    void testGetCurrentBpi_withSuccessResponse() throws Exception {
        SingleBpiCurrencyMappingDTO sampleBpiWithCurrencyMapping = SingleBpiCurrencyMappingDTO.builder()
            .setCode("TWD")
            .setCurrencyNameInZhTw("新台幣")
            .setRateFloat(new BigDecimal("123.123"))
            .build();
        BpiResponseDTO sampleBpiResponse = BpiResponseDTO.builder()
            .setUpdatedDateTime("1990/01/01 00:00:00")
            .setBpi(Collections.singletonMap("TWD", sampleBpiWithCurrencyMapping))
            .build();
        ;
        ResponseDTO<BpiResponseDTO> sampleResponse = ResponseDTO.createSuccessResponse(sampleBpiResponse);

        Mockito.when(this.mockBpiService.getCurrentBpi())
            .thenReturn(sampleBpiResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get(BpiController.BPI_REQUEST_PATH))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(sampleResponse.getSuccess())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.emptyString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.emptyString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.updatedDateTime",
                Matchers.is(sampleBpiResponse.getUpdatedDateTime())))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.data.bpi", Matchers.aMapWithSize(sampleBpiResponse.getBpi().size())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.bpi.TWD.code",
                Matchers.is(sampleBpiWithCurrencyMapping.getCode())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.bpi.TWD.currencyNameInZhTw",
                Matchers.is(sampleBpiWithCurrencyMapping.getCurrencyNameInZhTw())));
    }

    @ParameterizedTest
    @ArgumentsSource(BpiExceptionProvider.class)
    void testGetCurrentBpi_withKnownException(Class<Exception> exceptionClass) throws Exception {
        Mockito.when(this.mockBpiService.getCurrentBpi())
            .thenThrow(exceptionClass);
        this.mockMvc.perform(MockMvcRequestBuilders.get(BpiController.BPI_REQUEST_PATH))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(false)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.not(Matchers.emptyString())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.not(Matchers.emptyString())));
    }

    @Test
    void testGetCurrentBpi_withUnknownException() throws Exception {
        Mockito.when(this.mockBpiService.getCurrentBpi())
            .thenThrow(new RuntimeException());
        this.mockMvc.perform(MockMvcRequestBuilders.get(BpiController.BPI_REQUEST_PATH))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(false)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("9999")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Unknown exception occurred")));
    }

    private static class BpiExceptionProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(JsonParsingException.class),
                Arguments.of(BpiSourceConnectionException.class)
            );
        }
    }
}