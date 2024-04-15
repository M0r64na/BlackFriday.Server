package common.factory.service;

import common.builder.HttpResponseBuilder;
import common.builder.interfaces.IHttpResponseBuilder;

public final class HttpResponseBuilderFactory {
    private static IHttpResponseBuilder instance = null;

    private HttpResponseBuilderFactory() {}

    public static IHttpResponseBuilder getInstance() {
        if(instance == null) instance = new HttpResponseBuilder();

        return instance;
    }
}