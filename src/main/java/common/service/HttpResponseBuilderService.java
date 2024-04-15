package common.service;

import common.service.interfaces.IHttpResponseBuilderService;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseBuilderService implements IHttpResponseBuilderService {
    @Override
    public void buildHttResponse(HttpServletResponse resp, String responseToJson, int status) throws IOException {
        OutputStream out = resp.getOutputStream();

        resp.setContentType("application/json");
        resp.setStatus(status);
        if(!responseToJson.isEmpty()) out.write(responseToJson.getBytes());

        out.flush();
        out.close();
    }

    @Override
    public void buildHttResponse(HttpServletResponse resp, String responseToJson) throws IOException {
        this.buildHttResponse(resp, responseToJson, HttpServletResponse.SC_OK);
    }
}