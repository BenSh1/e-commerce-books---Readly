package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.dao.RoleDao;
import com.ecommerce.ecommerce.dao.UserDao;
//import com.ecommerce.ecommerce.dao.UserRoleDao;
//import com.ecommerce.ecommerce.dao.UserRoleDaoImpl;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;
/*
    @Autowired
    private UserRoleDao userRoleDaoDao;

 */

    //private BCryptPasswordEncoder passwordEncoder;

/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

 */
/*
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
*/


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if data already exists (optional)
        int desiredNumberOfRowsInRoleTable = 3;
        int desiredNumberOfRowsInUserTable = 3;
        int desiredNumberOfRowsInUser_RoleTable = 6;

        int desiredNumberOfRowsInBookTable = 3;


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


        if (bookDao.count() == 0) {

            Book firstBook = new Book("Everything Is F*cked",
                    "Mark Manson",
                    "Mark Manson's Everything is F*cked is a book about hope and much, much more\n" +
                            "Chances are you’ve experienced the feeling of hopelessness before. The dreary sense that everything you’re doing is worthless—that there’s just no point in trying anymore. Mark Manson’s Everything is F*cked is a book about hope and much, much more. Manson takes the reader into existentialist territory by first reflecting on what hope is and its relationship to meaning, and then expertly painting a picture of how hope fits into the world we live in today. \n" +
                            "Mark’s writing style strikes a rare balance between witty humor and philosophical self-help. Almost creating his own genre, Manson maintains his genuine appreciation for comedy while bringing the reader life-changing information that is both discernible and interesting. Mark’s latest book is full of wisdom and is a timely piece considering the apparent lack of hope in today’s society. Here are a few of our favorite key lessons and timeless quotes from Everything is F*cked.\n",
                    "Personal Development",
                    20,
                    10,
                    "Everything Is Fcked.jpg"
                    );
            bookDao.save(firstBook);

            Book secondBook = new Book("The Subtle Art of Not Giving a F*ck",
                    "Mark Manson",
                    "The Subtle Art of Not Giving a F*ck: A Counterintuitive Approach to Living a Good Life is a 2016 nonfiction self-help book by American blogger and author Mark Manson.[1] The book covers Manson's belief that life's struggles give it meaning and argues that typical self-help books offer meaningless positivity which is neither practical nor helpful, thus improperly approaching the problems many individuals face.",
                    "Personal Development",
                    25,
                    10,
                    "The Subtle Art of Not Giving a Fck.jpg");
            bookDao.save(secondBook);

            Book thirdBook = new Book("12 Rules for Life",
                    "Jordan Peterson",
                    "12 Rules for Life: An Antidote to Chaos is a 2018 self-help book by the Canadian clinical psychologist Jordan Peterson. It provides life advice through essays in abstract ethical principles, psychology, mythology, religion, and personal anecdotes.",
                    "Self-help , psychology , philosophy",
                    25,
                    10,
                    "12 Rules for Life.jpg");
            bookDao.save(thirdBook);

            System.out.println("Initial data saved to Book's Table");
        }
        else
        {
            System.out.println("Role's table already has data");
        }

        if (userDao.count() == 0) {
            //the password is 123
            User firstUser = new User("israel",
                    "$2a$10$Wor6K0NPS6z2m55ur9PakOh6lgtrdantPus8fwOuVZ3Cl9OQXeDkq",
                    "israel",
                    true,
                    "israeli",
                    "0521234567",
                    "ben@gmail.com",
                    "Israel",
                    "haifa",
                    "ramat almogi",
                    "12",
                    "12312",
                    "123456789",
                    "visa",
                    12,
                    2030
                    );


            //firstUser.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_ADMIN")));
            firstUser.setRoles(Arrays.asList(
                    roleDao.findRoleByName("ROLE_CUSTOMER"),
                    roleDao.findRoleByName("ROLE_MANAGER"),
                    roleDao.findRoleByName("ROLE_ADMIN")
            ));
            userDao.save(firstUser);
            System.out.println("Initial data saved to User's Table");

        }
        else{
            System.out.println("User's table already has data");
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