package com.elyashevich.subscription.servlet;

import com.elyashevich.subscription.command.ActionCommand;
import com.elyashevich.subscription.command.ActionFactory;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.proxy.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        page = command.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager. getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    MessageManager.EN.getMessage("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
    public void destroy(){
        ConnectionPool.getInstance().destroyConnection();
    }
}
