package com.sumit.repository;

import com.sumit.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class UserRepoWithCriteria {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getUsersForSentimentAnalysis(){
        log.info("Entry getUsersForSentimentAnalysis ...");

        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysisOpted").is(true));
        query.addCriteria(Criteria.where("roles").in("USER", "ADMIN"));
        List<User> users = mongoTemplate.find(query, User.class);
        log.info(STR."Users : \{users}");

        log.info("Exit getUsersForSentimentAnalysis !!!");
        return users;
    }

    public List<User> findAllUsersForPromotionEmail(){
        log.info("Entry findAllUsersForPromotionEmail ...");

        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        query.addCriteria(Criteria.where("roles").in("USER"));
        List<User> users = mongoTemplate.find(query, User.class);
        log.info(STR."Users : \{users}");

        log.info("Exit findAllUsersForPromotionEmail !!!");
        return users;
    }


}