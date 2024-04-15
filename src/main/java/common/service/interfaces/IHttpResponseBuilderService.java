package common.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IHttpResponseBuilderService {
    void buildHttResponse(HttpServletResponse resp, String responseToJson, int status) throws IOException;
    void buildHttResponse(HttpServletResponse resp, String responseToJson) throws IOException;
}