package com.tcs.tcsinterns.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Value;

@Data
public class MentorDTO {
    @NotNull(message = "{Mentor.id is required}")
    @Min(value= 1000,message = "{mentor.id.invaild}")
    @Max(value= 9999,message ="{mentor.id.invaild}")

    private Integer mentorId;
    private String mentorName;
    private Integer numberOfProjectsMentored;

    public Integer getMentorId() {
        return mentorId;
    }

    public void setMentorId(Integer mentorId) {
        this.mentorId = mentorId;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public Integer getNumberOfProjectsMentored() {
        return numberOfProjectsMentored;
    }

    public void setNumberOfProjectsMentored(Integer numberOfProjectsMentored) {
        this.numberOfProjectsMentored = numberOfProjectsMentored;
    }
}