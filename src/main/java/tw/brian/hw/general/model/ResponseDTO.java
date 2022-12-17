package tw.brian.hw.general.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@Data
@Builder(setterPrefix = "set")
public class ResponseDTO<T> {
    private Boolean success;
    private OffsetDateTime currentDateTime;
    private T data;
    private String errorCode;
    private String message;

    public static <E> ResponseDTO<E> createSuccessResponse(E data) {
        return ResponseDTO.<E>builder()
            .setSuccess(true)
            .setCurrentDateTime(OffsetDateTime.now())
            .setData(data)
            .setErrorCode("")
            .setMessage("")
            .build();
    }

    public static <E> ResponseDTO<E> createSuccessResponse(E data, String message) {
        return ResponseDTO.<E>builder()
            .setSuccess(true)
            .setCurrentDateTime(OffsetDateTime.now())
            .setData(data)
            .setErrorCode("")
            .setMessage(message)
            .build();
    }

    public static ResponseDTO<Void> createFailureResponse(String errorCode, String message) {
        return ResponseDTO.<Void>builder()
            .setSuccess(false)
            .setCurrentDateTime(OffsetDateTime.now())
            .setErrorCode(errorCode)
            .setMessage(message)
            .build();
    }
}
