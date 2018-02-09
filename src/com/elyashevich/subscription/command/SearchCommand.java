package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
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

public class SearchCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private PaperServiceImpl paperReceiver;

    SearchCommand(PaperServiceImpl paperReceiver) {
        this.paperReceiver = paperReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.main");
        String dataCriteria = request.getParameter(TextConstant.CRITERIA);
        String data = request.getParameter(TextConstant.SEARCH);
        User user = (User) request.getSession().getAttribute(TextConstant.USER_PARAM);
        try {
            request.setAttribute(TextConstant.PAPERS_RESTRICTION_PARAM, paperReceiver.findAllByDescription(user, data, dataCriteria));
            LOGGER.log(Level.INFO, "Successful searching operation.");
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setPagePath(page);
        return router;
    }
}
