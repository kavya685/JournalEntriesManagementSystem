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

    public Map<String, String> APP_CACHE;
    @PostConstruct
    public void init()
    {
        // instead of creating map outside loop, we can create it inside the loop and assign it to the APP_CACHE variable at the end,
        // this way we can avoid any concurrency issues that may arise
        // if multiple threads are trying to access the APP_CACHE variable at the same time,
        // by creating a new map and assigning it to the APP_CACHE variable at the end,
        // we ensure that all threads will see a consistent view of the cache,
        // and we can avoid any potential issues with concurrent modifications to the map
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalEntryRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all)
        {
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
