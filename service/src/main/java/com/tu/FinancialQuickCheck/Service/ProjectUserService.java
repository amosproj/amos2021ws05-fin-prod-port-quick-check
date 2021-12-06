package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import com.tu.FinancialQuickCheck.dto.UserDto;
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
    private final ProjectUserRepository projectUserRepository;

    @Autowired
    public ProjectUserService(UserRepository userRepository,
                              ProjectRepository projectRepository,
                              ProjectUserRepository projectUserRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
    }


    public List<ProjectUserDto> getProjectUsersByProjectId(int projectID){

        Optional<ProjectEntity> entity = projectRepository.findById(projectID);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        } else {

            Iterable<ProjectUserEntity> entities = entity.get().projectUserEntities;

            List<ProjectUserDto> projectUserDtos = new ArrayList<>() {
            };

            for (ProjectUserEntity tmp : entities) {
                projectUserDtos.add(new ProjectUserDto(
                        UUID.fromString(tmp.projectUserId.getUserid().id),
                        tmp.role,
                        tmp.projectUserId.getUserid().email,
                        tmp.projectUserId.getProjectid().id,
                        tmp.projectUserId.getUserid().username
                ));
            }

            return projectUserDtos;
        }
    }


    public void createProjectUser(int projectID, String userEmail, ProjectUserDto projectUserDto){

        ProjectUserEntity entity = new ProjectUserEntity();

        if(!userRepository.existsById(userEmail)){
            throw new ResourceNotFound("User does not exist.");
        } else if (!projectRepository.existsById(projectID)){
            throw new ResourceNotFound("Project does not exist.");
        } else {
            entity.projectUserId = new ProjectUserId(
                    projectRepository.findById(projectID).get(),
                    userRepository.findById(userEmail).get()
                    );
            entity.role = projectUserDto.role;

            projectUserRepository.save(entity);
        }
    }


    public void updateProjectUser(int projectID, UUID userID, ProjectUserDto projectUserDto){

        if(!projectUserRepository.existsById(new ProjectUserId(projectRepository.findById(projectID).get(),
                userRepository.findById(projectUserDto.userEmail).get()))){
            throw new ResourceNotFound("User is not assigned to project.");
        }else{
            projectUserRepository.findById(new ProjectUserId(projectRepository.findById(projectID).get(),
                    userRepository.findById(projectUserDto.userEmail).get()))
                    .map(
                        projectUser -> {
                            if(projectUserDto.role != null){
                                projectUser.role = projectUserDto.role;
                            }

                            return projectUserRepository.save(projectUser);
                        });
        }
    }

    // TODO: implement deleteUsers()
    // löscht Datensätze in project_user_entity
    // repository: projectUserRepository
    // z.B. projectUserRepository.deleteAll(); (gibt aber auch noch andere Möglichkeiten)
}
