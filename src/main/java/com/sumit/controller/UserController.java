package com.sumit.controller;

import com.sumit.entity.User;
import com.sumit.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        try{
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<User> update(@PathVariable String username, @RequestBody User user) {
        User dbUser = userService.update(username, user);
        if(dbUser != null)
            return new ResponseEntity<>(dbUser, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> delete(@PathVariable ObjectId userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}