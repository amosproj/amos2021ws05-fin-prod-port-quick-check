package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



/**
 * Underlying Service for User- aka. member-management.
 * Acts as connection between presentation Layer and persistence layer.
 */

@Service
public class ProjectUserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository repository;

    @Autowired
    public ProjectUserService(UserRepository userRepository,
                              ProjectRepository projectRepository,
                              ProjectUserRepository projectUserRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.repository = projectUserRepository;
    }


    public List<ProjectUserDto> getProjectUsersByProjectId(int projectID) {

        Optional<ProjectEntity> entity = projectRepository.findById(projectID);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        } else {

            Iterable<ProjectUserEntity> entities = entity.get().projectUserEntities;

            List<ProjectUserDto> projectUserDtos = new ArrayList<>() {
            };

            for (ProjectUserEntity tmp : entities) {
                projectUserDtos.add(new ProjectUserDto(
                        UUID.fromString(tmp.projectUserId.getUser().id),
                        tmp.role,
                        tmp.projectUserId.getUser().email,
                        tmp.projectUserId.getProject().id,
                        tmp.projectUserId.getUser().username
                ));
            }

            return projectUserDtos;
        }
    }

    //TODO: (test)
    public List<ProjectUserDto> wrapperCreateProjectUser(int projectID, List<ProjectUserDto> projectUsers) {
        List<ProjectUserEntity> entities = new ArrayList<>();
        List<ProjectUserDto> out = new ArrayList<>();

        for (ProjectUserDto projectUserDto : projectUsers) {
                ProjectUserEntity entity = createProjectUser(projectID, projectUserDto);
                entities.add(entity);
                out.add(new ProjectUserDto(entity));
        }

        repository.saveAll(entities);

        return out;
    }


    //TODO: (done - needs review) check for role -> if role dosent exist send "BAD REQUEST"
    private ProjectUserEntity createProjectUser(int projectID, ProjectUserDto projectUserDto){
        if (projectUserDto.role == null) {
            throw new BadRequest("Input is missing/incorrect.");
        } else if (!userRepository.existsByEmail(projectUserDto.userEmail)) {
            throw new ResourceNotFound("User does not exist.");
        } else if (!projectRepository.existsById(projectID)) {
            throw new ResourceNotFound("Project does not exist.");
        } else {
            ProjectUserEntity entity = new ProjectUserEntity();

            UserEntity u = userRepository.findById(projectUserDto.userID.toString()).get();
            entity.projectUserId = new ProjectUserId(
                    projectRepository.findById(projectID).get(), u);
            entity.role = projectUserDto.role;

            return entity;
        }
    }


    //TODO: (test)
    public List<ProjectUserDto> wrapperUpdateProjectUser(int projectID, List<ProjectUserDto> projectUsers) {
        List<ProjectUserEntity> entities = new ArrayList<>();
        List<ProjectUserDto> out = new ArrayList<>();

        for (ProjectUserDto projectUserDto : projectUsers) {
            if (!repository.existsById(new ProjectUserId(projectRepository.findById(projectID).get(),
                    userRepository.findById(projectUserDto.userEmail).get()))) {
                throw new ResourceNotFound("User is not assigned to project.");
            } else {
                ProjectUserEntity update = repository.findById(new ProjectUserId(
                        projectRepository.findById(projectID).get(),
                        userRepository.findById(projectUserDto.userEmail).get())).get();
                updateProjectUser(projectUserDto,update);
                entities.add(update);
                out.add(new ProjectUserDto(update));
            }
        }

        repository.saveAll(entities);

        return out;
    }

    //TODO: (test)
    public void updateProjectUser(ProjectUserDto projectUserDto,ProjectUserEntity projectUserEntity) {

        if (projectUserDto.role != null) {
            projectUserEntity.role = projectUserDto.role;
        }

    }


    //TODO: (test)
    public void wrapperDeleteProjectUser(int projectID, List<ProjectUserDto> projectUsers){

        for(ProjectUserDto projectUser: projectUsers){
            deleteProjectUser(projectID, projectUser);
        }
    }


    // TODO: (Max) implement testcases
    public void deleteProjectUser(int projectID, ProjectUserDto projectUserDto) {

        if (projectUserDto.userID != null) {

            if (!repository.existsById(new ProjectUserId(projectRepository.findById(projectID).get(),
                userRepository.findById(projectUserDto.userID.toString()).get()))) {
                throw new ResourceNotFound("User is not assigned to project.");
            } else {

                repository.deleteById(new ProjectUserId(
                        projectRepository.getById(projectID),
                        userRepository.getById(projectUserDto.userID.toString())));
            }

        } else {
            throw new BadRequest("Input missing/is incorrect");
        }
    }
}
