package common.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(String name, String description, int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                         UserDto createdBy, LocalDateTime createdOn, UserDto lastModifiedBy, LocalDateTime lastModifiedOn) { }