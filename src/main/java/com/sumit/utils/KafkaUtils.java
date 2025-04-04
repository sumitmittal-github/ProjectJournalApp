package com.sumit.utils;

import com.sumit.kafka.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaUtils {

    @Autowired
    KafkaTemplate<String, SentimentData> kafkaTemplate;

    public void send(String topic, String key, SentimentData data){
        kafkaTemplate.send(topic, key, data);
    }

    // we can also send data in specific partition by calling overloaded send() method of kafka template
    public void send(String topic, Integer partition, String key, SentimentData data){
        kafkaTemplate.send(topic, partition, key, data);
    }


}