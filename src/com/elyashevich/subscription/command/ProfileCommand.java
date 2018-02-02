package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class ProfileCommand implements ActionCommand {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String TYPE = "type";
    private UserService userReceiver;
    private String path;
    private long id;

    public ProfileCommand(UserService userReceiver){
        this.userReceiver = userReceiver;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
//        String email = request.getParameter(EMAIL);
//        String dob = request.getParameter(DOB);
//        String userName = request.getParameter(LOGIN);
//        String password = request.getParameter(PASSWORD);
        String imagePath = path;
//        ClientType clientType = ClientType.valueOf(request.getParameter(TYPE).toUpperCase());
//        BigDecimal amount = new BigDecimal(request.getParameter(AMOUNT));
        long userId;
        if (id!=0){
            userId = id;
        } else{
            userId = Long.parseLong(request.getParameter(ID));
        }

        UserValidator validator = new UserValidator();
        UserService userService = new UserService();
            try {
                User user = userService.findUserByID(userId);
                if (firstName!=null && lastName!=null) {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                }
                user.setImagePath(imagePath);
                user.setId(userId);
                if (userReceiver.updateUser(user)){
                    MailCommand.sendFromEmail(request, user.getEmail(), MessageManager.EN.getMessage("message.welcome"),
                            "Здравствуйте, "+firstName+"! Вы только что обновили свои данные в Subscription!");
                    request.setAttribute("titleMessage", LocaleService.defineMessageManager(request).getMessage("message.registrationsuccess"));
                    request.setAttribute("user", user);
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
