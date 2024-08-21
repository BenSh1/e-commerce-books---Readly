package com.ecommerce.ecommerce.service;
/*
import com.ecommerce.ecommerce.dao.PasswordResetTokenRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.entity.PasswordResetToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class PasswordResetService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService; // Service to send emails

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(calculateExpiryDate(24*60)); // 24 hours expiration
        tokenRepository.save(myToken);
    }

    public void sendPasswordResetEmail(User user, String token) {
        String url = "http://localhost:8080/resetPassword?token=" + token;
        String message = "Click the following link to reset your password: " + url;
        emailService.sendSimpleMessage(user.getEmail(), "Reset Password", message);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}

 */

