package com.elyashevich.subscription.command;

import com.elyashevich.subscription.action.MailThread;
import com.elyashevich.subscription.manager.ConfigurationManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class MailCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        if (sendFromEmail(request, request.getParameter("to"),
                request.getParameter("subject"), request.getParameter("body"))){
            page = ConfigurationManager.getProperty("path.page.send");
        }
        return page;
    }
    public static boolean sendFromEmail(HttpServletRequest request, String sendToEmail, String mailSubject, String mailText){
        boolean result = false;
        try {
            Properties properties = new Properties();
            ServletContext context = request.getServletContext();
            String filename = context.getInitParameter("mail");

            properties.load(context.getResourceAsStream(filename));

            MailThread mailOperator=new MailThread(sendToEmail, mailSubject, mailText, properties);
            mailOperator.start();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
