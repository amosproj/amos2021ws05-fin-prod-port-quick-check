package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ProjectNotFound;
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

    // TODO: hier fehlen noch die members und die productAreas
    public ProjectDto findById(int projectID) {

        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);

        if (projectEntity.isEmpty()) {
            throw new ProjectNotFound("projectID " + projectID + " not found");
        }else{
            Integer[] members = {1,2,3};
            Integer[] productAreas = {1,3};
            return new ProjectDto(projectEntity.get().id, projectEntity.get().name,
                    projectEntity.get().creator_id, members, productAreas);
        }

    }

    // TODO: hier fehlen noch die members und die productAreas
    // kann man bestehend Produktbereiche ändern? oder nur neue hinzufügen?
    public void updateById(ProjectDto projectDto) {

        if (!projectRepository.existsById(projectDto.id)) {
            throw new ProjectNotFound("projectID " + projectDto.id + " not found");
        }else{

            projectRepository.findById(projectDto.id).map(
                    project -> {
                        project.id = projectDto.id;
                        project.name = projectDto.name;
                        project.creator_id = projectDto.creatorID;
                        return projectRepository.save(project);
                    });
        }
    }

    public void deleteProject(int projectID) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);
        if (projectEntity.isEmpty()) {
            throw new ProjectNotFound("projectID " + projectID + " not found");
        }else{
            projectRepository.deleteById(projectID);
        }
    }

}
