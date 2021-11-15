package com.agile.monkeys.demo.user;

import com.agile.monkeys.demo.data.User;
import com.agile.monkeys.demo.data.UserRole;
import com.agile.monkeys.demo.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminUserStartup implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByUserName("admin").isPresent()) {
            System.out.println("User admin already configured.");
            return;
        }

        if (args.length != 1) {
            System.out.println("The command has a parameter, password for the admin user...");
            throw new IllegalArgumentException("The first time the component must be executed with a password");
        }

        User admin = new User();
        admin.setUserName("admin");
        admin.setPassword(args[0]);
        admin.setActive(true);
        admin.setRole(UserRole.ADMIN_ROLE.toString());
        userRepository.save(admin);

        System.out.println("User admin created successfully");
    }
}