package com.social.controller;

import com.social.models.SocialUser;
import com.social.services.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//please see this is needed for dependency injection also. autowired wont work without it
@RequestMapping("/api")

public class Controller {
    @Autowired
    private SocialService socialService;

    @GetMapping("public/social/users")
    public ResponseEntity<List<SocialUser>>getUsers(){
        return new ResponseEntity<>(socialService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/admin/social/users")
    public ResponseEntity<SocialUser>addUser(@RequestBody SocialUser socialUser){
        return new ResponseEntity<>(socialService.saveUser(socialUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/social/users/{userId}")
    public ResponseEntity<SocialUser>deleteUser(@PathVariable Long userId){
        return new ResponseEntity<>(socialService.deleteUser(userId), HttpStatus.CREATED);
    }

}
