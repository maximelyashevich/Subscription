package com.elyashevich.subscription.command;

import com.elyashevich.subscription.action.MailThread;
import com.elyashevich.subscription.resource.ConfigurationManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class MailCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        try {
        Properties properties = new Properties();

        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter("mail");
// загрузка параметров почтового сервера в объект свойств
            properties.load(context.getResourceAsStream(filename));
        String sendToEmail = request.getParameter("to");
        String mailSubject = request.getParameter("subject");
        String mailText = request.getParameter("body");
        MailThread mailOperator=new MailThread(sendToEmail, mailSubject, mailText, properties);
// запуск процесса отправки письма в отдельном потоке
        mailOperator.start();
// переход на страницу с предложением о создании нового письма
        page = ConfigurationManager.getProperty("path.page.send");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}
