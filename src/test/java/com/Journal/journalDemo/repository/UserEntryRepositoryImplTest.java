package com.Journal.journalDemo.repository;

import com.Journal.journalDemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserEntryRepositoryImplTest {
    @Autowired
    MongoTemplate mongoTemplate;
    public List<User> getUserOfSA()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        mongoTemplate.find(query, User.class);
        return null;
    }
}
