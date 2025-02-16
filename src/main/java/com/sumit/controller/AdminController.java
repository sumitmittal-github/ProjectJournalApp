package com.sumit.controller;

import com.sumit.entity.User;
import com.sumit.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        log.info("Entry AdminController.findAllUsers ...");
        List<User> list = userService.findAll();
        log.info("Exit AdminController.findAllUsers !!!");
        if(list != null && !list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usersSA")
    public ResponseEntity<List<User>> findSAUsers() {
        log.info("Entry AdminController.findSAUsers ...");
        List<User> list = userService.findSAUsers();
        log.info("Exit AdminController.findSAUsers !!!");
        if(list != null && !list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        try{
            User dbUser = userService.createAdmin(user);
            return new ResponseEntity<>(dbUser, HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}