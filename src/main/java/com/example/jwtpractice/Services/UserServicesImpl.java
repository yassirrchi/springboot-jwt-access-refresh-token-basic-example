package com.example.jwtpractice.Services;

import com.example.jwtpractice.Entities.AppRole;
import com.example.jwtpractice.Entities.AppUser;
import com.example.jwtpractice.Repositories.RoleRepo;
import com.example.jwtpractice.Repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service @Transactional @Slf4j
public class UserServicesImpl implements UserServices {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServicesImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("saving new user");
        return userRepo.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        log.info("saving new role");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        log.info("adding role to user");
        AppUser user=userRepo.findAppUserByUsername(username);
    AppRole role=roleRepo.findAppRoleByRolename(rolename);
    user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("fetching user");
        return userRepo.findAppUserByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }
}
