package com.sumit.service;

import com.sumit.entity.JournalEntry;
import com.sumit.entity.User;
import com.sumit.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JournalEntryServiceTests {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Test
    public void findById() {
        String username = "sumitmittal";
        ObjectId entryId = new ObjectId("67a9e9308944eb7aa97f67de");

        // user shall not be null
        User user = userService.findByUsername(username);
        assertNotNull(user);

        // above user shall have the journal entry with above entryId
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(je -> je.getId().equals(entryId)).toList();
        assertTrue(!journalEntries.isEmpty());
    }



}