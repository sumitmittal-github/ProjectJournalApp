package com.sumit.controller;

import com.sumit.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalController {

    private final Map<Long, JournalEntry> entries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(entries.values());
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getById(@PathVariable Long myId){
        return entries.get(myId);
    }

    @PostMapping
    public String create(@RequestBody JournalEntry entry){
        entries.put(entry.id(), entry);
        return "Added Successfully !!";
    }

    @PutMapping("/id/{myId}")
    public String update(@PathVariable Long myId, @RequestBody JournalEntry entry){
        entries.put(myId, entry);
        return "Updated Successfully !!";
    }

    @DeleteMapping("/id/{myId}")
    public String delete(@PathVariable Long myId){
        entries.remove(myId);
        return "Delete Successfully !!";
    }


}