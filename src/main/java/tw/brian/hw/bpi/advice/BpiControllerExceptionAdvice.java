package tw.brian.hw.bpi.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tw.brian.hw.bpi.exception.BpiSourceConnectionException;
import tw.brian.hw.bpi.exception.JsonParsingException;
import tw.brian.hw.general.model.ResponseDTO;
import tw.brian.hw.general.util.LoggingUtils;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@ControllerAdvice(basePackages = { "tw.brian.hw.bpi.controller" })
public class BpiControllerExceptionAdvice extends ResponseEntityExceptionHandler {
    /**
     * 處理Json解析例外並擷取錯誤訊息，回傳給使用者
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { JsonParsingException.class })
    protected ResponseEntity<Object> handleJsonParsingException(Exception ex, WebRequest request) {
        LoggingUtils.logError(ex);

        return handleExceptionInternal(
            ex,
            // TODO: error code just hard-coded
            ResponseDTO.createFailureResponse("0001", ex.getMessage()),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request);
    }

    /**
     * 處理連線異常並擷取錯誤訊息，回傳給使用者
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = BpiSourceConnectionException.class)
    protected ResponseEntity<Object> handleConnectionException(Exception ex, WebRequest request) {
        LoggingUtils.logError(ex);

        return handleExceptionInternal(
            ex,
            ResponseDTO.createFailureResponse("0001", ex.getMessage()),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request);
    }
}
