package com.sumit.service;

import com.sumit.entity.JournalEntry;
import com.sumit.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> findAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry findById(ObjectId entryId) {
        return journalEntryRepository.findById(entryId).orElse(null);
    }

    public JournalEntry create(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
        return journalEntry;
    }

    public JournalEntry update(ObjectId entryId, JournalEntry uiEntry) {
        JournalEntry dbEntry = findById(entryId);
        if(dbEntry == null)
            return uiEntry;

        dbEntry.setTitle(uiEntry.getTitle());
        dbEntry.setContent(uiEntry.getContent());
        dbEntry.setUpdatedOn(LocalDateTime.now());
        journalEntryRepository.save(dbEntry);
        return dbEntry;
    }

    public boolean delete(ObjectId entryId){
        journalEntryRepository.deleteById(entryId);
        return true;
    }

}