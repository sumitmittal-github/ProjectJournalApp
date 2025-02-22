package com.sumit.service;

import com.sumit.entity.User;
import com.sumit.repository.UserRepoWithCriteria;
import com.sumit.repository.UserRepository;
import com.sumit.constant.Roles;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepoWithCriteria userRepoWithCriteria;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User registerUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            log.warn(STR."User \{user.getUsername()} tried to register again, hence returned back.");
            return null;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Roles.USER.toString()));
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUser(String username, User uiUser) {
        //dbUser can not be null, because this was secure end point and only authenticated user can access this end point
        User dbUser = userRepository.findByUsername(username);

        // Can't update username and password. For username or password change it should have a different API because if we will change them now then the JWT token will be invalid
        //dbUser.setUsername(uiUser.getUsername());
        //dbUser.setPassword(bCryptPasswordEncoder.encode(uiUser.getPassword()));   // because from front end we will always get the plain password
        dbUser.setEmail(uiUser.getEmail());
        dbUser.setUpdatedOn(LocalDateTime.now());
        userRepository.save(dbUser);
        return dbUser;
    }

    public boolean deleteUser(String username){
        userRepository.deleteByUsername(username);
        return true;
    }

    public User registerDummy(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User createAdmin(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Roles.ADMIN.toString(), Roles.USER.toString()));
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findSAUsers() {
        return userRepoWithCriteria.getUsersForSentimentAnalysis();
    }

    public List<User> findAllUsersForSentimentsEmail() {
        return userRepoWithCriteria.findAllUsersForSentimentsEmail();
    }

    /*  When we use Basic Auth in SpringBoot, not the JWT.
    public User loginUser(User user) {
        User dbUser = userRepository.findByUsernameAndPassword(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));
        if(dbUser == null)
            return null;
        return dbUser;
    }
    */









}