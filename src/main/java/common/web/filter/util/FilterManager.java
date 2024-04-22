package common.web.filter.util;

import common.factory.filter.AuthenticationFilterFactory;
import common.factory.filter.AuthorizationFilterFactory;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public final class FilterManager {
    private FilterManager() {}

    public static void process(HttpServletRequest req, HttpServletResponse resp, boolean isAuthorizationNeeded) throws ServletException, IOException {
        Iterator<Filter> filters;
        Filter authenticationFilter = AuthenticationFilterFactory.getInstance();

        if(isAuthorizationNeeded) {
            Filter authorizationFilter = AuthorizationFilterFactory.getInstance();
            filters = Arrays.asList(authenticationFilter, authorizationFilter).iterator();
        }
        else filters = Collections.singletonList(authenticationFilter).iterator();

        FilterChain filterChain = new CustomFilterChain(filters);

        filterChain.doFilter(req, resp);
    }
}