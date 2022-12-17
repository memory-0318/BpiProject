package tw.brian.hw.bpi.fetcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tw.brian.hw.bpi.exception.BpiSourceConnectionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@ExtendWith(MockitoExtension.class)
class CoindeskBpiFetcherTest {
    @Mock
    private RestTemplate restTemplate;
    private final String fakeUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFetchCurrentPrice_withSuccessfulResponse() {
        CoindeskBpiFetcher fetcher = new CoindeskBpiFetcher(this.fakeUrl, this.restTemplate);
        String trueResponse = "{\"time\":{\"updated\":\"Dec 14, 2022 22:48:00 UTC\",\"updatedISO\":\"2022-12-14T22:48:00+00:00\",\"updateduk\":\"Dec 14, 2022 at 22:48 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"17,813.1110\",\"description\":\"United States Dollar\",\"rate_float\":17813.111},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"14,884.4931\",\"description\":\"British Pound Sterling\",\"rate_float\":14884.4931},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"17,352.5708\",\"description\":\"Euro\",\"rate_float\":17352.5708}}}";
        Mockito.when(this.restTemplate.getForObject(this.fakeUrl, String.class))
            .thenReturn(trueResponse);
        Assertions.assertEquals(trueResponse, fetcher.fetchCurrentPrice());
    }

    @ParameterizedTest
    @ValueSource(classes = {
        HttpClientErrorException.NotFound.class, HttpClientErrorException.BadRequest.class,
        HttpClientErrorException.Forbidden.class
    })
    void testFetchCurrentPrice_withConnectionFailure(Class<Exception> exceptionClass) {
        CoindeskBpiFetcher fetcher = new CoindeskBpiFetcher(this.fakeUrl, this.restTemplate);
        Mockito.when(this.restTemplate.getForObject(this.fakeUrl, String.class))
            .thenThrow(exceptionClass);

        Assertions.assertThrows(BpiSourceConnectionException.class, fetcher::fetchCurrentPrice);
    }
}