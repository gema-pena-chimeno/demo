package com.agile.monkeys.demo.user.domain;

import com.agile.monkeys.demo.data.Customer;
import com.agile.monkeys.demo.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Transactional(readOnly = true)
    @Query(value =
            "select * " +
                    "from user u " +
                    "where u.user_name ilike :query " +
                    "order by u.user_name asc",
            nativeQuery = true)
    List<User> findByQuery(@Param("query") String query);
}
