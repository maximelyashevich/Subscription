//package com.elyashevich.subscription.servlet;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.util.Properties;
//
//@WebServlet(name="image",urlPatterns={"/image/*"})
//public class ImageServlet extends HttpServlet {
//    private static final Logger LOGGER = LogManager.getLogger();
//    private static final String INPUT_PATH = "im";
//    private static final String UPLOAD_DIR = "C:\\Users\\Максим\\Desktop\\Programming\\Java\\Subscription\\web";
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        LOGGER.info("ImageServlet starts work!");
//        InputStream fis;
//        Properties property = new Properties();
//
//        response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
//        response.setHeader("Cache-Control", "no-cache, must-revalidate");
//        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
//        response.setHeader("Cache-Control", "max-age=0");
//        response.setHeader("Pragma", "no-cache");
//        File file = null;
//        try {
////            FileItemFactory factory = new DiskFileItemFactory();
////            ServletFileUpload upload = new ServletFileUpload(factory);
//            String photoPath = null;
//            String inputName = null;
////            List<FileItem> multiparts = upload.parseRequest(request);
//
//            if (inputName == null) {
//                file = new File(UPLOAD_DIR + "\\resource\\image\\user\\user.jpg");
//            } else{
//                file = new File(UPLOAD_DIR + inputName);
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        response.setHeader("Content-Type","image/jpeg");
//        response.setHeader("Content-Length", String.valueOf(file.length()));
//        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getPath() + "\"");
//        response.getOutputStream().write(Files.readAllBytes(file.toPath()));
//        LOGGER.info("Image was send!");
//        response.getOutputStream().flush();
//    }
//}
