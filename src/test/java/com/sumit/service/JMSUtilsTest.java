package com.sumit.service;

import com.sumit.utils.JMSUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JMSUtilsTest {

    @Autowired
    JMSUtils jmsUtils;

    @Test
    public void sendMailTest(){
        jmsUtils.sendMail("sumit.mittal996@gmail.com", "My Subject", "My Body");
    }

}