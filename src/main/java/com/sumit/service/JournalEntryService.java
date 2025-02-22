package com.sumit.service;

import com.sumit.entity.JournalEntry;
import com.sumit.entity.User;
import com.sumit.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> findAllByUser(String username) {
        User user = userService.findByUsername(username);
        if(user == null)
            return null;
        return user.getJournalEntries();
    }

    //@Transactional   : COMMENTED THIS BECAUSE WE ARE USING MONGODB ON LOCAL AND IT DOES NOT SUPPORT TRANSACTIONS
    public JournalEntry createByUser(String username, JournalEntry journalEntry){
        User user = userService.findByUsername(username);
        if(user == null)
            return null;

        // saving journal entry
        journalEntry.setCreatedOn(LocalDateTime.now());
        JournalEntry dbJournalEntry = journalEntryRepository.save(journalEntry);

        // saving user
        user.getJournalEntries().add(dbJournalEntry);
        userService.save(user);

        return dbJournalEntry;
    }

    public JournalEntry updateByUser(String username, ObjectId entryId, JournalEntry uiEntry) {
        User user = userService.findByUsername(username);
        if(user == null)
            return null;

        // check if this dbEntry belongs to this passed user
        boolean isBelongsToUser = user.getJournalEntries().stream().anyMatch(je -> je.getId().equals(entryId));
        if(!isBelongsToUser)
            return null;

        JournalEntry dbEntry = findById(username, entryId);
        if(dbEntry == null)
            return null;

        dbEntry.setTitle(uiEntry.getTitle());
        dbEntry.setContent(uiEntry.getContent());
        dbEntry.setUpdatedOn(LocalDateTime.now());
        journalEntryRepository.save(dbEntry);
        return dbEntry;
    }

    public JournalEntry findById(String username, ObjectId entryId) {
        User user = userService.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(je -> je.getId().equals(entryId)).toList();
        if(journalEntries.isEmpty())
            return null;
        return journalEntries.getFirst();
    }

    //@Transactional   : COMMENTED THIS BECAUSE WE ARE USING MONGODB ON LOCAL AND IT DOES NOT SUPPORT TRANSACTIONS
    public boolean deleteByUser(String username, ObjectId entryId){
        User user = userService.findByUsername(username);
        if(user == null)
            return false;

        // check if this dbEntry belongs to this passed user
        boolean isBelongsToUser = user.getJournalEntries().stream().anyMatch(je -> je.getId().equals(entryId));
        if(!isBelongsToUser)
            return false;

        // remove JE reference from user
        user.getJournalEntries().removeIf(je -> je.getId().equals(entryId));
        userService.save(user);

        // remove Journal entry
        journalEntryRepository.deleteById(entryId);
        return true;
    }

}