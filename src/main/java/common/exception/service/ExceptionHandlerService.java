package common.exception.service;

import com.google.gson.Gson;
import common.exception.dto.ErrorResponseDto;
import common.exception.service.interfaces.IExceptionHandlerService;
import common.factory.util.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionHandlerService implements IExceptionHandlerService {
    private final Gson gson = GsonFactory.getInstance();

    @Override
    public void handleException(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String errorResponseAsJson = this.getErrorResponseAsJson(req, resp);
        this.buildHttpResponse(resp, errorResponseAsJson);
    }

    private String getErrorResponseAsJson(HttpServletRequest req, HttpServletResponse resp) {
        String uri = (String) req.getAttribute("jakarta.servlet.error.request_uri");
        if(uri == null) uri = "Unknown";

        String executorServlet = (String) req.getAttribute("jakarta.servlet.error.servlet_name");
        if(executorServlet == null) executorServlet = "Unknown";

        Throwable exception = (Throwable) req.getAttribute("jakarta.servlet.error.exception");
        String type = exception == null ? "Generic" : exception.getClass().getName();

        Integer status = (Integer) req.getAttribute("jakarta.servlet.error.status_code");

        String message = (String) req.getAttribute("jakarta.servlet.error.message");
        if(message == null) message = "Unknown";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lastAccessedTime = req.getSession().getLastAccessedTime();
        String timestamp = sdf.format(new Date(lastAccessedTime));

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(uri, executorServlet, type, status, message, timestamp);

        return this.gson.toJson(errorResponseDto);
    }

    private void buildHttpResponse(HttpServletResponse resp, String errorResponseToJson) throws IOException {
        OutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        out.write(errorResponseToJson.getBytes());

        out.flush();
        out.close();
    }
}