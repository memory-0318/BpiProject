package tw.brian.hw.bpi.formatter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
public class OutputDateTimeFormatter {
    private DateTimeFormatter formatter;

    public OutputDateTimeFormatter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    public String formatDateTimeWithTimeZone(String inputDateTimeStr) {
        OffsetDateTime localDateTime = OffsetDateTime.parse(inputDateTimeStr);
        return this.formatter.format(localDateTime);
    }
}
