package com.tcs.tcsinterns;

import com.tcs.tcsinterns.dto.MentorDTO;
import com.tcs.tcsinterns.dto.ProjectDTO;
import com.tcs.tcsinterns.entity.Mentor;
import com.tcs.tcsinterns.exception.TcsInternException;
import com.tcs.tcsinterns.repository.MentorRepository;
import com.tcs.tcsinterns.repository.ProjectRepository;
import com.tcs.tcsinterns.service.ProjectAllocationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class TcsInternsApplicationTests {

	@Mock
	private MentorRepository mentorRepository;

	@Mock
	private ProjectRepository projectRepository;

	@InjectMocks
	private ProjectAllocationServiceImpl projectAllocationService;

	// 1️⃣ Mentor already mentoring 3 projects
	@Test
	void allocateProjectCannotAllocateTest() {

		Mentor mentor = new Mentor();
		mentor.setMentorId(1234);
		mentor.setMentorName("Ravi");
		mentor.setNumberOfProjectsMentored(3);

		when(mentorRepository.findById(1234))
				.thenReturn(Optional.of(mentor));

		MentorDTO mentorDTO = new MentorDTO();
		mentorDTO.setMentorId(1234);

		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectName("AI Project");
		projectDTO.setIdeaOwner("Vamsi");
		projectDTO.setReleaseDate(LocalDate.now());
		projectDTO.setMentorDTO(mentorDTO);

		TcsInternException exception = Assertions.assertThrows(
				TcsInternException.class,
				() -> projectAllocationService.allocateProject(projectDTO)
		);

		Assertions.assertEquals(
				"Service.CANNOT_ALLOCATE_PROJECT",
				exception.getMessage()
		);
	}

	// 2️⃣ Mentor not found
	@Test
	void allocateProjectMentorNotFoundTest() {

		when(mentorRepository.findById(9999))
				.thenReturn(Optional.empty());

		MentorDTO mentorDTO = new MentorDTO();
		mentorDTO.setMentorId(9999);

		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectName("Cloud Project");
		projectDTO.setIdeaOwner("Nath");
		projectDTO.setReleaseDate(LocalDate.now());
		projectDTO.setMentorDTO(mentorDTO);

		TcsInternException exception = Assertions.assertThrows(
				TcsInternException.class,
				() -> projectAllocationService.allocateProject(projectDTO)
		);

		Assertions.assertEquals(
				"Service.MENTOR_NOT_FOUND",
				exception.getMessage()
		);
	}
}