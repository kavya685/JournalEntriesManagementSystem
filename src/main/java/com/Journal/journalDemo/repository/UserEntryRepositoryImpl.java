package com.Journal.journalDemo.repository;

import com.Journal.journalDemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public class UserEntryRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;

    // this is basically creating dynamic query
    // in-built queries are not sufficient for our use case,
    // so we need to create custom query using MongoTemplate
    public List<User> getUserOfSA()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
