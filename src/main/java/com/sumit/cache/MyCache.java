package com.sumit.cache;

import com.sumit.entity.CustomCache;
import com.sumit.service.CustomCacheService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
@DependsOn("loadDummyDataInDB")  // To ensure that LoadDummyDataInDB will be initialized before this MyCache bean
public class MyCache {

    public Map<String, String> cacheMap;

    @Autowired
    CustomCacheService customCacheService;

    @PostConstruct
    public void init(){
        log.info("Initializing cache from DB ...");
        cacheMap = new HashMap<>();
        List<CustomCache> list = customCacheService.loadAllCustomCache();
        if(list != null)
            list.forEach(c -> cacheMap.put(c.getKey(), c.getValue()));
        log.info(cacheMap);
        log.info("Cache initialized !!!");
    }

}