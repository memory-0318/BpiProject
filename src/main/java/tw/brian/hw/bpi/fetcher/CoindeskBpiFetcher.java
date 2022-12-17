package tw.brian.hw.bpi.fetcher;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tw.brian.hw.bpi.exception.BpiSourceConnectionException;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
public class CoindeskBpiFetcher implements BpiFetcher {
    private final String coindeskApiUrl;
    private final RestTemplate restTemplate;

    public CoindeskBpiFetcher(String coindeskApiUrl, RestTemplate restTemplate) {
        this.coindeskApiUrl = coindeskApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public String fetchCurrentPrice() {
        try {
            return this.restTemplate.getForObject(this.coindeskApiUrl, String.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new BpiSourceConnectionException("Cannot connect to the BPI source", ex);
        } catch (HttpClientErrorException ex) {
            throw new BpiSourceConnectionException("Unknown exception occurred when connecting to the BPI source", ex);
        }
    }
}
