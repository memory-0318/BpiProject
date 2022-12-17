package tw.brian.hw.currency_mapping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tw.brian.hw.currency_mapping.id.IdGenerator;
import tw.brian.hw.currency_mapping.id.UUIDGenerator;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Configuration
public class CurrencyMappingMaintenanceConfig {
    @Bean
    IdGenerator genUUIDGeneratorBean() {
        return new UUIDGenerator();
    }
}
