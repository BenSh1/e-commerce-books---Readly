package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Customer;

import java.util.List;

//DAO = Data Access Object
public interface CustomerDAO {

    void save(Customer theCustomer);

    Customer findById(Integer id);

    List<Customer> findAll();


    List<Customer> findByUserNameAndPass(String userName , String password);
    //Customer findByUserNameAndPass(String userName , String password);


}
