package common.exception.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IExceptionHandlerService {
    void handleException(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}