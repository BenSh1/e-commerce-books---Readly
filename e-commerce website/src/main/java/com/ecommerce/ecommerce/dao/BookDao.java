package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;

public interface BookDao {
    public Book findBookByName(String theBookName);

}
