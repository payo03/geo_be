package com.spring.geo.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {
    
    private int fromMemberNumber;
    private int toMemberNumber;
    private String content;
    private String memberName;
    private int memberImage;
}
