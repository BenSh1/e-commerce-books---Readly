package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.entity.Cart;
import com.ecommerce.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookDao bookDao;

    @PostMapping("/{cartId}/books/{bookId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId, @PathVariable Long bookId, @RequestParam int quantity) {
        Cart cart = cartService.addToCart(cartId, bookId, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }
}
