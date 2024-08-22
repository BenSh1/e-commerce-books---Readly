package com.ecommerce.ecommerce.ben;




/*
    @Transactional
    public Order checkingQuantityEachBookInCart(List<CartItems> cartItems) {

        //update the stock for each book in the cartItems
        for(CartItems cartItem : cartItems) {

            Book book = bookDao.findByIdWithLock((long)cartItem.getBook().getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.getStock() < cartItem.getQuantity()) {
                if(cartItem.getQuantity() == 1){
                    cartService.removeBookFromCart(book.getBookId(),currentUser);
                }
                model.addAttribute("titleOfBook", book.getTitle());
                return "bookIsOutOfStock";
                //throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }

            book.setStock(book.getStock() - cartItem.getQuantity());
            bookService.addBook(book);
        }

    }

 */



/*
    @Transactional
    public Order processOrder(User currentUser, List<CartItems> cartItems) {

        try{
            //update the stock for each book in the cartItems
            for (CartItems cartItem : cartItems) {
                Book book = bookDao.findByIdWithLock((long)cartItem.getBook().getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                if (book.getStock() < cartItem.getQuantity()) {
                    throw new RuntimeException("Not enough stock for book: " + book.getTitle());
                }
                book.setStock(book.getStock() - cartItem.getQuantity());
                bookDao.save(book);
            }
        }
        catch(RuntimeException e){
            throw e;
        }

        // Process the order as usual
        List<OrderDetails> orderDetails = cartItems.stream().map(cartItem -> {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setBook(cartItem.getBook());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getBook().getPrice());
            return orderDetail;
        }).collect(Collectors.toList());

        //return orderRepository.save(new Order(orderDetails, currentUser));
        return this.createOrder(orderDetails, currentUser);

    }

 */



