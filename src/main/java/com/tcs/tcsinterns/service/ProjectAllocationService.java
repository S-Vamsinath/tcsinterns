package com.tcs.tcsinterns.service;

import com.tcs.tcsinterns.dto.MentorDTO;
import com.tcs.tcsinterns.dto.ProjectDTO;

import java.util.List;

public interface ProjectAllocationService {
    Integer allocateProject(ProjectDTO project);
    List<MentorDTO> getMentors(Integer count);
    void updateProjectMentor(Integer projectid,Integer mentorid);
    void deleteProject(Integer projectid);
}
