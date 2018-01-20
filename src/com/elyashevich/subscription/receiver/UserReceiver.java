//package com.elyashevich.subscription.receiver;
//
//import com.elyashevich.subscription.dao.UserDAO;
//import com.elyashevich.subscription.entity.User;
//import com.elyashevich.subscription.exception.DAOTechnicalException;
//import com.elyashevich.subscription.exception.ReceiverTechnicalException;
//import com.elyashevich.subscription.service.LoginService;
//import com.elyashevich.subscription.util.Encryption;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//public class UserReceiver {
//    public String findUser(String login, String password) throws ReceiverTechnicalException {
//        LoginService loginService = new LoginService();
//        if (loginService.checkUserData(login, password)){
//            return loginService.findUserWithEncryption(login, password);
//        } else{
//
//        }
//    }
//    public boolean addUser(String date, String firstName, String lastName, String email, String login, String password) throws ReceiverTechnicalException {
//        UserDAO userDAO = new UserDAO();
//        User user = new User();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        LocalDate birthday = LocalDate.parse(date, formatter);
//
//        user.setBirthday(birthday);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        user.setUserName(login);
//        user.setPassword(Encryption.encryptPassword(password));
//        try {
//            return userDAO.create(user);
//        } catch (DAOTechnicalException e) {
//            throw new ReceiverTechnicalException(e.getMessage(), e.getCause());
//        }
//    }
//}
