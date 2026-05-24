package com.Journal.journalDemo.repository;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserEntryRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);

    void deleteByUsername(String name);

    @Override
    <S extends User> List<S> findAll(Example<S> example);
}
