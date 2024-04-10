package common.dto;

public record ErrorResponseDto(String uri, String executorServlet, String type, Integer status, String message, String timestamp) {
}