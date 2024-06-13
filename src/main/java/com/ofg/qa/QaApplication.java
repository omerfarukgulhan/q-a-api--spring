package com.ofg.qa;

import com.ofg.qa.dao.UserRepository;
import com.ofg.qa.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class QaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return (args) -> {
            for (var i = 1; i <= 3; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setPassword("P4ssword");
                userRepository.save(user);
            }
        };
    }
}
