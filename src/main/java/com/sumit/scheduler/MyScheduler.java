package com.sumit.scheduler;

import com.sumit.entity.User;
import com.sumit.constant.Sentiment;
import com.sumit.kafka.SentimentData;
import com.sumit.utils.JMSUtils;
import com.sumit.service.UserService;
import com.sumit.constant.AppConstants;
import com.sumit.utils.KafkaUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Log4j2
public class MyScheduler {

    // NOTE : generate cron expression from http://www.cronmaker.com

    @Autowired
    UserService userService;

    @Autowired
    JMSUtils jmsUtils;

    @Autowired
    KafkaUtils kafkaUtils;

    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void sendSentimentsEmailEverySundayMorning(){
        try{
            log.info("******************************************************************************");
            log.info("CRON JOB STARTED ...");
            log.info("******************************************************************************");

            log.info("Sending weekly sentiments email ...");

            List<User> allUsersForSentimentsEmail = userService.findAllUsersForSentimentsEmail();
            log.info(STR."Found \{allUsersForSentimentsEmail.size()} users for sending sentiments mails");

            allUsersForSentimentsEmail.stream()
                    .forEach( user -> {
                                // calculate the kafka key
                                String key = AppConstants.KAFKA_SENTIMENTS_TOPIC_KEY;

                                // calculate the kafka value
                                int last7DaysJournalEntriesCount = (int)user.getJournalEntries().stream()
                                                                    .filter(x -> x.getCreatedOn().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                                                                    .count();
                                Sentiment sentiment = switch(last7DaysJournalEntriesCount) {
                                    case 0 -> Sentiment.ANXIOUS;
                                    case 1, 2 -> Sentiment.SAD;
                                    default -> Sentiment.HAPPY;
                                };
                                SentimentData sentimentData = new SentimentData(user.getUsername(), user.getEmail(), sentiment);

                                try{
                                    // send sentiments data in a kafka message
                                    kafkaUtils.send(AppConstants.KAFKA_TOPIC_NAME, key, sentimentData);
                                } catch(Exception e){
                                    log.info("kafka was down, so handle this scenario we are directly sending the email");
                                    String email = sentimentData.getEmail();
                                    String subject = "Weekend Sentiments";
                                    String body = STR."Hello \{sentimentData.getUsername()}, Your previous weeks sentiment was : \{sentimentData.getSentiment().name()}";
                                    jmsUtils.sendMail(email, subject, body);
                                }
                            }
                    );
        } catch(Exception e){
            log.error("Exception occurred in sendSentimentsEmailEverySundayMorning ", e);
        }
        log.info("Sentiments email sent !!!");

        log.info("******************************************************************************");
        log.info("CRON JOB ENDED !!!");
        log.info("******************************************************************************");
    }



}