package com.elyashevich.subscription.command;

import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class SearchCommand implements ActionCommand {
    private static final String SEARCH = "searchData";
    private static final String CRITERIA = "criteria";
    private PaperService userReceiver;

    public SearchCommand(PaperService userReceiver){
        this.userReceiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.main");
        String dataCriteria = request.getParameter(CRITERIA);
        String data = request.getParameter(SEARCH);
        try {
                request.setAttribute("papers", userReceiver.findAllByDescription(data, dataCriteria));
        } catch (ServiceTechnicalException e) {
            e.printStackTrace();
        }
        router.setPagePath(page);
        return router;
    }
}
