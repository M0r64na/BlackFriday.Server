package common.factory.filter;

import common.web.filter.AuthorizationFilter;
import jakarta.servlet.Filter;

public final class AuthorizationFilterFactory {
    private static Filter instance = null;

    private AuthorizationFilterFactory() {}

    public static Filter getInstance() {
        if(instance == null) instance = new AuthorizationFilter();

        return instance;
    }
}