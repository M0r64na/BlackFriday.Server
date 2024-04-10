package common.web;

import common.service.interfaces.IExceptionHandlerService;
import common.factory.service.ExceptionHandlerServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "GlobalExceptionServlet", value = "/error")
public class GlobalExceptionServlet extends HttpServlet {
    /* Similar to Spring Problem Details impl => json format
    {
        "type": "https://api.bookmarks.com/errors/not-found",
        "status": 404,
        "detail": "Bookmark with id=111 not found",
        "instance": "/api/bookmarks/111",
        "timestamp": "2023-08-30T05:21:59.828411Z"
    }

    * get exception msg
    * get status code
    * get executor servlet name
    * get the uniform resource identifier of the req
    * get timestamp
     */

    private final IExceptionHandlerService exceptionHandlerService = ExceptionHandlerServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.exceptionHandlerService.handleException(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.exceptionHandlerService.handleException(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.exceptionHandlerService.handleException(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.exceptionHandlerService.handleException(req, resp);
    }
}