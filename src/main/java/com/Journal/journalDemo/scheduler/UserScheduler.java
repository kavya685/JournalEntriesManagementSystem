package com.Journal.journalDemo.scheduler;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.repository.JournalEntryRepository;
import com.Journal.journalDemo.repository.UserEntryRepositoryImpl;
import com.Journal.journalDemo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class UserScheduler {
    @Autowired
    private UserEntryRepositoryImpl userEntryRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled( cron = "* * * * *" )
    public void sendEmailToSAUsers()
    {
        List<User> users = userEntryRepository.getUserOfSA();
        for(User user : users)
        {
            List<JournalEntry> journalEntry = user.getJournalEntries();
            if(journalEntry.size() > 0)
            {
                emailService.sendMail(user.getEmail(), "SA testing", journalEntry.toString());
            }
        }
    }
}
