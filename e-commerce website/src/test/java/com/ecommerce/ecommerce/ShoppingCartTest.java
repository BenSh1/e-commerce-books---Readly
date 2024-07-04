package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.dao.CartItemsRepository;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ShoppingCartTest {

    @Autowired
    private CartItemsRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AssertTrueValidator assertTrueValidator;

    @Test
    public void testAddOneCartItem(){
        Book book = entityManager.find(Book.class, 6);
        User customer = entityManager.find(User.class , 12);

        CartItems newItem = new CartItems();
        newItem.setUser(customer);
        newItem.setBook(book);
        newItem.setQuantity(1);
        newItem.setPrice(25);

        CartItems saveCartItems = cartRepo.save(newItem);

        assertTrue(saveCartItems.getCartItemsId() > 0);

    }
}
