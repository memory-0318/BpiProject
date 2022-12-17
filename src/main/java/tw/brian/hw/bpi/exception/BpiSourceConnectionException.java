package tw.brian.hw.bpi.exception;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
public class BpiSourceConnectionException extends RuntimeException {
    public BpiSourceConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
