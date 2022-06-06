package com.joebrooks.mapshotservice.global.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")
                || httpRequest.getMethod().equalsIgnoreCase("PUT")
                || httpRequest.getMethod().equalsIgnoreCase("DELETE")
                || httpRequest.getMethod().equalsIgnoreCase("TRACE")
                || httpRequest.getMethod().equalsIgnoreCase("PATCH")
                || httpRequest.getMethod().equalsIgnoreCase("OPTION")
                || httpRequest.getMethod().equalsIgnoreCase("POST")) {

            httpResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            httpResponse.setStatus(405);

            return;
        }

        chain.doFilter(request, response);
    }
}
