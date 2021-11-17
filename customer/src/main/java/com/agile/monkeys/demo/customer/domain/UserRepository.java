package com.agile.monkeys.demo.customer.domain;

import com.agile.monkeys.demo.data.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByUserNameAndActive(String userName, boolean active);
}

