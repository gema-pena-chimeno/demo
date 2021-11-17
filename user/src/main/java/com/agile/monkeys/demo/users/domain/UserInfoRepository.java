package com.agile.monkeys.demo.users.domain;

import com.agile.monkeys.demo.data.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Transactional(readOnly = true)
    @Query(value =
            "select * " +
            "from user_info " +
            "where user_name ilike :query " +
            "order by user_name asc",
            nativeQuery = true)
    List<UserInfo> findByQuery(@Param("query") String query);

    @Transactional(readOnly = true)
    @Query(value =
            "select count(*) = 0 " +
            "from user_info " +
            "where active = true and role = :adminRole and id <> :id",
            nativeQuery = true)
    boolean isLastAdmin(@Param("id") String id, @Param("adminRole") String adminRole);

    Optional<UserInfo> findByUserName(String userName);

    Optional<UserInfo> findByUserNameAndActive(String userName, boolean active);
}
