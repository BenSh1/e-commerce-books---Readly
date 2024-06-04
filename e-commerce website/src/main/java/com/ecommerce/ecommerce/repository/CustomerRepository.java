package com.ecommerce.ecommerce.repository;


import com.ecommerce.ecommerce.entity.Customers;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

}