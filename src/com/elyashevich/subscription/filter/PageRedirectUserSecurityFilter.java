package com.elyashevich.subscription.filter;

import com.elyashevich.subscription.command.ClientType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/main/*", "/jsp/user/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectUserSecurityFilter implements Filter{
    private String indexPath;

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getSession().getAttribute(TextConstant.USER_PARAM)==null || !ClientType.USER.equals(((User)httpRequest.getSession().getAttribute(TextConstant.USER_PARAM)).getType())) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }else{
            chain.doFilter(request, response);
        }

    }

    public void destroy() {
    }
}



