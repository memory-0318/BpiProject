package tw.brian.hw.currency_mapping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.brian.hw.currency_mapping.dto.in.CurrencyMappingInputDTO;
import tw.brian.hw.currency_mapping.dto.out.CurrencyMappingOutputDTO;
import tw.brian.hw.currency_mapping.entiry.CurrencyMappingEntity;
import tw.brian.hw.currency_mapping.exception.CurrencyMappingNotFoundException;
import tw.brian.hw.currency_mapping.exception.CurrencyCodeExistedException;
import tw.brian.hw.currency_mapping.id.IdGenerator;
import tw.brian.hw.currency_mapping.mapper.CurrencyMappingMapper;
import tw.brian.hw.currency_mapping.repository.CurrencyMappingRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/15
 */
@Service
public class CurrencyMappingService {
    @Autowired
    private CurrencyMappingRepository currencyMappingRepository;
    @Autowired
    private IdGenerator hashIdGenerator;

    public CurrencyMappingOutputDTO addCurrencyMapping(CurrencyMappingInputDTO inputDTO) {
        if (this.currencyMappingRepository.existsByCurrencyCode(inputDTO.getCurrencyCode())) {
            throw new CurrencyCodeExistedException(inputDTO.getCurrencyCode());
        }

        CurrencyMappingEntity entityToSave = CurrencyMappingMapper.INSTANCE.toEntity(inputDTO);
        entityToSave.setHashId(this.hashIdGenerator.genId());
        return CurrencyMappingMapper.INSTANCE.toResponseDTO(
            this.currencyMappingRepository.save(entityToSave)
        );
    }

    public CurrencyMappingOutputDTO getCurrencyMapping(String hashId) {
        return CurrencyMappingMapper.INSTANCE.toResponseDTO(
            this.currencyMappingRepository.findByHashId(hashId)
                .orElseThrow(() -> new CurrencyMappingNotFoundException(hashId))
        );
    }

    public List<CurrencyMappingOutputDTO> listAllCurrencyMappings() {
        return this.currencyMappingRepository.findAll()
            .stream()
            .map(CurrencyMappingMapper.INSTANCE::toResponseDTO)
            .collect(Collectors.toList());
    }

    public CurrencyMappingOutputDTO updateCurrencyMapping(String hashId, CurrencyMappingInputDTO inputDTO) {
        CurrencyMappingEntity entityToUpdate = this.currencyMappingRepository.findByHashId(hashId)
            .orElseThrow(() -> new CurrencyMappingNotFoundException(hashId));
        entityToUpdate.setCurrencyCode(inputDTO.getCurrencyCode());
        entityToUpdate.setCurrencyNameInZhTw(inputDTO.getCurrencyNameInZhTw());
        return CurrencyMappingMapper.INSTANCE.toResponseDTO(
            this.currencyMappingRepository.save(entityToUpdate)
        );
    }

    public CurrencyMappingOutputDTO removeCurrencyMapping(String hashId) {
        if (!this.currencyMappingRepository.existsByHashId(hashId)) {
            throw new CurrencyMappingNotFoundException(hashId);
        }

        return CurrencyMappingMapper.INSTANCE.toResponseDTO(
            this.currencyMappingRepository.deleteByHashId(hashId)
        );
    }
}
