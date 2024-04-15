package common.mapper;

import common.dto.StatusDto;
import data.domain.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IStatusMapper {
    IStatusMapper INSTANCE = Mappers.getMapper(IStatusMapper.class);

    StatusDto toRecord(Status status);
}