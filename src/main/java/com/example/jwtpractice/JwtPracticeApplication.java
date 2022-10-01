package com.example.jwtpractice;

import com.example.jwtpractice.Entities.AppRole;
import com.example.jwtpractice.Entities.AppUser;
import com.example.jwtpractice.Services.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class JwtPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtPracticeApplication.class, args);
    }
    @Bean
    CommandLineRunner run(UserServices userServices){
 return args -> {



     userServices.saveRole(new AppRole(null,"ROLE_USER"));
     userServices.saveRole(new AppRole(null,"ROLE_MANAGER"));
     userServices.saveRole(new AppRole(null,"ROLE_ADMIN"));
     userServices.saveRole(new AppRole(null,"ROLE_SUPER_ADMIN"));
     userServices.saveUser(new AppUser(null,"yass rch","mohamed","123",new ArrayList<>()));
     userServices.saveUser(new AppUser(null,"yass rch","yassir","123",new ArrayList<>()));userServices.addRoleToUser("yassir","ROLE_MANAGER");
     userServices.addRoleToUser("mohamed","ROLE_MANAGER");
     userServices.addRoleToUser("mohamed","ROLE_USER");



 };
    }

}
