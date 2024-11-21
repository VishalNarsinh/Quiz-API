package com.quizapi;

import com.quizapi.dto.UserDto;
import com.quizapi.service.RoleService;
import com.quizapi.service.UserService;
import com.quizapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;

@SpringBootApplication
public class QuizApiApplication implements CommandLineRunner {


    private final RoleService roleService;
    private final UserService userService;

    @Value("${admin.password}")
    private String adminPassword;

    public QuizApiApplication(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(QuizApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.saveRole();
        UserDto admin = UserDto.builder().userId(1).name("Admin").email("admin@quizhub.com").password("admin").roles(new HashSet<>()).build();

        userService.saveUser(admin,null,AppConstants.ADMIN_USER);
    }
}
