package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;

public class PictureUpdateCommand implements ActionCommand{
    private String pictureFileName;
    private UserService userService;

    public PictureUpdateCommand(String pictureFileName,  UserService userService){
        this.pictureFileName = pictureFileName;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        String userIdString = request.getParameter(TextConstant.USER_ID);
        long userId = Long.parseLong(userIdString);
        try {
        User user = userService.findUserByID(userId);
        user.setImagePath("\\resource\\image\\user\\"+pictureFileName);
        userService.updateUser(user, user.getAddress());
        request.getSession().setAttribute(TextConstant.USER_PARAM, user);
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        page = ConfigurationManager.getProperty("path.page.user");
        router.setPagePath(page);
        return router;
    }
}
