
/*
package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dao.ResetTokenRepository;
import com.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    // Step 1: Show "Forgot Password" Form
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password"; // Renders the forgot-password.html page
    }

    // Step 2: Handle "Forgot Password" Form Submission
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            userService.generateResetToken(email);
            model.addAttribute("message", "A password reset link has been sent to your email.");
        } catch (Exception e) {
            model.addAttribute("error", "Email address not found.");
        }
        return "forgot-password"; // Return the same page with feedback
    }

    // Step 3: Show Reset Password Form
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        ResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Token has expired.");
            return "error"; // Display an error page
        }

        model.addAttribute("token", token);
        return "reset-password"; // Renders reset-password.html
    }

    // Step 4: Handle Reset Password Submission
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                Model model) {
        ResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Token has expired.");
            return "error"; // Display an error page
        }

        userService.updatePassword(resetToken.getUser(), password);
        model.addAttribute("message", "Your password has been successfully reset.");
        return "success"; // Display a success page
    }
}

 */
