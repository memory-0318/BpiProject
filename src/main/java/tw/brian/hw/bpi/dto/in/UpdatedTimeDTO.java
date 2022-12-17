package tw.brian.hw.bpi.dto.in;

import lombok.Builder;
import lombok.Data;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Data
@Builder(setterPrefix = "set")
public class UpdatedTimeDTO {
    public static final String FIELD_UPDATED = "updated";
    public static final String FIELD_UPDATED_ISO = "updatedISO";
    public static final String FIELD_UPDATED_UK = "updateduk";

    private String updated;
    private String updatedISO;
    private String updateUK;
}
