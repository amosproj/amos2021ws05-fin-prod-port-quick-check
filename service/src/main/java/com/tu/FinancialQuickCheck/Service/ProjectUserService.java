package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ListOfProjectUserDto;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



/**
 * Underlying Service for (un)assigning users to projects.
 * Acts as connection between presentation Layer and persistence layer.
 */

@Service
public class ProjectUserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final ProjectUserRepository repository;

    @Autowired
    public ProjectUserService(UserRepository userRepository,
                              ProjectRepository projectRepository,
                              ProjectUserRepository projectUserRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.repository = projectUserRepository;
    }

    //TODO: (prio: high) include in API documentation (Project.yaml)
    public List<ProjectUserDto> getProjectUsersByProjectId(int projectID) {
        Optional<ProjectEntity> entity = projectRepository.findById(projectID);

        if (entity.isEmpty()) {
            return null;
        } else {
            return new ListOfProjectUserDto(entity.get().projectUserEntities).projectUsers;
        }
    }


    //TODO: (test)
    public List<ProjectUserDto> updateProjectUsers(int projectID, List<ProjectUserDto> projectUsers) {

        if(projectRepository.existsById(projectID)){
            List<ProjectUserEntity> entities = new ArrayList<>();
            List<ProjectUserDto> out = new ArrayList<>();

            for (ProjectUserDto projectUser: projectUsers) {
                ProjectUserEntity update = updateProjectUser(projectID, projectUser);
                if(update != null){
                    entities.add(update);
                    out.add(new ProjectUserDto(update));
                }else{
                    return null;
                }
            }

            repository.saveAll(entities);
            return out;
        }else{
            return null;
        }
    }


    //TODO: (test)
    public ProjectUserEntity updateProjectUser(int projectID, ProjectUserDto u) {

        if (userRepository.existsById(u.userID.toString())) {
            Optional<ProjectUserEntity> update = repository.findById(
                    new ProjectUserId(
                            projectRepository.findById(projectID).get(),
                            userRepository.findById(u.userID.toString()).get()));
            if (update.isEmpty()) {
                throw new BadRequest("User is not assigned to project.");
            } else {

                if (u.role != null) {update.get().role = u.role;}
                return update.get();
            }
        } else {
            return null;
        }
    }


    //TODO: (test)
    public Boolean wrapperDeleteProjectUser(int projectID, List<ProjectUserDto> projectUsers){
        Boolean tmp = Boolean.TRUE;
        for(ProjectUserDto projectUser: projectUsers){
            if(tmp){
                tmp = deleteProjectUser(projectID, projectUser);
            }
        }

        return tmp;
    }


    // TODO: (test)
    public Boolean deleteProjectUser(int projectID, ProjectUserDto projectUserDto) {

        if (projectUserDto.userID != null) {

            if (!repository.existsById(new ProjectUserId(projectRepository.findById(projectID).get(),
                userRepository.findById(projectUserDto.userID.toString()).get()))) {
                throw new ResourceNotFound("User is not assigned to project.");
            } else {

                repository.deleteById(new ProjectUserId(
                        projectRepository.getById(projectID),
                        userRepository.getById(projectUserDto.userID.toString())));

                return Boolean.TRUE;
            }

        } else {
            return Boolean.FALSE;
        }
    }
}
