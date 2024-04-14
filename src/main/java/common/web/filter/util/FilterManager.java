package common.web.filter.util;

import common.factory.filter.AuthenticationFilterFactory;
import common.factory.filter.AuthorizationFilterFactory;
import common.web.filter.AuthenticationFilter;
import common.web.filter.AuthorizationFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public final class FilterManager {
    private FilterManager() {}

    public static void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Filter authenticationFilter = AuthenticationFilterFactory.getInstance();
        Filter authorizationFilter = AuthorizationFilterFactory.getInstance();
        Iterator<Filter> filters = Arrays.asList(authenticationFilter, authorizationFilter).iterator();
        FilterChain filterChain = new CustomFilterChain(filters);

        filterChain.doFilter(req, resp);
    }
}