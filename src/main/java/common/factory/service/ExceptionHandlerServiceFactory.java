package common.factory.service;

import common.exception.service.ExceptionHandlerService;
import common.exception.service.interfaces.IExceptionHandlerService;

public final class ExceptionHandlerServiceFactory {
    private static IExceptionHandlerService instance = null;

    private ExceptionHandlerServiceFactory() {}

    public static IExceptionHandlerService getInstance() {
        if(instance == null) instance = new ExceptionHandlerService();

        return instance;
    }
}