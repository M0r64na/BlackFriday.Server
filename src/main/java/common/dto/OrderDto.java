package common.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(StatusDto status, UserDto createdBy, Set<OrderItemDto> items, LocalDateTime createdOn) { }