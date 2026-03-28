//this service package is for business logic

package com.Journal.journalDemo.service;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

//    ✔️ @Transactional is used to mark a method as atomic (all-or-nothing).
//    ✔️ When Spring sees @Transactional, it creates a proxy and monitors the method.
//    ✔️ PlatformTransactionManager is the interface Spring uses to control transactions.
//    ✔️ MongoTransactionManager is the implementation that actually talks to MongoDB and does commit/rollback.
//    atomicity
    public JournalEntry saveEntry(JournalEntry journalEntry, String username)
    {
        User user = userEntryService.findByUsername(username);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userEntryService.saveEntry(user);
        return saved;
    }

    public JournalEntry saveEntry(JournalEntry journalEntry)
    {
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String username)
    {
        User user = userEntryService.findByUsername(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userEntryService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
