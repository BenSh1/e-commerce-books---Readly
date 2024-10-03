package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.dao.RoleDao;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeRoles();
        initializeBooks();
        initializeUsers();
    }



    /**
     * Initializes the roles in the Role table if no roles are present.
     */
    private void initializeRoles() {
        int desiredNumberOfRowsInRoleTable = 3;

        if (roleDao.count() == 0) {
            List<String> rolesList = List.of("ROLE_CUSTOMER", "ROLE_MANAGER", "ROLE_ADMIN");

            for (int i = 0; i < desiredNumberOfRowsInRoleTable; i++) {
                Role role = new Role();
                role.setName(rolesList.get(i));
                roleDao.save(role);
            }
            System.out.println("Initial data saved to Role's Table");
        }
    }

    /**
     * Initializes the books in the Book table if no books are present.
     */
    private void initializeBooks() {
        if (bookDao.count() == 0) {

            List<Book> books = List.of(
                    new Book("Everything Is F*cked", "Mark Manson",
                            "Everything is F*cked by Mark Manson is a self-help book that explores the paradoxical nature of hope and how our values shape the way we approach life. It offers insight into the importance of finding meaning in our lives and navigating the challenges of existence.",
                            "Personal Development", 20, 10, "active", "Everything Is Fcked.jpg"),

                    new Book("The Subtle Art of Not Giving a F*ck", "Mark Manson",
                            "The Subtle Art of Not Giving a F*ck: A Counterintuitive Approach to Living a Good Life is a 2016 nonfiction self-help book by American blogger and author Mark Manson.[1] The book covers Manson's belief that life's struggles give it meaning and argues that typical self-help books offer meaningless positivity which is neither practical nor helpful, thus improperly approaching the problems many individuals face.",
                            "Personal Development", 25, 10, "active", "The Subtle Art of Not Giving a Fck.jpg"),

                    new Book("12 Rules for Life", "Jordan Peterson",
                            "12 Rules for Life: An Antidote to Chaos is a 2018 self-help book by the Canadian clinical psychologist Jordan Peterson. It provides life advice through essays in abstract ethical principles, psychology, mythology, religion, and personal anecdotes. The book topped bestseller lists in Canada, the United States, and the United Kingdom, and had sold over ten million copies worldwide, as of May 2023.[1] Peterson went on a world tour to promote the book, receiving much attention following an interview with Channel 4 News.[2][3] The book is written in a more accessible style than his previous academic book, Maps of Meaning: The Architecture of Belief (1999).[9] A sequel, Beyond Order: 12 More Rules for Life, was published in March 2021.",
                            "Self-help, psychology, philosophy", 25, 10, "active", "12 Rules for Life.jpg"),

                    new Book("From Animals into Gods: A Brief History of Humankind", "Yuval Noah Harari",
                            "In \"From Animals into Gods: A Brief History of Humankind,\" Yuval Noah Harari takes readers on an enthralling journey through the annals of history, \n" +
                                    "detailing the transformative events that catapulted Homo sapiens from mere creatures of the animal kingdom to the architects of civilizations \n" +
                                    "and masters of the planet.",
                            "History, social philosophy", 20, 10, "active", "From Animals into Gods A Brief History of Humankind.jpg")
            );

            for (Book book : books) {
                bookDao.save(book);
            }

            System.out.println("Initial data saved to Book's Table");
        }
    }

    /**
     * Initializes the users in the User table if no users are present.
     */
    private void initializeUsers() {
        if (userDao.count() == 0) {
            User firstUser = new User("israel",
                    "$2a$10$Wor6K0NPS6z2m55ur9PakOh6lgtrdantPus8fwOuVZ3Cl9OQXeDkq", // password: 123
                    "israel", true, "israeli", "0521234567", "ben@gmail.com",
                    "Israel", "haifa", "ramat almogi", "12", "12312", "123456789",
                    "visa", 12, 2030);

            firstUser.setRoles(Arrays.asList(
                    roleDao.findRoleByName("ROLE_CUSTOMER"),
                    roleDao.findRoleByName("ROLE_MANAGER"),
                    roleDao.findRoleByName("ROLE_ADMIN")
            ));

            userDao.save(firstUser);
            System.out.println("Initial data saved to User's Table");
        }
    }

}