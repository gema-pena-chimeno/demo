package com.agile.monkeys.demo.customer.domain;

import com.agile.monkeys.demo.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserNameAndActive(String userName, boolean active);
}

