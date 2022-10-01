package com.example.jwtpractice.Services;

import com.example.jwtpractice.Entities.AppRole;
import com.example.jwtpractice.Entities.AppUser;
import com.example.jwtpractice.Repositories.RoleRepo;
import com.example.jwtpractice.Repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @Transactional @Slf4j
public class UserServicesImpl implements UserServices, UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user=userRepo.findAppUserByUsername(username);
        if(user==null){
            log.info("user not found");
            throw new UsernameNotFoundException("username not found");
        }else{
            log.info("userfound");

        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        });

        return new User(user.getUsername(),user.getPassword(),authorities);
    }
}
