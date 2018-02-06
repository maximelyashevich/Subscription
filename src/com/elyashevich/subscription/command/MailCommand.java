package com.elyashevich.subscription.command;

import com.elyashevich.subscription.mail.MailThread;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class MailCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        if (sendFromEmail(request, request.getParameter(TextConstant.TO),
                request.getParameter(TextConstant.SUBJECT), request.getParameter(TextConstant.BODY))){
            page = ConfigurationManager.getProperty("path.page.send");
        }
        router.setPagePath(page);
        return router;
    }
    public static boolean sendFromEmail(HttpServletRequest request, String sendToEmail, String mailSubject, String mailText) throws CommandTechnicalException {
        boolean result = false;
        try {
            Properties properties = new Properties();
            ServletContext context = request.getServletContext();
            String filename = context.getInitParameter(TextConstant.MAIL);

            properties.load(context.getResourceAsStream(filename));

            MailThread mailOperator=new MailThread(sendToEmail, mailSubject, mailText, properties);
            mailOperator.start();
            result = true;
        } catch (IOException e) {
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        return result;
    }
}
