package com.elyashevich.subscription.servlet;

import com.elyashevich.subscription.manager.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(location = "e:/tmp"
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5
)
public class FileUploadingServlet extends HttpServlet{
    private static final String UPLOAD_DIR = "uploads";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()){
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload file directory="+fileSaveDir.getAbsolutePath());
        String path="";
        for (Part part: request.getParts()){
            if (getSubmittedFileName(part)!=null){
                part.write(uploadFilePath + File.separator + getSubmittedFileName(part));
                System.out.println(uploadFilePath + File.separator + getSubmittedFileName(part));
                path+=getSubmittedFileName(part);
            }
        }

        System.out.println(path);
     //  copyFileUsingJava7Files(new File(uploadFilePath+File.separator+path), new File("web\\resource\\image\\user\\"+path));
       String path2= "C:\\Users\\Максим\\Desktop\\Programming\\Java\\Subscription\\web\\resource\\image\\user\\1.jpg";
        File source = new File(uploadFilePath + File.separator + path);
        File dest = new File(path2);
        copyDirectory(source, dest);
       request.getSession().setAttribute("imagePathNew", "/resource/image/user/1.jpg");
        String page = ConfigurationManager.getProperty("path.page.index");
        response.sendRedirect(request.getContextPath() + page);
    }
    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
    private static void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }
}




