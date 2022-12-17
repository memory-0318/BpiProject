package tw.brian.hw.currency_mapping.entiry;

import javax.persistence.*;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Entity
@Table(name = "currency_mapping")
public class CurrencyMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer pkey;
    @Column(name = "hash_id", unique = true)
    private String hashId;
    @Column(name = "currency_code", unique = true)
    private String currencyCode;
    @Column(name = "currency_name_zhtw")
    private String currencyNameInZhTw;

    public Integer getPkey() {
        return pkey;
    }

    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyNameInZhTw() {
        return currencyNameInZhTw;
    }

    public void setCurrencyNameInZhTw(String currencyNameInZhTw) {
        this.currencyNameInZhTw = currencyNameInZhTw;
    }
}
