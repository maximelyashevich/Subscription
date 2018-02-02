package com.elyashevich.subscription.servlet;

import com.elyashevich.subscription.command.ProfileCommand;
import com.elyashevich.subscription.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(location = "e:/tmp"
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5
)
public class FileUploadingServlet extends HttpServlet{
      private static final String UPLOAD_DIR = "C:\\Users\\Максим\\Desktop\\Programming\\Java\\Subscription\\web\\resource\\image\\user";
      private static final String ID = "userID";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
             //   LOGGER.error("No multipart");
                return;
            }
        try {
            response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setHeader("Cache-Control", "post-check=0,pre-check=0");
            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Pragma", "no-cache");
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        File file = null;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime now = LocalDateTime.now();
            String photoPath = null;
            String id = null;
            List<FileItem> multiparts = upload.parseRequest(request);
            String inputName;
              for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                photoPath = File.separator + id+dtf.format(now)+item.getName();
                file = new File(UPLOAD_DIR+photoPath);
                item.write(file);
            }
            else {
                inputName = item.getFieldName();
                file =new File(UPLOAD_DIR+photoPath);
                if (inputName.equalsIgnoreCase(ID)) {
                    id = item.getString();
                }
                item.write(file);
            }
        }
            ProfileCommand command=new ProfileCommand(new UserService());
            command.setPath("\\resource\\image\\user"+photoPath);
            command.setId(Integer.parseInt(id));
            Router router = command.execute(request);
            String page = router.getPagePath();
            response.sendRedirect(request.getContextPath() + page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




