package com.sumit.service;

import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoles().add("USER");
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User registerAdmin(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoles().add("ADMIN");
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User loginUser(User user) {
        User dbUser = userRepository.findByUsernameAndPassword(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));
        if(dbUser == null)
            return null;

        return dbUser;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }



    public void save(User user) {
        userRepository.save(user);
    }

    public User update(String username, User uiUser) {
        User dbUser = findByUsername(username);         //dbUser can not be null, because this was secure end point and only authenticated user can access this end point
        dbUser.setUsername(uiUser.getUsername());
        dbUser.setPassword(bCryptPasswordEncoder.encode(uiUser.getPassword()));
        dbUser.setUpdatedOn(LocalDateTime.now());
        userRepository.save(dbUser);
        return dbUser;
    }

    public boolean delete(String username){
        userRepository.deleteByUsername(username);
        return true;
    }


}