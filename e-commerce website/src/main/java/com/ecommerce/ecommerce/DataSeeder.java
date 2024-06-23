package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.dao.RoleDao;
import com.ecommerce.ecommerce.dao.UserDao;
//import com.ecommerce.ecommerce.dao.UserRoleDao;
//import com.ecommerce.ecommerce.dao.UserRoleDaoImpl;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;
/*
    @Autowired
    private UserRoleDao userRoleDaoDao;

 */

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if data already exists (optional)
        int desiredNumberOfRowsInRoleTable = 3;
        int desiredNumberOfRowsInUserTable = 3;
        int desiredNumberOfRowsInUser_RoleTable = 6;

        if (roleDao.count() == 0) {
            List<String> rolesList = new ArrayList<>();
            rolesList.add("ROLE_CUSTOMER");
            rolesList.add("ROLE_MANAGER");
            rolesList.add("ROLE_ADMIN");

            for (int i = 0; i < desiredNumberOfRowsInRoleTable; i++) {
                    Role role = new Role();
                    role.setName(rolesList.get(i));
                    roleDao.save(role);
                }
            System.out.println("Initial data saved to Role's Table");
        }
        else
        {
            System.out.println("Role's table already has data");
        }
/*
        if (userDao.count() == 0) {
            List<String> usersList = new ArrayList<>();
            User user = new User();
            user.setUserName("ben");
            user.setLastName("Sharabi");
            user.setEmail("ben@gmail.com");
            user.setPassword("ben@gmail.com");



            for (int i = 0; i < desiredNumberOfRowsInRoleTable; i++) {
                Role role = new Role();
                role.setName(stringList.get(i));
                roleDao.save(role);
            }
            System.out.println("Initial data saved to Role's Table");
        }
        else
        {
            System.out.println("Role's table already has data");
        }


 */




    }
}