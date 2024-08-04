import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;//@PostMapping("/{cartId}/books/{bookId}")
@PostMapping("/cart/add")
public String addBookToCart(@PathVariable Long cartId, @PathVariable Long bookId, @RequestParam int quantity) {
        /*
        try {
            cartService.addToCart(cartId, bookId, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding book to cart");
        }

         */

    //cartService.addToCart(cartId, bookId ,quantity);

        /*
        Book book = bookService.findById(bookId);
        if (book != null) {
            cartService.addToCart2(book, quantity);
        }

         */

    // Redirect to cart view or display confirmation message (optional)
    return "redirect:/cart";
}

/*
    @GetMapping("/orderList")
    public String getOrderList(Model model) {

        List<Order> orders = orderRepository.findAll();

        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();


        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetails);


        return "orderList";

    }

 */


/*
    @GetMapping("/orderList2")
    public String getOrderList(HttpSession session, Model model) {

        //User currentUser = (User) session.getAttribute("user");
        //if (currentUser == null) {
        //    throw new RuntimeException("User not logged in");
        //}



        // Assume getOrderForUser returns an Order object for the current user
        //Order order = orderService.getOrderForUser(currentUser);

        List<Order> orders = orderRepository.findAll();
        //System.out.println("====================================orders.size()===================================== : " + orders.size());


        for (Order order : orders) {
            for(OrderDetails orderDetail : orderDetails) {
                if(order.getOrderId() == orderDetail.getOrder().getOrderId())
                {
                    order.add(orderDetail);
                }
            }
        }


        model.addAttribute("orders", orders);

       // System.out.println("orders.getFirst().getOrderDetails : " + orders.getFirst().getOrderDetails());


        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        System.out.println("=======================================================");
        System.out.println("==========orderDetails.getFirst().getBook().getTitle()================================="+ orderDetails.getFirst().getBook().getTitle());

        System.out.println("=======================================================");
        model.addAttribute("orderDetails", orderDetails);

        return "orderList";
    }


 */



