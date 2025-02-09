package com.sumit.service;

import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public User save(User user) {
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User update(String username, User uiUser) {
        User dbUser = findByUsername(username);
        if(dbUser == null)
            return null;
        dbUser.setUsername(uiUser.getUsername());
        dbUser.setPassword(uiUser.getPassword());
        dbUser.setUpdatedOn(LocalDateTime.now());
        userRepository.save(dbUser);
        return dbUser;
    }

    public boolean delete(ObjectId userId){
        userRepository.deleteById(userId);
        return true;
    }

}