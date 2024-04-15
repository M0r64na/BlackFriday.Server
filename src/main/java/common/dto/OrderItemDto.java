package common.dto;

public record OrderItemDto(ProductDto product, int productQuantity) { }