package tw.brian.hw.bpi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tw.brian.hw.bpi.formatter.OutputDateTimeFormatter;

import java.time.format.DateTimeFormatter;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Configuration
public class BpiConfig {

    @Bean
    public RestTemplate genRestTemplateBean() {
        return new RestTemplate();
    }

    @Bean
    public OutputDateTimeFormatter genOutputDateTimeFormatterBean(@Value("${time.format}") String format) {
        return new OutputDateTimeFormatter(format);
    }
}
