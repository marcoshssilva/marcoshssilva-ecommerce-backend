package br.com.marcoshssilva.ecommerce.filters;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Expose-Headers", "Location");
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
    
}
