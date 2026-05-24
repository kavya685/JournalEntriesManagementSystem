package com.Journal.journalDemo.scheduler;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.repository.JournalEntryRepository;
import com.Journal.journalDemo.repository.UserEntryRepository;
import com.Journal.journalDemo.repository.UserEntryRepositoryImpl;
import com.Journal.journalDemo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserScheduler {
    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private EmailService emailService;

    // @Scheduled( cron = "* * * * *" )
    public void sendEmailToSAUsers()
    {
        List<User> users = userEntryRepository.findAll();
        for(User user : users)
        {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            for(JournalEntry journalEntry : journalEntries)
            {
                if(journalEntry.getSentiment() != null)
                emailService.sendMail("kavyapopat68@gmail.com", "SA testing", String.valueOf(journalEntry.getSentiment()));
            }
        }
    }
}
