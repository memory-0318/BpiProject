package tw.brian.hw.currency_mapping.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tw.brian.hw.currency_mapping.exception.CurrencyCodeExistedException;
import tw.brian.hw.currency_mapping.exception.CurrencyMappingNotFoundException;
import tw.brian.hw.general.model.ResponseDTO;
import tw.brian.hw.general.util.LoggingUtils;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@ControllerAdvice(basePackages = { "tw.brian.hw.currency_mapping.controller" })
public class CurrencyMappingControllerExceptionAdvice extends ResponseEntityExceptionHandler {
    /**
     * 處理指定幣別重複的例外，並回傳給使用者
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { CurrencyCodeExistedException.class })
    protected ResponseEntity<Object> handleCurrencyCodeExistedException(Exception ex, WebRequest request) {
        LoggingUtils.logError(ex);

        return handleExceptionInternal(
            ex,
            // TODO: error code just hard-coded
            ResponseDTO.createFailureResponse("1000", ex.getMessage()),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request);
    }

    /**
     * 處理指定幣別資料不存在的例外，並回傳給使用者
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { CurrencyMappingNotFoundException.class })
    protected ResponseEntity<Object> handleCurrencyNotFoundException(Exception ex, WebRequest request) {
        LoggingUtils.logError(ex);

        return handleExceptionInternal(
            ex,
            // TODO: error code just hard-coded
            ResponseDTO.createFailureResponse("1001", ex.getMessage()),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request);
    }
}
