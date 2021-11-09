package com.agile.monkeys.demo.user.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    // TODO
    List<User> findByLastName(String lastName);

    User findById(long id);
}
