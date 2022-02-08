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
 * Service for (un)assigning users to projects, alternative updating existing assigned Users.
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
     * This method is updating a list of users for projects by its ID. Updating Existing Users or adding new.
     *
     * @param projectID The ID of the project for which users should be updated.
     * @param projectUsers A list of project users for which specific project, users should be updated or added.
     * @return A updated list of project user data transfer object and new users. null at failure
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

    /**
     * Updates single User of a specific Project (over projectID). Used in updateProjectUsers.
     *
     * @param projectID The ID of the project for which user should be updated.
     * @param u UserDto of User who should be updated
     * @return updated ProjectUserDto at success. null at failure
     */
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
     * Multiple deleting (removing) of Project Users from Project.
     *
     * @param projectID the ID of the project for which users should be removed.
     * @param projectUsers List of UserDto's of projectUsers who should be removed.
     * @return true at success, false at failure
     */
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

    /**
     * Single deleting (removing) of Project User from Project.
     *
     * @param projectID the ID of the project for which user should be removed.
     * @param projectUserDto UserDto of projectUser who should be removed.
     * @return true at success, false at failure
     */
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
