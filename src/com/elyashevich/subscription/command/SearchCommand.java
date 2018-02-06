package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;

public class SearchCommand implements ActionCommand {
    private static final String SEARCH = "searchData";
    private static final String CRITERIA = "criteria";
    private PaperService paperReceiver;

    public SearchCommand(PaperService paperReceiver){
        this.paperReceiver = paperReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.main");
        String dataCriteria = request.getParameter(CRITERIA);
        String data = request.getParameter(SEARCH);
        User user = (User) request.getSession().getAttribute(TextConstant.USER_PARAM);
        try {
                request.setAttribute(TextConstant.PAPERS_RESTRICTION_PARAM, paperReceiver.findAllByDescription(user, data, dataCriteria));
        } catch (ServiceTechnicalException e){
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setPagePath(page);
        return router;
    }
}
