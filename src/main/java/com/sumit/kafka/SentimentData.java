package com.sumit.kafka;

import com.sumit.constant.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentimentData {

    private String username;
    private String email;
    private Sentiment sentiment;

}