package com.Journal.journalDemo.controller;

import com.Journal.journalDemo.cache.AppCache;
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

    @Autowired
    AppCache appCache;

    @GetMapping("/getall")
    public ResponseEntity<?> getUsers()
    {
        List<User> users =  userEntryService.getAll();
        if(users!=null && !users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // this is done so that we do not have to restart the server every time we change the data or config in db,
    // we can just call this api to refresh the cache
    @GetMapping("calling-init")
    public void clearAppCache()
    {
        appCache.init();
    }

    @PostMapping
    public boolean createUserAdmin(@RequestBody User user)
    {
        userEntryService.registerAdmin(user);
        return true;
    }
}
