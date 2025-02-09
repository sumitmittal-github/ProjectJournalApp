package com.sumit.controller;

import com.sumit.entity.JournalEntry;
import com.sumit.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> findAllByUser(@PathVariable String username) {
        List<JournalEntry> list = journalEntryService.findAllByUser(username);
        if(list != null && !list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{entryId}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId entryId) {
        JournalEntry entry = journalEntryService.findById(entryId);
        if(entry != null)
            return new ResponseEntity<>(entry, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createByUser(@PathVariable String username, @RequestBody JournalEntry entry) {
        JournalEntry dbEntry = journalEntryService.createByUser(username, entry);
        if(dbEntry != null)
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/id/{entryId}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId entryId, @RequestBody JournalEntry entry) {
        JournalEntry dbEntry = journalEntryService.update(entryId, entry);
        if(dbEntry != null)
            return new ResponseEntity<>(dbEntry, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{entryId}")
    public ResponseEntity<?> delete(@PathVariable String username, @PathVariable ObjectId entryId) {
        boolean isDeleted = journalEntryService.deleteByUser(username, entryId);
        if(isDeleted)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}