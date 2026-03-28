package com.Journal.journalDemo.controller;

import com.Journal.journalDemo.entity.JournalEntry;
import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.service.JournalEntryService;
import com.Journal.journalDemo.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userEntryService.findByUsername(authentication.getName());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> arr = user.getJournalEntries();
        if(arr!=null && !arr.isEmpty()) return new ResponseEntity<>(arr,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity createEntry(@RequestBody JournalEntry myEntry)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userEntryService.findByUsername(username);
            if(user==null) return new ResponseEntity(HttpStatus.NOT_FOUND);
            JournalEntry saved = journalEntryService.saveEntry(myEntry,username);
            user.getJournalEntries().add(saved);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myID}")
    public ResponseEntity getSpecific(@PathVariable ObjectId myID)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userEntryService.findByUsername(userName);
        List<JournalEntry> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).collect(Collectors.toList());
        if(!entry.isEmpty()) return new ResponseEntity<>(entry,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
It feels strange at first, but it actually makes perfect sense once you see what `@DBRef` really is.

Think of `@DBRef` like this:

User is not storing the journal content.
User is storing just the **ID reference** to another document.

So inside Mongo it’s basically:

```json
User {
   journalEntries: [ObjectId("abc123")]
}
```

That’s it. Just the ID.

---

## 🟢 Why UPDATE doesn’t need user change

When you update a JournalEntry:

```java
journalEntryRepository.save(updatedEntry);
```

You are modifying the document that ObjectId("abc123") points to.

The ID stays the same.

User still points to the same ID.

So nothing in User needs to change.

It’s like:

* You have a file shortcut on your desktop.
* You edit the actual file.
* The shortcut still works because the file path didn’t change.

No need to update the shortcut.

---

## 🔴 Why DELETE needs user change

When you delete:

```java
journalEntryRepository.deleteById(id);
```

You remove the actual file.

But the shortcut (reference in User) still exists.

Now it points to nothing.

That’s called a dangling reference.

MongoDB won’t clean that automatically.

So you must manually remove that ID from user’s list.

---

## 🧠 The Core Difference

| Operation | Does ID change? | Need to update User? |
| --------- | --------------- | -------------------- |
| Update    | ❌ No            | ❌ No                 |
| Delete    | ✅ Removed       | ✅ Yes                |

Because User stores ID, not content.

`@DBRef` is just a pointer. No magic sync.

*/

    @DeleteMapping("/id/{myID}")
    public boolean deleteEntry(@PathVariable ObjectId myID)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userEntryService.findByUsername(userName);
        journalEntryService.deleteById(myID,userName);
        return true;
    }

    @PutMapping("/id/{myID}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myID, @RequestBody JournalEntry newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userEntryService.findByUsername(userName);
        List<JournalEntry> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).collect(Collectors.toList());

        if(!entry.isEmpty())
        {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myID);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle() );
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
        }}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
