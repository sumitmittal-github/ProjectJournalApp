package com.sumit.service;

import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import com.sumit.security.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.*;


@SpringBootTest
public class CustomUserDetailsServiceTestsWithSpringContext {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserRepository userRepository;



    @Test
    public void loadUserByUsername(){
        // mock the response for dependent userRepository call
        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(new User("mockUsername", "mockPassword", List.of("USER")));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("mockUsername");
        Assertions.assertNotNull(userDetails);
    }


}