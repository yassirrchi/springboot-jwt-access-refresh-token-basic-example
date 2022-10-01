package com.example.jwtpractice.Services;

import com.example.jwtpractice.Entities.AppRole;
import com.example.jwtpractice.Entities.AppUser;

import java.util.List;

public interface UserServices {
    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole role);
    void addRoleToUser(String username,String rolename);
    AppUser getUser(String username);
    List<AppUser> getUsers();

}
