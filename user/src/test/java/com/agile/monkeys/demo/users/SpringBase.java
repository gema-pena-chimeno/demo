package com.agile.monkeys.demo.users;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;
import java.util.HashMap;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = SpringBase.Initializer.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public abstract class SpringBase {

    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:9.6.11")
            .withDatabaseName("customer_db_test")
            .withUsername("postgres")
            .withPassword("postgres");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            HashMap<String, Object> propertyMap = new HashMap<>();
            propertyMap.put("spring.datasource.url", postgres.getJdbcUrl());
            configurableApplicationContext.getEnvironment().getPropertySources()
                    .addFirst(new MapPropertySource("databaseProperties", propertyMap));
        }
    }
}
