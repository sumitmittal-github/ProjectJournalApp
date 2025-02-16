package com.sumit.repository;

import com.sumit.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    void deleteByUsername(String username);

}