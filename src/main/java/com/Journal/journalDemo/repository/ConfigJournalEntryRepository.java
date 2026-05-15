package com.Journal.journalDemo.repository;

import com.Journal.journalDemo.cache.AppCache;
import com.Journal.journalDemo.entity.ConfigJournalAppEntity;
import com.Journal.journalDemo.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalEntryRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
