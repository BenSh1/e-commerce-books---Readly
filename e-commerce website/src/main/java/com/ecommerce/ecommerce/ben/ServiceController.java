package com.ecommerce.ecommerce.ben;
/*
//import com.ecommerce.ecommerce.dao.CustomerDAO;
//import com.ecommerce.ecommerce.dao.CustomerDAOImpl;
import com.ecommerce.ecommerce.dao.CustomerDAO;
import com.ecommerce.ecommerce.dao.UserDao;
//import com.ecommerce.ecommerce.repository.CustomerRepository;

import com.ecommerce.ecommerce.entity.Customer;
//import com.ecommerce.ecommerce.repository.CustomerRepository;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ServiceController {

    @Autowired
    private CustomerDAO customerDAO;


    @Autowired
    private UserDao userDAO;

    public void saveCustomer(Customer customer) {
        customerDAO.save(customer);
        //customerRepository.save(customer);
    }




    public void saveUser(User user) {
        userDAO.save(user);
        //customerRepository.save(customer);
    }

    public List<Customer> queryForCustomersByUserNameAndPassword(String userName , String password){

        // get a list of students
        List<Customer> theCustomers= customerDAO.findByUserNameAndPass(userName , password);
        //Customer theCustomer= customerDAO.findByUserNameAndPass(userName , password);

        // display list of students

        for(Customer tempStudent : theCustomers) {
            if(tempStudent.getUserName().equals(userName) && tempStudent.getPassword().equals(password)) {
                return theCustomers;
            }
            System.out.println(tempStudent);
        }
        return null;
    }





}

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
    //---------------------JPARepository-------------------------------------------------
    /*
    @Autowired
    private CustomerRepository customerRepository;



    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

     */

    /*
    public List<Customer> getAllBooks() {
        return customerRepository.findAll();
    }

     */
    /*
    public Customer getCustomer(String userName, String password) {

        return customerRepository.findById(userName).orElse(null);
    }

     */
    /*
    public boolean checkLogin(String username, String password) {
        Customer user = customerRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

     */

    /*
    public void createCustomer(Customer customer) {
        customerDAO.save(customer);
        //customerRepository.save(customer);
    }
    */


