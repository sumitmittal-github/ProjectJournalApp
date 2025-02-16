package com.sumit.service;

import com.sumit.entity.CustomCache;
import com.sumit.repository.CustomCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCacheService {

    @Autowired
    CustomCacheRepository customCacheRepository;

    public List<CustomCache> loadAllCustomCache(){
        return customCacheRepository.findAll();
    }

}