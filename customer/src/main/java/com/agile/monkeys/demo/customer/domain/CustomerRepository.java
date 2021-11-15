package com.agile.monkeys.demo.customer.domain;

import com.agile.monkeys.demo.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Transactional(readOnly = true)
    @Query(value =
            "select * " +
            "from customers c " +
            "where c.active = true and (c.first_name ilike :query or c.last_name ilike :query) " +
            "order by c.last_name asc",
            nativeQuery = true)
    List<Customer> findByQuery(@Param("query") String query);
}
