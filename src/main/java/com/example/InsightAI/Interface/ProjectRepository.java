package com.example.InsightAI.Interface;

import com.example.InsightAI.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,String>  {
    List<Project> findByUserId(String userId);
}
