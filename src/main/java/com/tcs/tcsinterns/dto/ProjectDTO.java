package com.tcs.tcsinterns.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ProjectDTO {

    private Integer projectId;
    @NotNull(message = "{project.name.required}")
    private String projectName;
    @NotNull(message = "{idea.owner.required}")
    private String ideaOwner;
    @NotNull(message = "{release.date.required}")
    private LocalDate releaseDate;
    @NotNull(message = "{mentordetails.required}")
    @Valid
    private MentorDTO mentorDTO;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIdeaOwner() {
        return ideaOwner;
    }

    public void setIdeaOwner(String ideaOwner) {
        this.ideaOwner = ideaOwner;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MentorDTO getMentorDTO() {
        return mentorDTO;
    }

    public void setMentorDTO(MentorDTO mentorDTO) {
        this.mentorDTO = mentorDTO;
    }
}