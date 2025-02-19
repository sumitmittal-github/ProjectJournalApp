package com.sumit.constant;

public interface AppConstants {

    // for Redis cache
    Long CACHE_TIME_TO_LIVE_MINUTES = 240L;


    // for Apache Kafka
    String KAFKA_CONSUMER_GROUP_ID = "my-consumer-group";
    String KAFKA_TOPIC_NAME= "my-topic";
    String KAFKA_SENTIMENTS_TOPIC_KEY = "weekly_sentiments";


    String CACHE_GET_WEATHER_API_KEY = "GET_WEATHER_API_URL";


}