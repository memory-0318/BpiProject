package tw.brian.hw.general.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tw.brian.hw.general.model.ResponseDTO;
import tw.brian.hw.general.util.LoggingUtils;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@ControllerAdvice
public class GeneralControllerExceptionAdvice extends ResponseEntityExceptionHandler {
    /**
     * 處理未知的錯誤，並回傳給使用者
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        LoggingUtils.logError(ex);

        return handleExceptionInternal(
            ex,
            // TODO: error code and message are just hard-coded
            ResponseDTO.createFailureResponse("9999", "Unknown exception occurred"),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request);
    }
}
