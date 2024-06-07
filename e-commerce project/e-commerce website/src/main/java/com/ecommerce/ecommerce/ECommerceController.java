package com.ecommerce.ecommerce;

//import com.ecommerce.ecommerce.dao.CustomerDAO;
//import com.ecommerce.ecommerce.dao.CustomerDAOImpl;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.Data_Transfer_Object.FormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ECommerceController {


    @Autowired
    private ServiceController customerService;


    @GetMapping("/greeting")
    public String helloWorld(){
        return "Hello World from Spring Boot!";
    }


    @GetMapping("/goodbye")
    public String goodbye(){
        return "Goodbye from Spring Boot!";
    }

    @GetMapping("/")
    public String getHtmPage(){
        return "index";
    }

    @GetMapping("/page1")
    public String getPage1(){
        return "page1";
    }

    @GetMapping("/page2")
    public String getPage2(){
        return "page2";
    }

    @GetMapping("/register")
    public String getPageRegistration(Model model){
        model.addAttribute("customer", new Customer());

        return "registration";
    }

    //@PostMapping("/submit")
    @PostMapping("/successfulRegister")
    public String submitForm(@ModelAttribute Customer customer, Model model) {
        // Handle form data
        //customerService.saveCustomer(customer);
        model.addAttribute("customer", customer);
        //System.out.println("customer id :  " + customer.getCustomerId());
        customerService.saveCustomer(customer);
        //customerDAO.save(customer);
        return "registerSuccessed";
    }

    @GetMapping("/login")
    public String getPageLogin(Model model){
        //model.addAttribute("customer", new Customer());
        return "login";
    }

    @PostMapping("/successfulLogin")
    public String getResultLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 Model model) {
        // Handle form data
        //customerService.getCustomer(userName,password);
        //model.addAttribute("customer", customer);

        System.out.println("customer userName :  " + userName);
        System.out.println("customer password :  " + password);
        List<Customer> theCustomers = customerService.queryForCustomersByUserNameAndPassword(userName , password);
        //model.addAttribute("customer", theCustomers.getFirst());

        if (theCustomers != null)
        {
            // Add the first customer to the model
            if (!theCustomers.isEmpty()) {
                model.addAttribute("customer", theCustomers.getFirst());
            }
            return "loginSuccessed";
        }
        return "loginFail";


    }


    /*
    @GetMapping("/view")
    public String viewHtml(Model model) throws IOException {
        File htmlFile = new File("path/to/your/file.html");
        String htmlContent = myService.parseHtml(htmlFile);
        model.addAttribute("htmlContent", htmlContent);
        return "view";
    }
    @GetMapping("/register")
    public String getPageRegistration(Model model){
        File htmlFile = new File("path/to/your/file.html" "");
        model.addAttribute("customer", new Customer());
        return "registration";
    }

     */


    /*
    @GetMapping("/register")
    public String getPageRegistration(){
        return "registration";
    }

    @PostMapping("/register")
    public String submitForm(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("customerId") int customerId,
                             @RequestParam("userName") String userName,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone) {

        Customer customer = new Customer(customerId,firstName,lastName,userName,password,email,phone);
        System.out.println("customer id : " + customer.getCustomerId());
        customerService.createConsumer(customer);
        return "redirect:/registerSuccessed"; // Redirect to a success page
    }
    */


    /*
    @GetMapping("/register")
    public String getPageRegistration(){
        return "registration";
    }*/

    /*
    @PostMapping("/submit")
    public String submitForm(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("customerId") int customerId,
                             @RequestParam("userName") String userName,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone) {

        Customer customer = new Customer(customerId,firstName,lastName,userName,password,email,phone);
        System.out.println("customer id : " + customer.getCustomerId());
        customerService.createConsumer(customer);
        return "redirect:/registerSuccessed"; // Redirect to a success page
    }
    */
    @GetMapping("/register2")
    public String getPageRegistration2(Model model){
        model.addAttribute("formData", new FormData());
        return "registration2";
    }

    @GetMapping("/register3")
    public String getPageRegistration3(Model model){
        model.addAttribute("formData", new FormData());
        return "registration3";
    }
    /*
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute FormData formData, Model model) {
        // Handle form data
        model.addAttribute("formData", formData);
        return "registerSuccessed2";
    }
    */







}
