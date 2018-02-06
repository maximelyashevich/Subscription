package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;

public class HidePaperCommand implements ActionCommand {
    private PaperService paperService;

    public HidePaperCommand(PaperService paperService){
        this.paperService = paperService;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        long paperId = Long.parseLong(request.getParameter(TextConstant.PAPER_ID));
        PaperEdition paperEdition;
        try {
            paperEdition = paperService.findPaperById(paperId);
            paperEdition.setAvailability(!paperEdition.isAvailability());
            if (paperService.updatePaperEdition(paperEdition)){
                router.setRoute(Router.RouteType.REDIRECT);
                request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperService.findAll());
                page = ConfigurationManager.getProperty("path.page.admin");
                router.setPagePath(page);
            }
            else{
                System.out.println("ERRRRROR!!!!!");
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        return router;
    }
}
