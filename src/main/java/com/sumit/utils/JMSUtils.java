package com.sumit.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JMSUtils {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String body){
        try{
            log.info(STR."Sending Email, to=\{to}, subject=\{subject}, to=\{body} ...");
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            javaMailSender.send(mail);
            log.info("Mail sent !!!");
        } catch(Exception e){
            log.error("Exception occurred in sendMail : ", e);
        }
    }



}