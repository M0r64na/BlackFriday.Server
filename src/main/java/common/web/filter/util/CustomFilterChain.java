package common.web.filter.util;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.Iterator;

public class CustomFilterChain implements FilterChain {
    private final Iterator<Filter> filters;

    public CustomFilterChain(Iterator<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        if(filters.hasNext()) filters.next().doFilter(servletRequest, servletResponse, this);
    }
}