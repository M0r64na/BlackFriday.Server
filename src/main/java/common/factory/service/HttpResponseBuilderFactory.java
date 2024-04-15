package common.factory.service;

import common.service.HttpResponseBuilderService;
import common.service.interfaces.IHttpResponseBuilderService;

public final class HttpResponseBuilderFactory {
    private static IHttpResponseBuilderService instance = null;

    private HttpResponseBuilderFactory() {}

    public static IHttpResponseBuilderService getInstance() {
        if(instance == null) instance = new HttpResponseBuilderService();

        return instance;
    }
}