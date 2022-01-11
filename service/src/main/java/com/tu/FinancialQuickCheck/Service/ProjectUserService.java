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


//    public List<ProjectUserDto> getProjectUsersByProjectId(int projectID) {
//
//        Optional<ProjectEntity> entity = projectRepository.findById(projectID);
//
//        if (entity.isEmpty()) {
//            throw new ResourceNotFound("projectID " + projectID + " not found");
//        } else {
//
//            Iterable<ProjectUserEntity> entities = entity.get().projectUserEntities;
//
//            List<ProjectUserDto> projectUserDtos = new ArrayList<>() {
//            };
//
//            for (ProjectUserEntity tmp : entities) {
//                projectUserDtos.add(new ProjectUserDto(
//                        UUID.fromString(tmp.projectUserId.getUser().id),
//                        tmp.role,
//                        tmp.projectUserId.getUser().email,
//                        tmp.projectUserId.getProject().id,
//                        tmp.projectUserId.getUser().username
//                ));
//            }
//
//            return projectUserDtos;
//        }
//    }


    /**
     * This method is updating a list of users for projects by its ID.
     *
     * @param projectID The ID of the project for which users should be updated.
     * @param projectUsers A list of project users for which specific project users should be updated or added.
     * @return A updated list of project user data transfer object and new users.
     */
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

    /**
     * This method is updating one user for a project.
     *
     * @param projectID The ID of the project for which a user should be updated.
     * @param u The project user data transfer object.
     * @throws BadRequest When the user is not assigned to a project.
     * @return The updated project user entity.
     */
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


    /**
     * This method acts as a wrapper for deleting users from projects.
     *
     * @param projectID The ID of the project for which users should be deleted.
     * @param projectUsers The project users data transfer object.
     */
    //TODO: (test)
    public void wrapperDeleteProjectUser(int projectID, List<ProjectUserDto> projectUsers){

        for(ProjectUserDto projectUser: projectUsers){
            deleteProjectUser(projectID, projectUser);
        }
    }


    /**
     * This method is deleting users from projects.
     *
     * @param projectID The ID of the project for which users should be deleted.
     * @param projectUserDto The project users data transfer object.
     * @throws ResourceNotFound When the user is not assigned to the project.
     * @throws BadRequest When the input is missing or incorrect.
     */
    // TODO: (Max) implement testcases
    public void deleteProjectUser(int projectID, ProjectUserDto projectUserDto) {

        if (projectUserDto.userID != null) {

            if (!repository.existsById(new ProjectUserId(projectRepository.findById(projectID).get(),
                userRepository.findById(projectUserDto.userID.toString()).get()))) {
                throw new ResourceNotFound("User is not assigned to project.");
            }else{

                repository.deleteById(new ProjectUserId(
                        projectRepository.getById(projectID),
                        userRepository.getById(projectUserDto.userID.toString())));
            }

        }else{
            throw new BadRequest("Input missing/is incorrect");
        }
    }
}
