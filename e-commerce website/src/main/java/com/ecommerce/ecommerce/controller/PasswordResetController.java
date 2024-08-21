package com.ecommerce.ecommerce.controller;
/*
import com.ecommerce.ecommerce.dao.PasswordResetTokenRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.PasswordResetToken;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordResetService passwordResetService;


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    @PostMapping("/forgetPassword")
    public String getForgotPasswordForm() {
        return "/user/forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String processForgotPasswordForm(@RequestParam("email") String userEmail, Model model) {
        //User user = userRepository.findByEmail(userEmail);
        User user = userDao.findByEmail(userEmail);

        if (user == null) {
            model.addAttribute("errorMessage", "No account found for this email.");
            return "/user/forgetPassword";
        }

        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetTokenForUser(user, token);
        passwordResetService.sendPasswordResetEmail(user, token);

        model.addAttribute("successMessage", "Password reset link has been sent to your email.");
        return "/user/forgetPassword";
    }


    @GetMapping("/resetPassword")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        //PasswordResetToken resetToken = passwordResetService.findByToken(token);
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("errorMessage", "Invalid or expired token.");
            return "/user/resetPassword";
        }

        model.addAttribute("token", token);
        return "/user/resetPassword";
    }
    @PostMapping("/resetPassword")
    public String handleResetPassword(@RequestParam("token") String token, @RequestParam("password") String password, Model model) {
        //PasswordResetToken resetToken = passwordResetService.findByToken(token);
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("errorMessage", "Invalid or expired token.");
            return "/user/resetPassword";
        }

        User user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        //userRepository.save(user);
        userDao.save(user);

        model.addAttribute("successMessage", "Password has been reset successfully.");
        return "/user/resetPassword";
    }


}

 */

