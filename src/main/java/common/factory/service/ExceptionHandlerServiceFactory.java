package common.factory.service;

import common.service.ExceptionHandlerService;
import common.service.interfaces.IExceptionHandlerService;

public final class ExceptionHandlerServiceFactory {
    private static IExceptionHandlerService instance = null;

    private ExceptionHandlerServiceFactory() {}

    public static IExceptionHandlerService getInstance() {
        if(instance == null) instance = new ExceptionHandlerService();

        return instance;
    }
}