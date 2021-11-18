package com.agile.monkeys.demo.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages={"com.agile.monkeys.demo"})
@EntityScan(basePackages="com.agile.monkeys.demo.data")
@EnableJpaRepositories("com.agile.monkeys.demo.customer.domain")
public class CustomerApplication {
    public static void main(String[] args) {

        SpringApplication.run(CustomerApplication.class, args);
    }
}
