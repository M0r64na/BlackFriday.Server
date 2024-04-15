package common.service.interfaces;

import common.builder.interfaces.IHttpResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IExceptionHandlerService extends IHttpResponseBuilder {
    void handleException(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}