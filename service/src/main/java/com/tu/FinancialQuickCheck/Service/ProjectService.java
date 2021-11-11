package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class ProjectService {


    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // only return projectId, projectName
    public Iterable<ProjectEntity> getAllProjects(){

        return projectRepository.getProjects();
    }


    public ProjectEntity createProject(ProjectEntity project) {
        return projectRepository.save(project);
    }

    public Optional<ProjectEntity> findById(int projectID) {
        return projectRepository.findById(projectID);
    }

    public void deleteProject(int projectID) {
        projectRepository.deleteById(projectID);
    }

}
