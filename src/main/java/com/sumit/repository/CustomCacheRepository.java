package com.sumit.repository;

import com.sumit.entity.CustomCache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCacheRepository extends MongoRepository<CustomCache, ObjectId> {


}