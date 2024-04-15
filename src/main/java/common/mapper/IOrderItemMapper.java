package common.mapper;

import common.dto.OrderItemDto;
import data.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IOrderItemMapper {
    IOrderItemMapper INSTANCE = Mappers.getMapper(IOrderItemMapper.class);

    OrderItemDto toRecord(OrderItem orderItem);
}