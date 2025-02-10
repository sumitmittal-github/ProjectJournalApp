package com.sumit.controller;

import com.sumit.entity.User;
import com.sumit.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User dbUser = userService.update(username, user);
        return new ResponseEntity<>(dbUser, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.delete(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
    ONLY FOR ADMIN
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = userService.findAll();
        if(list != null && !list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> findById(@PathVariable ObjectId userId) {
        User user = userService.findById(userId);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<User> findByUsername(@PathVariable String userName) {
        User user = userService.findByUsername(userName);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    */


}