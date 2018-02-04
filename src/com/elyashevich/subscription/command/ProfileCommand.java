package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfileCommand implements ActionCommand {
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String TYPE = "type";
    private UserService userReceiver;
    private String path;
    private long id;

    public ProfileCommand(UserService userReceiver){
        this.userReceiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;

        String firstName = request.getParameter(TextConstant.FIRST_NAME);
        String lastName = request.getParameter(TextConstant.LAST_NAME);
        String country = request.getParameter(TextConstant.COUNTRY_PARAM);
        String city = request.getParameter(TextConstant.CITY);
        String postIndex = request.getParameter(TextConstant.POST_INDEX);
        String detailAddress = request.getParameter(TextConstant.DETAIL_ADDRESS);
        String dob = request.getParameter(TextConstant.DOB);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate birthday = LocalDate.parse(dob, formatter);

        long userId = Long.parseLong(request.getParameter(ID));
        UserValidator validator = new UserValidator();
        UserService userService = new UserService();
            try {
                User user = userService.findUserByID(userId);
                if (firstName!=null && lastName!=null) {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                }
                user.setId(userId);
                user.setBirthday(birthday);
                Address address = userReceiver.getAddress(country, city, postIndex, detailAddress);
                user.setAddress(address);
                if (userReceiver.updateUser(user, address)){
                    MailCommand.sendFromEmail(request, user.getEmail(), MessageManager.EN.getMessage("message.welcome"),
                            "Здравствуйте, "+firstName+"! Вы только что обновили свои данные в Subscription!");
//                    request.setAttribute("titleMessage", LocaleService.defineMessageManager(request).getMessage("message.registrationsuccess"));
                    request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                    router.setRoute(Router.RouteType.REDIRECT);
                    page = ConfigurationManager.getProperty("path.page.user");
                } else{
                    request.setAttribute("errorLoginPassMessage", MessageManager.EN.getMessage("message.loginerror"));
                }
            } catch (ServiceTechnicalException e) {
                request.setAttribute("errorLoginPassMessage", MessageManager.EN.getMessage("message.loginerror"));
                // page = ConfigurationManager.getProperty("path.page.error");
            }
        router.setPagePath(page);
        return router;
    }
}
