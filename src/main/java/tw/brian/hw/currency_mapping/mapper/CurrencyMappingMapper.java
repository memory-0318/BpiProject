package tw.brian.hw.currency_mapping.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tw.brian.hw.currency_mapping.dto.in.CurrencyMappingInputDTO;
import tw.brian.hw.currency_mapping.dto.out.CurrencyMappingOutputDTO;
import tw.brian.hw.currency_mapping.entiry.CurrencyMappingEntity;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Mapper
public interface CurrencyMappingMapper {
    CurrencyMappingMapper INSTANCE = Mappers.getMapper(CurrencyMappingMapper.class);

    CurrencyMappingOutputDTO toResponseDTO(CurrencyMappingEntity entity);

    CurrencyMappingEntity toEntity(CurrencyMappingInputDTO inputDTO);
}
