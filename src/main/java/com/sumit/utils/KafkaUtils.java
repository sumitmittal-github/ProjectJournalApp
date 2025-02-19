package com.sumit.utils;

import com.sumit.kafka.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaUtils {

    @Autowired
    KafkaTemplate<String, SentimentData> kafkaTemplate;

    public void sendMessage(String topic, String key, SentimentData data){
        kafkaTemplate.send(topic, key, data);
    }

    // we can also send data in specific partition by calling overloaded send() method of kafka template
    public void sendMessage(String topic, Integer partition, String key, SentimentData data){
        kafkaTemplate.send(topic, partition, key, data);
    }


}