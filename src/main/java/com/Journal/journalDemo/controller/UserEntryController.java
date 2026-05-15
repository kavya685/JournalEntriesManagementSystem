package com.Journal.journalDemo.controller;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.repository.UserEntryRepository;
import com.Journal.journalDemo.service.JournalEntryService;
import com.Journal.journalDemo.service.UserEntryService;
import com.Journal.journalDemo.service.WeatherService;
import org.bson.types.ObjectId;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private WeatherService weatherService;

//    @GetMapping
//    public List<User> getAllUsers()
//    {
//        return userEntryService.getAll();
//    }

    @PostMapping
    public boolean createUser(@RequestBody User user)
    {
        userEntryService.registerUser(user);
        return true;
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user)
    {
        @Nullable
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userEntryService.findByUsername(username);
        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(user.getPassword());
        userEntryService.saveEntry(userInDB);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName() != null)
        {
            return new ResponseEntity<>("today's weather: " + weatherService.getWeather("San Francisco"), HttpStatus.OK);
        }
        else
        return new ResponseEntity<>("hello " + authentication.getName(), HttpStatus.OK);
    }
}
