package com.sumit.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    private String email;

    private boolean sentimentAnalysisOpted;

    private List<String> roles = new ArrayList<>();

    @DBRef
    List<JournalEntry> journalEntries = new ArrayList<>();

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    public User(@NonNull String username, @NonNull String password, String email, boolean sentimentAnalysisOpted, List<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sentimentAnalysisOpted = sentimentAnalysisOpted;
        this.roles = roles;
    }


}