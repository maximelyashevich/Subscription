package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class PictureUpdateCommand implements ActionCommand {
    private String pictureFileName;
    private UserService userService;
    private PaperService paperService;
    private static final Logger LOGGER = LogManager.getLogger();

    public PictureUpdateCommand(String pictureFileName, UserService userService, PaperService paperService) {
        this.pictureFileName = pictureFileName;
        this.userService = userService;
        this.paperService = paperService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;

        String userIdString = request.getParameter(TextConstant.USER_ID);
        String paperIdString = request.getParameter(TextConstant.PAPER_ID);
        String secret = request.getParameter(TextConstant.SECRET);

        LOGGER.log(Level.INFO, "Start picture updating...");
        switch (secret) {
            case TextConstant.USER_PARAM:
                long userId = Long.parseLong(userIdString);
                try {
                    User user = userService.findUserByID(userId);
                    user.setImagePath(TextConstant.PREFIX_IMAGE_USER + pictureFileName);
                    userService.updateUser(user, user.getAddress());
                    request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                    LOGGER.log(Level.INFO, "Successful picture updating.");
                } catch (ServiceTechnicalException e) {
                    throw new CommandTechnicalException(e.getMessage(), e.getCause());
                }
                page = ConfigurationManager.getProperty("path.page.user");
                break;
            case TextConstant.PAPER_PARAM:
                long paperId = Long.parseLong(paperIdString);
                try {
                    PaperEdition paperEdition = paperService.findPaperById(paperId);
                    paperEdition.setImagePath(TextConstant.PREFIX_IMAGE_USER + pictureFileName);
                    paperService.updatePaperEdition(paperEdition);
                    request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperService.findAll());
                    LOGGER.log(Level.INFO, "Successful picture updating.");
                } catch (ServiceTechnicalException e) {
                    throw new CommandTechnicalException(e.getMessage(), e.getCause());
                }
                page = ConfigurationManager.getProperty("path.page.admin");
                break;
        }
        router.setPagePath(page);
        return router;
    }
}
