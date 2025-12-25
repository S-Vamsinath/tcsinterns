package com.tcs.tcsinterns.api;

import com.tcs.tcsinterns.dto.MentorDTO;
import com.tcs.tcsinterns.dto.ProjectDTO;
import com.tcs.tcsinterns.service.ProjectAllocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/tcsinterns")
public class ProjectAllocationAPI {
    @Autowired
    private ProjectAllocationService projectservice;
    @Autowired
    private Environment environment;

    @PostMapping("/project")
    public ResponseEntity<String> allocateProject(@Valid @RequestBody ProjectDTO projectDTO){
        Integer ProjectId= projectservice.allocateProject(projectDTO);
        String message=environment.getProperty("API.ALLOCATION_SUCCESS");
        return new ResponseEntity<>(message+":"+ProjectId, HttpStatus.CREATED);
    }

    @GetMapping("/mentor/{count}")
    public ResponseEntity<List<MentorDTO>> getMentors(@PathVariable Integer count){
        List<MentorDTO> list=projectservice.getMentors(count);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PutMapping("/project/{projectid}/{mentorid}")
    public ResponseEntity<String> updateProjectMentor(
            @PathVariable Integer projectid,
            @Digits(integer = 4,fraction = 0,message="{mentor.id.invaild}")
            @PathVariable
            Integer mentorid) {
        projectservice.updateProjectMentor(projectid,mentorid);
        String message= environment.getProperty("API.PROJECT_UPDATE_SUCCESS");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/project/{projectid}")
    public ResponseEntity<String> deleteProject(
            @PathVariable Integer projectid){
        projectservice.deleteProject(projectid);
        String message = environment.getProperty("API.PROJECT_DELETE_SUCCESS");
        return  new ResponseEntity<>(message,HttpStatus.OK);
    }

}
