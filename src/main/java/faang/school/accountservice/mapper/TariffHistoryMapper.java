package faang.school.accountservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffHistoryMapper {

    TariffHistoryDto toDto(TariffHistory tariffHistory);

    TariffHistory toEntity(TariffHistoryDto tariffHistoryDto);
}
