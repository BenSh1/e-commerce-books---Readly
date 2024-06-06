package com.ecommerce.ecommerce;

//import com.ecommerce.ecommerce.dao.CustomerDAO;
//import com.ecommerce.ecommerce.dao.CustomerDAOImpl;
import com.ecommerce.ecommerce.repository.CustomerRepository;

import com.ecommerce.ecommerce.entity.Customer;
//import com.ecommerce.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;

@Service
public class ServiceController {
    /*
    @Autowired
    private CustomerDAO customerDAO;

    */

    /*
    public String parseHtml() throws IOException {
        File htmlFile = new ClassPathResource("registration.html").getFile();
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        return doc.outerHtml();
    }

    public void saveCustomer(String someData) {
        Customer entity = new Customer();
        entity.setSomeField(someData);
        myEntityDao.save(entity);
    }



     */

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }


    /*
    public void createCustomer(Customer customer) {
        customerDAO.save(customer);
        //customerRepository.save(customer);
    }
    */



}
