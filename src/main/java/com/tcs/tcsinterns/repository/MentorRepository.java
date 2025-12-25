package com.tcs.tcsinterns.repository;

import com.tcs.tcsinterns.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor,Integer> {
    List<Mentor> findByNumberOfProjectsMentored(Integer numberProjectsMentored);

}
