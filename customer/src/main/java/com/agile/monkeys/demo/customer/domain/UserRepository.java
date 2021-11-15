package com.agile.monkeys.demo.customer.domain;

import com.agile.monkeys.demo.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String> {

    @Transactional(readOnly = true)
    @Query(value =
            "SELECT * " +
            "FROM users " +
            "WHERE user_name = :userName AND active = true ",
            nativeQuery = true)
    User findByUserName(@Param("userName") String userName);
}

