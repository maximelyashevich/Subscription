package com.elyashevich.subscription.action;

import com.elyashevich.subscription.creator.SessionCreator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailThread extends Thread {
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;
    public MailThread(String sendToEmail, String mailSubject, String mailText, Properties properties) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }
    private void init() {
// объект почтовой сессии
        Session mailSession = (new SessionCreator(properties)).createSession();
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            ///
            message.setFrom(new InternetAddress(mailSession.getProperties().getProperty("mail.smtp.name")));
            //!
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html; charset=utf-8");
            message.setRecipient(Message.RecipientType. TO, new InternetAddress(sendToEmail));
        } catch (AddressException e) {
            System. err.print("Некорректный адрес:" + sendToEmail + " " + e);
// in log file
        } catch (MessagingException e) {
            System. err.print("Ошибка формирования сообщения" + e);
// in log file
        }
    }
    public void run() {
        init();
        try {
// отправка почтового сообщения
            Transport.send(message);
        } catch (MessagingException e) {
            System. err. print("Ошибка при отправлении сообщения" + e);
// in log file
        }
    }
}