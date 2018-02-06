package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.DefaultService;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.RegistrationService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private RegistrationService registrationReceiver;

    RegistrationCommand(RegistrationService registrationReceiver) {
        this.registrationReceiver = registrationReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page;
        UserService userService = new UserService();
        DefaultService defaultService = new DefaultService();
        LocaleService localeService = new LocaleService();
        LOGGER.log(Level.INFO, "Starting registration...");
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);

        String firstName = request.getParameter(TextConstant.FIRST_NAME);
        String lastName = request.getParameter(TextConstant.LAST_NAME);
        String email = request.getParameter(TextConstant.EMAIL);
        String dob = request.getParameter(TextConstant.DOB);
        String userName = request.getParameter(TextConstant.PARAM_NAME_LOGIN);
        String password = request.getParameter(TextConstant.PARAM_NAME_PASSWORD);
        String country = request.getParameter(TextConstant.COUNTRY_PARAM);
        String city = request.getParameter(TextConstant.CITY);
        String postIndex = request.getParameter(TextConstant.POST_INDEX);
        String detailAddress = request.getParameter(TextConstant.DETAIL_ADDRESS);

        MessageManager messageManager = localeService.defineMessageManager(userLocale);
        String titleMessage = defaultService.defineTitleMessage(firstName, lastName, detailAddress, userName, password, postIndex, country, city, dob);
        request.setAttribute(TextConstant.TITLE_PARAM_REGISTRATION, localeService.defineMessageManager(userLocale).getMessage(titleMessage));
        page = ConfigurationManager.getProperty("path.page.registration");
        if (TextConstant.SUCCESS.equals(titleMessage)) {
            try {
                if (registrationReceiver.findUserByLogin(userName)) {
                    LOGGER.log(Level.INFO, "Find user with same login");
                    request.getSession().setAttribute(TextConstant.TITLE_PARAM_REGISTRATION, messageManager.getMessage("message.loginEx"));
                } else if (registrationReceiver.findUserByEmail(email)) {
                    LOGGER.log(Level.INFO, "Find user with same email");
                    request.getSession().setAttribute(TextConstant.TITLE_PARAM_REGISTRATION, messageManager.getMessage("message.emailEx"));
                }
                if (!registrationReceiver.findUserByLogin(userName) && !registrationReceiver.findUserByEmail(email)){
                    User user = userService.getUser(dob, firstName, lastName, email, userName, password);
                    Address address = userService.getAddress(country, city, postIndex, detailAddress);
                    if (registrationReceiver.createUserWithEncryption(user, address)) {
                        MailCommand.sendFromEmail(request, email, messageManager.getMessage("message.welcome"),
                                "Здравствуйте, " + firstName + "! Мы очень рады, что Вы решили попробовать Subscription!");
                        request.getSession().setAttribute(TextConstant.TITLE_PARAM, localeService.defineMessageManager(userLocale).getMessage("message.registrationsuccess"));
                        page = ConfigurationManager.getProperty("path.page.login");
                        LOGGER.log(Level.INFO, "Successful registration!");
                    } else {
                        request.getSession().setAttribute(TextConstant.ERROR_MESSAGE_PARAM, messageManager.getMessage("message.loginerror"));
                        LOGGER.log(Level.INFO, "Registration has been failed.");
                    }
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
