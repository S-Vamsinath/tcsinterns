package com.tcs.tcsinterns.service;

import com.tcs.tcsinterns.dto.MentorDTO;
import com.tcs.tcsinterns.dto.ProjectDTO;
import com.tcs.tcsinterns.entity.Mentor;
import com.tcs.tcsinterns.entity.Project;
import com.tcs.tcsinterns.exception.TcsInternException;
import com.tcs.tcsinterns.repository.MentorRepository;
import com.tcs.tcsinterns.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProjectAllocationServiceImpl implements ProjectAllocationService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // 1️⃣ Allocate Project
    @Override
    @Transactional
    public Integer allocateProject(ProjectDTO projectDTO) {

        Mentor mentor = mentorRepository.findById(
                projectDTO.getMentorDTO().getMentorId()
        ).orElseThrow(() ->
                new TcsInternException("Service.MENTOR_NOT_FOUND")
        );

        if (mentor.getNumberOfProjectsMentored() >= 3) {
            throw new TcsInternException("Service.CANNOT_ALLOCATE_PROJECT");
        }

        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setIdeaOwner(projectDTO.getIdeaOwner());
        project.setReleaseDate(projectDTO.getReleaseDate());
        project.setMentor(mentor);

        mentor.setNumberOfProjectsMentored(
                mentor.getNumberOfProjectsMentored() + 1
        );

        Project savedProject = projectRepository.save(project);

        // 🔥 THIS LINE IS CRITICAL
        return savedProject.getProjectId();
    }

    // 2️⃣ Get mentors by project count
    @Override
    public List<MentorDTO> getMentors(Integer count) {

        List<Mentor> mentors =
                mentorRepository.findByNumberOfProjectsMentored(count);

        if (mentors.isEmpty()) {
            throw new TcsInternException("Service.MENTOR_NOT_FOUND");
        }

        List<MentorDTO> dtoList = new ArrayList<>();

        for (Mentor mentor : mentors) {
            MentorDTO dto = new MentorDTO();
            dto.setMentorId(mentor.getMentorId());
            dto.setMentorName(mentor.getMentorName());
            dto.setNumberOfProjectsMentored(
                    mentor.getNumberOfProjectsMentored());
            dtoList.add(dto);
        }

        return dtoList;
    }

    // 3️⃣ Update project mentor
    @Override
    public void updateProjectMentor(Integer projectId, Integer mentorId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new TcsInternException("Service.PROJECT_NOT_FOUND"));

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() ->
                        new TcsInternException("Service.MENTOR_NOT_FOUND"));

        project.setMentor(mentor);
    }

    // 4️⃣ Delete project
    @Override
    public void deleteProject(Integer projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new TcsInternException("Service.PROJECT_NOT_FOUND"));

        projectRepository.delete(project);
    }
}