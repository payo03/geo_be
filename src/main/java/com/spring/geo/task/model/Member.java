package com.spring.geo.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Member {

    private int memberNumber;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberPhoneNumber;
    private String memberRegistrationDate;
    private String memberWebsite;
    private int memberStatus;
    private int memberLevel;
    private int memberImage;
    private boolean remember;

}
