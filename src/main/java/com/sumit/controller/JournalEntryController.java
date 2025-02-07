package com.sumit.controller;

import com.sumit.entity.JournalEntry;
import com.sumit.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> findAll() {
        return journalEntryService.findAll();
    }

    @GetMapping("/id/{entryId}")
    public JournalEntry findById(@PathVariable ObjectId entryId) {
        return journalEntryService.findById(entryId);
    }

    @PostMapping
    public JournalEntry create(@RequestBody JournalEntry entry) {
        return journalEntryService.create(entry);
    }

    @PutMapping("/id/{entryId}")
    public JournalEntry update(@PathVariable ObjectId entryId, @RequestBody JournalEntry entry) {
        return journalEntryService.update(entryId, entry);
    }

    @DeleteMapping("/id/{entryId}")
    public String delete(@PathVariable ObjectId entryId) {
        journalEntryService.delete(entryId);
        return "Delete Successfully !!";
    }


}