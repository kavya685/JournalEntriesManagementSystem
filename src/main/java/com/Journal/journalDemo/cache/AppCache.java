package com.Journal.journalDemo.cache;

import com.Journal.journalDemo.entity.ConfigJournalAppEntity;
import com.Journal.journalDemo.repository.ConfigJournalEntryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigJournalEntryRepository configJournalEntryRepository;
    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init()
    {
        List<ConfigJournalAppEntity> all = configJournalEntryRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all)
        {
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
