package com.agile.monkeys.demo.user.domain;

import com.agile.monkeys.demo.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
