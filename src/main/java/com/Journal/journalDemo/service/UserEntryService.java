//this service package is for business logic

package com.Journal.journalDemo.service;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.repository.JournalEntryRepository;
import com.Journal.journalDemo.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
// the below annotation is for logging, it will create a logger
// instance for this class and we can use it to log messages
// at different levels (info, debug, error, etc.)
// without having to manually create a logger instance.
// It simplifies the logging process and helps in tracking
// the flow of the application and debugging issues.
@Slf4j

public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User userEntry) {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(List.of("USER"));
        userEntryRepository.save(userEntry);
        //trying logger
        log.info("user registered with username: {}" , userEntry.getUsername());
    }

    public void registerAdmin(User userEntry)
    {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(List.of("USER","ADMIN"));
        userEntryRepository.save(userEntry);
    }

    public void saveEntry(User userEntry) {
        userEntryRepository.save(userEntry);
    }

    public List<User> getAll()
    {
        return userEntryRepository.findAll();
    }

    public Optional<User> findById(ObjectId id)
    {
        return userEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        userEntryRepository.deleteById(id);
    }

    public User findByUsername(String username)
    {
        return userEntryRepository.findByUsername(username);
    }
}
