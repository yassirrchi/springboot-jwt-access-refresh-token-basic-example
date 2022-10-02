package com.example.jwtpractice.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtpractice.Entities.AppRole;
import com.example.jwtpractice.Entities.AppUser;
import com.example.jwtpractice.InterClass.UserXRole;
import com.example.jwtpractice.Services.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader= request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer ")){
            try{

                String Refreshtoken =authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256("SECRET".getBytes());
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=verifier.verify(Refreshtoken);
                String username=decodedJWT.getSubject();
                AppUser user=userServices.getUser(username);
                String accessToken= JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+60*10000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",user.getRoles().stream().map(AppRole::getRolename).collect(Collectors.toList()))
                        .sign(algorithm);



                Map<String,String> tokens=new HashMap<>();
                tokens.put("accessToken",accessToken);
                tokens.put("refreshToken",Refreshtoken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


//
            }catch(Exception e){
                 response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError();
                Map<String,String> error=new HashMap<>();
                error.put("error_message",e.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);


            };

        }
        else{
            throw new RuntimeException("refreshtoken is missing");
         }

    }

}
