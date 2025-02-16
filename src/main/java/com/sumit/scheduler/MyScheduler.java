package com.sumit.scheduler;

import com.sumit.entity.User;
import com.sumit.service.EmailService;
import com.sumit.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Log4j2
public class MyScheduler {

    // NOTE : generate cron expression from http://www.cronmaker.com

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void sendPromotionEmailEverySundayMorning(){
        try{
            log.info("Sending promotion email ...");
            List<User> allUsersForPromotionEmail = userService.findAllUsersForPromotionEmail();
            allUsersForPromotionEmail
                    .stream()
                    .forEach( user -> emailService.sendMail(user.getEmail(),
                                                     "Weekend Promotion",
                                                            STR."Hello \{user.getUsername()}, Special promotions are enables on website.")
                    );
        } catch(Exception e){
            log.error("Exception occurred in sendPromotionEmailEverySundayMorning ", e);
        }
        log.info("Promotion email sent !!!");
    }



}