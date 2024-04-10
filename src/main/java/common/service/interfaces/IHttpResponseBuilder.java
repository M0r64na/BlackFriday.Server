package common.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IHttpResponseBuilder {
    void buildHttResponse(HttpServletResponse resp, String responseToJson, int status) throws IOException;
}