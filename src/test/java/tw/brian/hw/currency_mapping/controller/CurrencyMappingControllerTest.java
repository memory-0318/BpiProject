package tw.brian.hw.currency_mapping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tw.brian.hw.currency_mapping.dto.in.CurrencyMappingInputDTO;
import tw.brian.hw.currency_mapping.dto.out.CurrencyMappingOutputDTO;
import tw.brian.hw.currency_mapping.exception.CurrencyCodeExistedException;
import tw.brian.hw.currency_mapping.exception.CurrencyMappingNotFoundException;
import tw.brian.hw.currency_mapping.service.CurrencyMappingService;

import java.util.Collections;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@WebMvcTest(CurrencyMappingController.class)
class CurrencyMappingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CurrencyMappingService mockCurrencyMappingService;
    private CurrencyMappingInputDTO inputDTO;
    private final String fakeCurrencyMappingId = "123";

    @BeforeEach
    void setUp() {
        this.inputDTO = this.genSampleCurrencyMappingInputDTO();
    }

    @Nested
    @DisplayName("Test adding currency mapping operations")
    class AddOperationTestSet {

        @Test
        void testAddCurrencyMapping_withSuccessResponse() throws Exception {
            CurrencyMappingOutputDTO outputDTO = genSampleCurrencyMappingOutputDTO();
            Mockito.when(mockCurrencyMappingService.addCurrencyMapping(Mockito.any()))
                .thenReturn(outputDTO);
            mockMvc.perform(MockMvcRequestBuilders.post(CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.emptyString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.emptyString()))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.data.currencyCode", Matchers.is(outputDTO.getCurrencyCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyNameInZhTw",
                    Matchers.is(outputDTO.getCurrencyNameInZhTw())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputDTO.getHashId())));
        }

        @Test
        void testAddCurrencyMapping_withCurrencyCodeExisted() throws Exception {
            Mockito.when(mockCurrencyMappingService.addCurrencyMapping(Mockito.any()))
                .thenThrow(new CurrencyCodeExistedException(inputDTO.getCurrencyCode()));
            mockMvc.perform(MockMvcRequestBuilders.post(CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        void testAddCurrencyMapping_withUnknownException() throws Exception {
            Mockito.when(mockCurrencyMappingService.addCurrencyMapping(Mockito.any()))
                .thenThrow(new RuntimeException());
            mockMvc.perform(MockMvcRequestBuilders.post(CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("9999")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Unknown exception occurred")));
        }
    }

    @Nested
    @DisplayName("Test query operations of currency mapping")
    class QueryOperationTestSet {
        @Test
        void testGetCurrencyMapping_withSuccessResponse() throws Exception {
            CurrencyMappingOutputDTO outputDTO = genSampleCurrencyMappingOutputDTO();
            Mockito.when(mockCurrencyMappingService.getCurrencyMapping(Mockito.any()))
                .thenReturn(outputDTO);
            mockMvc.perform(MockMvcRequestBuilders.get(
                    String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.emptyString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.emptyString()))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.data.currencyCode", Matchers.is(outputDTO.getCurrencyCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyNameInZhTw",
                    Matchers.is(outputDTO.getCurrencyNameInZhTw())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputDTO.getHashId())));
        }

        @Test
        void testGetCurrencyMapping_withCurrencyMappingNotFound() throws Exception {
            Mockito.when(mockCurrencyMappingService.getCurrencyMapping(Mockito.any()))
                .thenThrow(new CurrencyMappingNotFoundException(inputDTO.getCurrencyCode()));
            mockMvc.perform(MockMvcRequestBuilders.get(
                    String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("1001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.not(Matchers.emptyString())));
        }

        @Test
        void testGetCurrencyMapping_withUnknownException() throws Exception {
            Mockito.when(mockCurrencyMappingService.getCurrencyMapping(Mockito.any()))
                .thenThrow(new RuntimeException());
            mockMvc.perform(MockMvcRequestBuilders.get(
                    String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("9999")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Unknown exception occurred")));
        }

        @Test
        void testListAllCurrencyMappings_withSuccessResponse() throws Exception {
            CurrencyMappingOutputDTO outputDTO = genSampleCurrencyMappingOutputDTO();
            Mockito.when(mockCurrencyMappingService.listAllCurrencyMappings())
                .thenReturn(Collections.singletonList(outputDTO));
            mockMvc.perform(MockMvcRequestBuilders.get(CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.emptyString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.emptyString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].currencyCode").value(outputDTO.getCurrencyCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].currencyNameInZhTw")
                    .value(outputDTO.getCurrencyNameInZhTw()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(outputDTO.getHashId()));
        }

        @Test
        void testListAllCurrencyMappings_withUnknownException() throws Exception {
            Mockito.when(mockCurrencyMappingService.listAllCurrencyMappings())
                .thenThrow(new RuntimeException());
            mockMvc.perform(MockMvcRequestBuilders.get(CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("9999")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Unknown exception occurred")));
        }
    }

    @Nested
    @DisplayName("Test updating operation of currency mapping")
    class UpdateOperationsTestSet {

        @Test
        void testUpdateCurrencyMapping_withSuccessResponse() throws Exception {
            CurrencyMappingOutputDTO outputDTO = genSampleCurrencyMappingOutputDTO();
            Mockito.when(mockCurrencyMappingService.updateCurrencyMapping(Mockito.any(), Mockito.any()))
                .thenReturn(outputDTO);
            mockMvc.perform(MockMvcRequestBuilders.put(
                        String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.emptyString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.emptyString()))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.data.currencyCode", Matchers.is(outputDTO.getCurrencyCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyNameInZhTw",
                    Matchers.is(outputDTO.getCurrencyNameInZhTw())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputDTO.getHashId())));
        }

        @Test
        void testUpdateCurrencyMapping_withCurrencyMappingNotFound() throws Exception {
            Mockito.when(mockCurrencyMappingService.updateCurrencyMapping(Mockito.any(), Mockito.any()))
                .thenThrow(new CurrencyMappingNotFoundException(inputDTO.getCurrencyCode()));
            mockMvc.perform(MockMvcRequestBuilders.put(
                        String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("1000")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.not(Matchers.emptyString())));
        }

        @Test
        void testUpdateCurrencyMapping_withUnknownException() throws Exception {
            Mockito.when(mockCurrencyMappingService.updateCurrencyMapping(Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException());
            mockMvc.perform(MockMvcRequestBuilders.put(
                        String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("9999")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Unknown exception occurred")));
        }
    }

    @Nested
    @DisplayName("Test removal operation of currency mapping")
    class RemoveOperationsTestSet {

        @Test
        void testRemoveCurrencyMapping_withSuccessResponse() throws Exception {
            CurrencyMappingOutputDTO outputDTO = genSampleCurrencyMappingOutputDTO();
            Mockito.when(mockCurrencyMappingService.removeCurrencyMapping(Mockito.any()))
                .thenReturn(outputDTO);
            mockMvc.perform(MockMvcRequestBuilders.delete(
                    String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentDateTime", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.emptyString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.emptyString()))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.data.currencyCode", Matchers.is(outputDTO.getCurrencyCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyNameInZhTw",
                    Matchers.is(outputDTO.getCurrencyNameInZhTw())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputDTO.getHashId())));
        }

        @Test
        void testRemoveCurrencyMapping_withCurrencyMappingNotFound() throws Exception {
            Mockito.when(mockCurrencyMappingService.removeCurrencyMapping(Mockito.any()))
                .thenThrow(new CurrencyMappingNotFoundException(inputDTO.getCurrencyCode()));
            mockMvc.perform(MockMvcRequestBuilders.delete(
                    String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("1001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.not(Matchers.emptyString())));
        }

        @Test
        void testRemoveCurrencyMapping_withUnknownException() throws Exception {
            Mockito.when(mockCurrencyMappingService.removeCurrencyMapping(Mockito.any()))
                .thenThrow(new RuntimeException());
            mockMvc.perform(MockMvcRequestBuilders.delete(
                    String.format("%s/%s", CurrencyMappingController.CURRENCY_MAPPING_REQUEST_URL, fakeCurrencyMappingId)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("9999")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Unknown exception occurred")));
        }
    }

    private CurrencyMappingOutputDTO genSampleCurrencyMappingOutputDTO() {
        return CurrencyMappingOutputDTO.builder()
            .setHashId("123")
            .setCurrencyCode("TTT")
            .setCurrencyNameInZhTw("測試幣別")
            .build();
    }

    private CurrencyMappingInputDTO genSampleCurrencyMappingInputDTO() {
        return CurrencyMappingInputDTO.builder()
            .setCurrencyCode("TTT")
            .setCurrencyNameInZhTw("測試幣別")
            .build();
    }
}