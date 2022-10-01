package com.example.jwtpractice.Repositories;


import com.example.jwtpractice.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUsername(String username);
    
}
