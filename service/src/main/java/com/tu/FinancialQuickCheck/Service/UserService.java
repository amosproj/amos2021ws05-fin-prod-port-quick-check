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
public class UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       ProjectRepository projectRepository,
                       ProjectUserRepository projectUserRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
    }

    /**
     * create new User and saves in (user)repository
     * TODO: attribut email ist anders benannt in API, muss angepasst werden
     * TODO: check input: email sollte in form einer email sein
     *
     * @param userDto
     * @return UserDto
     */
    public UserDto createUser(UserDto userDto) {

        UserEntity newUser = new UserEntity();
        newUser.id = UUID.randomUUID().toString();
        newUser.username = userDto.username;
        newUser.email = userDto.email;
        newUser.password = userDto.password;
        userRepository.save(newUser);
        return new UserDto(UUID.fromString(newUser.id), newUser.email, newUser.username);
    }

    /**
     * returns a List of all User
     * TODO: wollen wir hier wirklich das pw mit zurück geben?
     */
    public List<UserDto> findAllUser() {

        List<UserDto> userList = new ArrayList<>();
        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for (UserEntity userEntity : allUserEntitys) {
            UserDto userDto = new UserDto(UUID.fromString(userEntity.id), userEntity.email, userEntity.username);

            userDto.password = userEntity.password;
            userList.add(userDto);
        }
        return userList;
    }

    /**
     * Search for User by given email
     *
     * TODO: find faster way to access Users other than iterate over all of them
     *
     * @param email
     * @return userDto
     */
    public UserDto findByEmail(String email) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for (UserEntity userEntity : allUserEntitys) {
            if (userEntity.email.equals(email)) {
                UserDto userDto = new UserDto(userEntity.email);
                userDto.id = UUID.fromString(userEntity.id);
                return userDto;
            }
        }
        throw new ResourceNotFound("User Email " + email + " not found");
    }

    /**
     * search for ID in repository and updates if found
     * TODO: email sollte anpassbar sein
     *
     * @param userDto
     * @param email
     */
    public void updateByEmail(UserDto userDto, String email) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for (UserEntity userEntity : allUserEntitys) {
            if (userEntity.email.equals(email)) {
                if (userDto.email != null) {
                    userEntity.email = userDto.email;
                }

                if (userDto.password != null) {
                    userEntity.password = userDto.password;
                }

                if (userDto.username != null) {
                    userEntity.username = userDto.username;
                }
                userRepository.save(userEntity);
                return;
            }
        }
        throw new ResourceNotFound(" User Email |" + email + "| not found");

    }

    public void updateByUserID(UserDto userDto, String userID) {

        UUID u = UUID.fromString(userID);

        Optional<UserEntity> entity = userRepository.findById(userID);

        if (!userRepository.existsById(userID)) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else {

            entity.map(
                    user -> {
                        if (userDto.email != null) {
                            user.email = userDto.email;
                        }

                        if (userDto.password != null) {
                            user.password = userDto.password;
                        }

                        if (userDto.username != null) {
                            user.username = userDto.username;
                        }

                        return userRepository.save(user);
                    });

        }
    }

    /**
     * Deletes User
     * TODO: discuss implementation
     *
     * @param email
     */

    public void deleteUser(String email) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for (UserEntity userEntity : allUserEntitys) {
            if (userEntity.email.equals(email)) {
                Optional<UserEntity> tmpEnt = userRepository.findById(userEntity.id);
                if (tmpEnt.isEmpty()) {
                    throw new ResourceNotFound("User email |" + email + "| not found");
                } else {
                    userRepository.deleteById(tmpEnt.get().id);
                }
                return;
            }
        }
    }


    public void deleteUserById(String userID) {
        Optional<UserEntity> entity = userRepository.findById(userID);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else {
            userRepository.deleteById(userID);
        }

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


    public void createProjectUser(int projectID, String userID, ProjectUserDto projectUserDto){

        ProjectUserEntity entity = new ProjectUserEntity();

        if(!userRepository.existsById(userID) || !projectRepository.existsById(projectID)){
            throw new ResourceNotFound("User or project does not exist.");
        }else{
            entity.projectUserId = new ProjectUserId(
                    projectRepository.findById(projectID).get(),
                    userRepository.findById(userID).get()
                    );
            entity.role = projectUserDto.role;

            projectUserRepository.save(entity);
        }
    }


    public void updateProjectUser(int projectID, UUID userID, ProjectUserDto projectUserDto){

        if(!projectUserRepository.existsById(new ProjectUserId(projectRepository.findById(projectID).get(),
                userRepository.findById(userID.toString()).get()))){
            throw new ResourceNotFound("User is not assigned to project.");
        }else{
            projectUserRepository.findById(new ProjectUserId(projectRepository.findById(projectID).get(),
                    userRepository.findById(userID.toString()).get()))
                    .map(
                        projectUser -> {
                            if(projectUserDto.role != null){
                                projectUser.role = projectUserDto.role;
                            }

                            return projectUserRepository.save(projectUser);
                        });
        }
    }
}
