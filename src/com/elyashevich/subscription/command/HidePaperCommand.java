package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.impl.PaperServiceImpl;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class HidePaperCommand implements ActionCommand {
    private PaperServiceImpl paperServiceImpl;
    private static final Logger LOGGER = LogManager.getLogger();

    HidePaperCommand(PaperServiceImpl paperServiceImpl) {
        this.paperServiceImpl = paperServiceImpl;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.admin");
        PaperEdition paperEdition;

        LOGGER.log(Level.INFO, "Starting hide paper edition...");

        long paperId = Long.parseLong(request.getParameter(TextConstant.PAPER_ID));

        try {
            paperEdition = paperServiceImpl.findPaperById(paperId);
            paperEdition.setAvailability(!paperEdition.isAvailability());
            if (paperServiceImpl.updatePaperEdition(paperEdition)) {
                request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperServiceImpl.findAll());
            } else {
                LOGGER.log(Level.INFO, "Paper edition hiding has been failed.");
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
