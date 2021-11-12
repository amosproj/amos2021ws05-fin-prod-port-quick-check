package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {


    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // only return projectId, projectName
    public List<SmallProjectDto> getAllProjects(){

        List<SmallProjectDto> smallProjectDtos = new ArrayList<>() {
        };
        Iterable<ProjectEntity> projectEntities = projectRepository.findAll();
        
        for(ProjectEntity tmp : projectEntities){
            smallProjectDtos.add(new SmallProjectDto(tmp.id, tmp.name));
        }

        return smallProjectDtos;
    }

    // TODO: muss noch um ProductBereiche erweitert werden und Mitglieder
    // die Erweiterung für Produktbereiche hängt von der Definition für Produktbereiche ab
    public ProjectDto createProject(ProjectDto project) {
        ProjectEntity newProject = new ProjectEntity(project.name, project.creatorID);
        projectRepository.save(newProject);
        project.id = newProject.id;
        return project;
    }

    public Optional<ProjectEntity> findById(int projectID) {
        return projectRepository.findById(projectID);
    }

    public void deleteProject(int projectID) {
        projectRepository.deleteById(projectID);
    }

}
