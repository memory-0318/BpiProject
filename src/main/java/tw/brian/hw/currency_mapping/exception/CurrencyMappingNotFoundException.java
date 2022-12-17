package tw.brian.hw.currency_mapping.exception;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
public class CurrencyMappingNotFoundException extends RuntimeException {
    public CurrencyMappingNotFoundException(String hashId) {
        super(String.format("Currency Mapping not found when id is %s", hashId));
    }
}
