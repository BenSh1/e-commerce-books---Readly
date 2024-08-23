package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.dto.WebUser;
import com.ecommerce.ecommerce.entity.User;

public class UserConverter {

    // Converts User entity to WebUser DTO
    public static WebUser convertToWebUser(User user) {
        WebUser webUser = new WebUser();
        webUser.setUserName(user.getUserName());
        webUser.setFirstName(user.getFirstName());
        webUser.setLastName(user.getLastName());
        webUser.setEmail(user.getEmail());
        webUser.setPhone(user.getPhone());
        webUser.setCountry(user.getCountry());
        webUser.setCity(user.getCity());
        webUser.setStreetAddress(user.getStreetAddress());
        webUser.setApartmentNumber(user.getApartmentNumber());
        webUser.setZipCode(user.getZipCode());
        webUser.setCreditCardNumber(user.getCreditCardNumber());
        webUser.setCreditCardCompany(user.getCreditCardCompany());
        webUser.setCardExpiryMonth(user.getCardExpiryMonth());
        webUser.setCardExpiryYear(user.getCardExpiryYear());
        // Password is not set here for security reasons or if you want to let the user input it.
        return webUser;
    }

    // Converts WebUser DTO back to User entity
    public static User convertToUser(WebUser webUser, User user) {
        user.setUserName(webUser.getUserName());
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEmail(webUser.getEmail());
        user.setPhone(webUser.getPhone());
        user.setCountry(webUser.getCountry());
        user.setCity(webUser.getCity());
        user.setStreetAddress(webUser.getStreetAddress());
        user.setApartmentNumber(webUser.getApartmentNumber());
        user.setZipCode(webUser.getZipCode());
        user.setCreditCardNumber(webUser.getCreditCardNumber());
        user.setCreditCardCompany(webUser.getCreditCardCompany());
        user.setCardExpiryMonth(webUser.getCardExpiryMonth());
        user.setCardExpiryYear(webUser.getCardExpiryYear());
        // Optionally update the password if it has been changed
        return user;
    }
}
