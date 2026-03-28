package com.Journal.journalDemo.controller;

import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserEntryService userEntryService;

    @GetMapping("/getall")
    public ResponseEntity<?> getUsers()
    {
        List<User> users =  userEntryService.getAll();
        if(users!=null && !users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public boolean createUserAdmin(@RequestBody User user)
    {
        userEntryService.registerAdmin(user);
        return true;
    }
}
