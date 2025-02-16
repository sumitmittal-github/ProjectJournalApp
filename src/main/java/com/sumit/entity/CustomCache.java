package com.sumit.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "custom_cache")
@Data
public class CustomCache {

    @NonNull
    private String key;

    @NonNull
    private String value;


}