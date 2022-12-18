package tw.brian.hw.general.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tw.brian.hw.general.util.LoggingUtils;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/18
 */
@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response) {
        if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
            LoggingUtils.logResponse(((ServletServerHttpRequest) request).getServletRequest(),
                ((ServletServerHttpResponse) response).getServletResponse(), body);
        }

        return body;
    }
}
