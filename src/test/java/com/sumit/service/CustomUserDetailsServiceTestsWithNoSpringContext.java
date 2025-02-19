package com.sumit.service;

import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import com.sumit.security.CustomUserDetailsService;
import com.sumit.constant.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTestsWithNoSpringContext {

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void loadUserByUsername(){ 
        // mock the response for dependent userRepository call
        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(new User("MockUsername", "MockPassword", "mock@mock.com", true, List.of(Roles.USER.toString())));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("sumitmittal");
        Assertions.assertNotNull(userDetails);
    }


}