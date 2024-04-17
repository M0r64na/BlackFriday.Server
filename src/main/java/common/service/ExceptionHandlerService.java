package common.service;

import com.google.gson.Gson;
import common.builder.HttpResponseBuilder;
import common.dto.ErrorResponseDto;
import common.exception.ConflictException;
import common.exception.ForbiddenException;
import common.exception.NotAuthorizedException;
import common.exception.NotFoundException;
import common.service.interfaces.IExceptionHandlerService;
import common.factory.util.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerService extends HttpResponseBuilder implements IExceptionHandlerService {
    private final Gson gson = GsonFactory.getInstance();
    private final Map<Class<? extends Exception>, Integer> exceptionStatusMappings = new HashMap<>() {{
        put(ConflictException.class, HttpServletResponse.SC_CONFLICT);
        put(NotAuthorizedException.class, HttpServletResponse.SC_UNAUTHORIZED);
        put(ForbiddenException.class, HttpServletResponse.SC_FORBIDDEN);
        put(NotFoundException.class, HttpServletResponse.SC_NOT_FOUND);
    }};

    @Override
    public void handleException(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int status = this.getErrorStatus(req);
        String errorResponseAsJson = this.getErrorResponseAsJson(req, resp, status);

        super.buildHttResponse(resp, errorResponseAsJson, status);
    }

    private Class<?> getExceptionClass(HttpServletRequest req) {
        return req.getAttribute("jakarta.servlet.error.exception").getClass();
    }

    private int getErrorStatus(HttpServletRequest req) {
        Class<?> exceptionClass = this.getExceptionClass(req);

        return exceptionStatusMappings.getOrDefault(exceptionClass, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private String getErrorResponseAsJson(HttpServletRequest req, HttpServletResponse resp, int status) {
        String uri = (String) req.getAttribute("jakarta.servlet.error.request_uri");
        if(uri == null) uri = "Unknown";

        String executorServlet = (String) req.getAttribute("jakarta.servlet.error.servlet_name");
        if(executorServlet == null) executorServlet = "Unknown";

        String type = this.getExceptionClass(req).getName();

        String message = (String) req.getAttribute("jakarta.servlet.error.message");
        if(message == null) message = "Unknown";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lastAccessedTime = req.getSession().getLastAccessedTime();
        String timestamp = sdf.format(new Date(lastAccessedTime));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(uri, executorServlet, type, status, message, timestamp);

        return this.gson.toJson(errorResponseDto);
    }
}