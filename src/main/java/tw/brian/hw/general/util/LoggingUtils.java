package tw.brian.hw.general.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/18
 */
public final class LoggingUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingUtils.class);

    public static void logRequest(HttpServletRequest req, Object reqBody) {
        String loggingMessage = "REQUEST " +
            String.format("method=[%s] ", req.getMethod()) +
            String.format("path=[%s] ", req.getRequestURI()) +
            String.format("header=[%s] ", buildHeadersMap(req)) +
            String.format("params=[%s] ", buildParamsMap(req)) +
            String.format("body=[%s]", reqBody);

        LOGGER.info(loggingMessage);
    }

    public static void logResponse(HttpServletRequest req, HttpServletResponse resp, Object respBody) {
        String loggingMessage = "RESPONSE " +
            String.format("header=[%s] ", buildHeadersMap(resp)) +
            String.format("body=[%s]", respBody);

        LOGGER.info(loggingMessage);
    }

    private static Map<String, String> buildParamsMap(HttpServletRequest req) {
        Map<String, String> paramsMap = new HashMap<>();

        Enumeration<String> paramKeys = req.getHeaderNames();
        while (paramKeys.hasMoreElements()) {
            String paramKey = paramKeys.nextElement();
            String paramVal = req.getParameter(paramKey);
            paramsMap.put(paramKey, paramVal);
        }

        return paramsMap;
    }

    private static Map<String, String> buildHeadersMap(HttpServletRequest req) {
        Map<String, String> headersMap = new HashMap<>();

        Enumeration<String> headerKeys = req.getHeaderNames();
        while (headerKeys.hasMoreElements()) {
            String headerKey = headerKeys.nextElement();
            String headerVal = req.getHeader(headerKey);
            headersMap.put(headerKey, headerVal);
        }

        return headersMap;
    }

    private static Map<String, String> buildHeadersMap(HttpServletResponse resp) {
        Map<String, String> headersMap = new HashMap<>();

        for (String headerKey : resp.getHeaderNames()) {
            headersMap.put(headerKey, resp.getHeader(headerKey));
        }

        return headersMap;
    }
}
