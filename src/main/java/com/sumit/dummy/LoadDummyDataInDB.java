package com.sumit.dummy;

import com.sumit.constant.Roles;
import com.sumit.entity.JournalEntry;
import com.sumit.entity.User;
import com.sumit.service.CustomCacheService;
import com.sumit.service.JournalEntryService;
import com.sumit.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class LoadDummyDataInDB {

    @Autowired
    UserService userService;
    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    CustomCacheService customCacheService;

    /*@PostConstruct
    public void insertDummyDataInDB(){
        log.info("Creating Dummy Data ...");

        userService.register(new User("sumit.mittal","pwd123", "sumit@gmail.com", true, List.of(Roles.USER.toString())));
        userService.register(new User("lokesh.mittal","pwd456", "lokesh@gmail.com", false, List.of(Roles.ADMIN.toString(), Roles.USER.toString())));
        userService.register(new User("suman.mittal","pwd789", "suman@gmail.com", true, List.of(Roles.ADMIN.toString())));
        userService.register(new User("dummy.user","dummy123", "dummy@dummy", true, List.of(Roles.USER.toString())));

        journalEntryService.createByUser("sumit.mittal", new JournalEntry("Morning", "This is a wonderful morning"));
        journalEntryService.createByUser("sumit.mittal", new JournalEntry("Study", "Complete the DSA and System Design"));
        journalEntryService.createByUser("sumit.mittal", new JournalEntry("Kids", "Janvi & Aarvi"));
        journalEntryService.createByUser("lokesh.mittal", new JournalEntry("Target", "Canada Citizenship"));

        customCacheService.create("GET_WEATHER_API_URL", "http://api.weatherstack.com/current?access_key=%s&query=%s");
        log.info("Created Dummy Data !!!");
    }*/


}