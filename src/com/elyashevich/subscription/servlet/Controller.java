package com.elyashevich.subscription.servlet;

import com.elyashevich.subscription.command.ActionCommand;
import com.elyashevich.subscription.command.ActionFactory;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {"/controller/*"})

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
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
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        Router router;
        String page = null;
        try {
            router = command.execute(request);
            if (router!=null) {
                page = router.getPagePath();
                if (page != null) {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                    switch (router.getRoute()) {
                        case FORWARD:
                            dispatcher.forward(request, response);
                            break;
                        case REDIRECT:
                            response.sendRedirect(request.getContextPath() + page);
                            break;
                    }
                }
            }
            if (router==null || page==null){
                LOGGER.log(Level.INFO, "Null page");
                page = ConfigurationManager.getProperty("path.page.index");
                request.getSession().setAttribute(TextConstant.NULL_PAGE, MessageManager.EN.getMessage("message.nullpage"));
                response.sendRedirect(request.getContextPath() + page);
            }
        } catch (CommandTechnicalException e) {
            LOGGER.catching(e);
            request.getSession().setAttribute(TextConstant.EXCEPTION_CAUSE, e.getCause());
            request.getSession().setAttribute(TextConstant.EXCEPTION_MESSAGE, e.getMessage());
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error")).forward(request, response);
        }
    }

    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
