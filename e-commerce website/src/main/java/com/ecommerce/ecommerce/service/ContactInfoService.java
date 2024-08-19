package com.ecommerce.ecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService {
    @Value("${contact.manager.email}")
    private String managerEmail;

    @Value("${contact.manager.phone}")
    private String managerPhone;

    public String getManagerEmail() {
        return managerEmail;
    }

    public String getManagerPhone() {
        return managerPhone;
    }
}
