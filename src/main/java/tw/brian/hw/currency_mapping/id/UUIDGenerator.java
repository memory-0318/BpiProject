package tw.brian.hw.currency_mapping.id;

import java.util.UUID;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
public class UUIDGenerator implements IdGenerator {
    @Override
    public String genId() {
        return UUID.randomUUID().toString();
    }
}
