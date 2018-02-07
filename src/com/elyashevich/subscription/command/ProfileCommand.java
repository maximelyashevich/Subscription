package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.DefaultService;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.MailService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfileCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private UserService userReceiver;

    ProfileCommand(UserService userReceiver){
        this.userReceiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page;
        LocaleService localeService = new LocaleService();
        MailService mailService = new MailService();
        DefaultService defaultService = new DefaultService();

        ServletContext context = request.getServletContext();
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeService.defineMessageManager(userLocale);

        LOGGER.log(Level.INFO, "Try to update user profile...");

        String firstName = request.getParameter(TextConstant.FIRST_NAME);
        String lastName = request.getParameter(TextConstant.LAST_NAME);
        String country = request.getParameter(TextConstant.COUNTRY_PARAM);
        String city = request.getParameter(TextConstant.CITY);
        String postIndex = request.getParameter(TextConstant.POST_INDEX);
        String detailAddress = request.getParameter(TextConstant.DETAIL_ADDRESS);
        String dob = request.getParameter(TextConstant.DOB);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TextConstant.DATE_PATTERN);

        LocalDate birthday = LocalDate.parse(dob, formatter);

        String titleMessage = defaultService.checkNotUniqueData(birthday, postIndex, firstName, lastName, country, city, detailAddress);
        request.getSession().setAttribute(TextConstant.TITLE_PARAM_REGISTRATION, localeService.defineMessageManager(userLocale).getMessage(titleMessage));
        page = ConfigurationManager.getProperty("path.page.user");

        if (TextConstant.SUCCESS_OPERATION.equals(titleMessage)) {
            try {
                long userId = Long.parseLong(request.getParameter(TextConstant.USER_ID));
                User user = userReceiver.findUserByID(userId);
                userReceiver.setUserFeatures(user, firstName, lastName, userId, birthday, country, city, detailAddress, postIndex);
                if (userReceiver.updateUser(user, user.getAddress())) {
                    LOGGER.log(Level.INFO, "Try to send message to user email...");
                    mailService.sendFromEmail(context, user.getEmail(), messageManager.getMessage("message.welcome"),
                            messageManager.getMessage("message.hello") + firstName + messageManager.getMessage("message.update"));
                    request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                    request.getSession().setAttribute(TextConstant.TITLE_UPDATE, TextConstant.EMPTY_STRING);
                    LOGGER.log(Level.INFO, "Successful updating!");
                    LOGGER.log(Level.INFO, "Successful message sending.");
                } else {
                    request.getSession().setAttribute(TextConstant.ERROR_MESSAGE_PARAM, messageManager.getMessage("message.loginerror"));
                    LOGGER.log(Level.INFO, "Updating user profile has been failed.");
                }
            } catch (ServiceTechnicalException e) {
                LOGGER.catching(e);
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
        }
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
