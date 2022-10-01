package com.example.jwtpractice.Controllers;

import com.example.jwtpractice.Entities.AppRole;
import com.example.jwtpractice.Entities.AppUser;
import com.example.jwtpractice.InterClass.UserXRole;
import com.example.jwtpractice.Services.UserServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    private final UserServices userServices;

    public MainController(UserServices userServices) {
        this.userServices = userServices;
    }
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(userServices.getUsers());
    }
    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userServices.saveUser(user));


    }
    @PostMapping("/role/save")
    public ResponseEntity<AppRole> saveRole(@RequestBody AppRole role){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userServices.saveRole(role));


    }
    @PostMapping("/role/addtouser")
    public ResponseEntity addRoleToUser(@RequestBody UserXRole data){
        userServices.addRoleToUser(data.getUsername(),data.getRolename());
        return ResponseEntity.ok().build();

    }

}
