package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.RegistrationService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {

    private RegistrationService registrationReceiver;

    public RegistrationCommand(RegistrationService registrationReceiver){
        this.registrationReceiver = registrationReceiver;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
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

        UserValidator validator = new UserValidator();
        UserService userService = new UserService();
        LocaleService localeService = new LocaleService();
        //  && validator.isCountryExists(country)
        if (validator.isLoginAndPasswordCorrect(userName, password) &&
                validator.isUserDataCorrect(firstName, lastName, email)
              ){
            try {
                User user = userService.getUser(dob, firstName, lastName, email, userName, password);
                Address address = userService.getAddress(country, city, postIndex, detailAddress);
                if (registrationReceiver.createUserWithEncryption(user, address)){
                    MailCommand.sendFromEmail(request, email, MessageManager.EN.getMessage("message.welcome"),
                            "Здравствуйте, "+firstName+"! Мы очень рады, что Вы решили попробовать Subscription!");
                    request.setAttribute("titleMessage", localeService.defineMessageManager(userLocale).getMessage("message.registrationsuccess"));
                    page = ConfigurationManager.getProperty("path.page.login");
                } else{
                    request.setAttribute("errorLoginPassMessage", MessageManager.EN.getMessage("message.loginerror"));
                }
            } catch (ServiceTechnicalException e){
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
        } else{
            request.setAttribute("titleMessage", MessageManager.EN.getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        router.setPagePath(page);
         return router;
    }

}
