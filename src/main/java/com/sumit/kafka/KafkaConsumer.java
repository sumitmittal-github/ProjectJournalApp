package com.sumit.kafka;

import com.sumit.service.EmailService;
import com.sumit.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumer {

    @Autowired
    EmailService emailService;

    /* If we want to read from a particular partition
    @KafkaListener(topicPartitions = @TopicPartition(topic = Constants.KAFKA_TOPIC_NAME, partitions = {"0"}),
                   groupId = Constants.KAFKA_CONSUMER_GROUP_ID)*/
    @KafkaListener(topics = Constants.KAFKA_TOPIC_NAME, groupId = Constants.KAFKA_CONSUMER_GROUP_ID)
    public void consumeMessage(ConsumerRecord<String, SentimentData> record) {
        log.info(STR."Received message in kafka = \{record}");
        log.info(STR."We have received the sentiments data from partition who is having data of key = \{record.key()} ");

        // send email to the user
        String email = record.value().getEmail();
        String subject = "Weekend Sentiments";
        String body = STR."Hello \{record.value().getUsername()}, Your previous weeks sentiment was : \{record.value().getSentiment().name()}";
        emailService.sendMail(email, subject, body);
    }


}