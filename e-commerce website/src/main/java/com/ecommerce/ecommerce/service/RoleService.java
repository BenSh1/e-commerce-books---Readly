package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.RoleDao;
import com.ecommerce.ecommerce.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleDao roleDao;


    public List<Role> getRoles() {
        return roleDao.getAllRoles();
    }



}
