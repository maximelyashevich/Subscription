package com.elyashevich.subscription.servlet;

import com.elyashevich.subscription.command.ActionCommand;
import com.elyashevich.subscription.command.PictureUpdateCommand;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100)
public class FileUploadingServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "resource\\image\\user";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        LocaleService localeService = new LocaleService();
        MessageManager messageManager = localeService.defineMessageManager(userLocale);

        String applicationPath = request.getServletContext().getRealPath(TextConstant.EMPTY_STRING);
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        String fileName = null;
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
            break;
        }

        ActionCommand command = new PictureUpdateCommand(fileName, new UserService(), new PaperService());
        try {
            Router router = command.execute(request);
            if (router.getPagePath() != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
            } else {
                String page = ConfigurationManager.getProperty("path.page.index");
                request.getSession().setAttribute(TextConstant.NULL_PAGE, messageManager.getMessage("message.nullPage"));
                response.sendRedirect(request.getContextPath() + page);
            }
        } catch (CommandTechnicalException e) {
            request.getSession().setAttribute(TextConstant.EXCEPTION_CAUSE, e.getCause().toString());
            request.getSession().setAttribute(TextConstant.EXCEPTION_MESSAGE, e.getMessage());
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error")).forward(request, response);
        }
    }


    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(TextConstant.SEPARATOR);
        for (String token : tokens) {
            if (token.trim().startsWith(TextConstant.FILENAME)) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return TextConstant.EMPTY_STRING;
    }
}
