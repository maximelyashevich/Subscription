//package com.elyashevich.subscription.filter;
//
//import com.elyashevich.subscription.entity.User;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
/////////!!!! - ClientType - подпакет!!!
//@WebFilter(urlPatterns = { "/controller" }, servletNames = { "controller" })
//public class ServletSecurityFilter implements Filter {
//    private String contextPath;
//    public void destroy() {
//    }
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        User user = (User) req.getSession().getAttribute("LOGIN_USER");
//        if (user == null) {
//            //put your redirect stuff here
//            res.sendRedirect(contextPath+"/jsp/login.jsp");
//        } else {
//            switch (user.getType()) {
//                case ADMIN:
//                    //put your redirect stuff here
//                    res.sendRedirect(contextPath+"/jsp/admin/admin.jsp");
//                    break;
//                case USER:
//                    //put your redirect stuff here
//                    res.sendRedirect(contextPath+"/jsp/main.jsp");
//                    break;
//                default:
//                    break;
//            }
//            chain.doFilter(request, response);
//        }
//    }
//    public void init(FilterConfig fConfig) {
//        contextPath = fConfig.getServletContext().getContextPath();
//    }
//}