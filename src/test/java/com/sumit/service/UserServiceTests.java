package com.sumit.service;

import com.sumit.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;


    @BeforeAll
    public static void beforeAll(){
        System.out.println("Will execute before 1st test case");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("Will execute after last test case");
    }



    @BeforeEach
    public void beforeEach(){
        System.out.println("Will execute before every test case");
    }


    @AfterEach
    public void afterEach(){
        System.out.println("Will execute after every test case");
    }


    // assertTrue example
    @Test
    public void assertTrueExample() {
        assertTrue(5>3);
    }

    // assertNotNull example
    @Test
    public void findByUsername() {
        String username = "sumitmittal";

        // user shall not be null
        assertNotNull(userRepository.findByUsername(username));
    }

    // assertNull example
    @Disabled
    @Test
    public void findByUsernameNotInDB() {
        String username = "unknownuser";

        // user shall be null because above user does not exist in DB
        assertNull(userRepository.findByUsername(username));
    }


    @ParameterizedTest
    @CsvSource({"1,1,2", "9,2,11"})
    public void sum(int a, int b, int expected){
        assertEquals(expected, a+b);
    }





}