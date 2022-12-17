package tw.brian.hw.currency_mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.brian.hw.currency_mapping.entiry.CurrencyMappingEntity;

import java.util.Optional;

@Repository
public interface CurrencyMappingRepository extends JpaRepository<CurrencyMappingEntity, Integer> {
    boolean existsByHashId(String hashId);

    boolean existsByCurrencyCode(String currencyCode);

    Optional<CurrencyMappingEntity> findByHashId(String hashId);

    CurrencyMappingEntity deleteByHashId(String hashId);
}