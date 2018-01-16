//package com.elyashevich.subscription.filter;
//
//import com.elyashevich.subscription.command.client.ClientType;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
/////////!!!! - ClientType - подпакет!!!
//@WebFilter(urlPatterns = { "/controller" }, servletNames = { "controller" })
//public class ServletSecurityFilter implements Filter {
//    public void destroy() {
//    }
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        HttpSession session = req.getSession();
//        ClientType type = (ClientType) session.getAttribute("userType");
//        if (type == null) {
//            type = ClientType. GUEST;
//            session.setAttribute("userType", type);
//            RequestDispatcher dispatcher = request.getServletContext()
//                    .getRequestDispatcher("/jsp/login.jsp");
//            dispatcher.forward(req, resp);
//            return;
//        }
//        // pass the request along the filter chain
//        chain.doFilter(request, response);
//    }
//    public void init(FilterConfig fConfig) throws ServletException {
//    }
//}