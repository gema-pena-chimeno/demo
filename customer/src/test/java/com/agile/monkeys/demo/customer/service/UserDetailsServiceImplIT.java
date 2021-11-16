package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.SpringBase;
import com.agile.monkeys.demo.customer.utils.ResourceUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserDetailsServiceImplIT extends SpringBase {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void init() {
        configureUsers();
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void loadUserByUsername_admin_returned() {
        // when
        final UserDetails result = userDetailsService.loadUserByUsername("admin");

        // then
        // TODO: change role names?
        assertEquals( "admin", result.getUsername());
        assertEquals(List.of("ROLE_ADMIN_ROLE"), result.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void loadUserByUsername_userActive_returned() {

        // when
        final UserDetails result = userDetailsService.loadUserByUsername("user_active");

        // then
        assertEquals("user_active", result.getUsername());
        assertEquals(List.of("ROLE_USER_ROLE"), result.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void loadUserByUsername_userInactive_returned() {

        // when / then
        assertThrows(NotFoundException.class, () -> userDetailsService.loadUserByUsername("user_inactive"));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void loadUserByUsername_nonExistingUser_returned() {

        // when / then
        assertThrows(NotFoundException.class, () -> userDetailsService.loadUserByUsername("non_existing"));
    }

    private void configureUsers() {
        String query = ResourceUtils.loadAsString("sql_scripts/insert_users.sql");
        jdbcTemplate.execute(query);
    }

}