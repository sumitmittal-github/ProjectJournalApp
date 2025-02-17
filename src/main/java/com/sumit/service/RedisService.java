package com.sumit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class RedisService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    ObjectMapper mapper = new ObjectMapper();

    public <T> T getFromCache(String key, Class<T> entityClass){
        log.info(STR."Fetching key-value from Redis cache for key : \{key} ...");
        try {
            Object redisResponse = redisTemplate.opsForValue().get(key);
            log.info(STR."Fetched key : \{key} value : \{redisResponse}");
            if(redisResponse != null) {
                // convert java Object into our customObject
                return mapper.readValue(redisResponse.toString(), entityClass);
            }

        } catch (JsonProcessingException e) {
            log.error(STR."Exception in getFromCache for key = \{key} ", e);
        }
        return null;
    }

    public void setInCache(String key, Object value, Long timeToLive){
        log.info(STR."Adding key-value in redis cache for key = \{key} ...");
        try {
            String jsonValue = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, timeToLive, TimeUnit.MINUTES);
            log.info(STR."Added key-value in redis cache for key = \{key} is done !!!");
        } catch (Exception e) {
            log.error(STR."Exception in setInCache for key = \{key} ", e);
        }
    }



}