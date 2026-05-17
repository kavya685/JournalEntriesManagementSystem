package com.Journal.journalDemo.repository;

import com.Journal.journalDemo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootTest
public class UserEntryRepositoryImplTest {
    @Autowired
    private UserEntryRepositoryImpl userEntryRepository;

    @Test
    public void testSA()
    {
        userEntryRepository.getUserOfSA();
    }
}
