package com.agile.monkeys.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.agile.monkeys.demo"})
@EntityScan(basePackages="com.agile.monkeys.demo.data")
@EnableJpaRepositories("com.agile.monkeys.demo.user.domain")
public class UserApplication implements CommandLineRunner {

    @Autowired
    AdminUserStartup adminUserStartup;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        adminUserStartup.run(args);
    }
}
