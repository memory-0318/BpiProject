package tw.brian.hw.currency_mapping.exception;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
public class CurrencyCodeExistedException extends RuntimeException {
    public CurrencyCodeExistedException(String currencyCode) {
        super(String.format("There is the same currency code (%s) existed", currencyCode));
    }
}
