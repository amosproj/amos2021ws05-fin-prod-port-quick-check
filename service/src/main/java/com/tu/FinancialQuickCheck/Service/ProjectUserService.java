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

    public List<ProjectUserDto> getProjectUsersByProjectId(int projectID) {
        Optional<ProjectEntity> entity = projectRepository.findById(projectID);

        if (entity.isEmpty()) {
            return null;
        } else {
            return new ListOfProjectUserDto(entity.get().projectUserEntities).projectUsers;
        }
    }

    /**
     * This method is updating a list of users for projects by its ID.
     *
     * @param projectID The ID of the project for which users should be updated.
     * @param projectUsers A list of project users for which specific project users should be updated or added.
     * @return A updated list of project user data transfer object and new users.
     */
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

    public Boolean wrapperDeleteProjectUser(int projectID, List<ProjectUserDto> projectUsers){
        Boolean tmp = Boolean.TRUE;
        if(projectRepository.existsById(projectID)){
            for(ProjectUserDto projectUser: projectUsers){
                if(tmp){
                    tmp = deleteProjectUser(projectID, projectUser);
                }
            }
        }else{
            tmp = Boolean.FALSE;
        }

        return tmp;
    }

    public Boolean deleteProjectUser(int projectID, ProjectUserDto projectUserDto) {

        if (projectUserDto.userID != null) {
            ProjectEntity project = projectRepository.findById(projectID).get();
            UserEntity user = userRepository.findById(projectUserDto.userID.toString()).get();
            ProjectUserId tmp = new ProjectUserId(project, user);
            if (!repository.existsById(tmp)) {
                throw new ResourceNotFound("User is not assigned to project.");
            }else{

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
