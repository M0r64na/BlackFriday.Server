package common.factory.filter;

import common.web.filter.AuthenticationFilter;
import jakarta.servlet.Filter;

public final class AuthenticationFilterFactory {
    private static Filter instance = null;

    private AuthenticationFilterFactory() {}

    public static Filter getInstance() {
        if(instance == null) instance = new AuthenticationFilter();

        return instance;
    }
}