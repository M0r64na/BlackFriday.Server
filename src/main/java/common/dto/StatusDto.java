package common.dto;

import data.domain.enums.OrderStatus;

public record StatusDto(OrderStatus orderStatus) { }