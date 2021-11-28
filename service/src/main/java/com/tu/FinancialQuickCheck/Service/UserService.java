package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
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

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
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
        repository.save(newUser);
        return new UserDto(UUID.fromString(newUser.id), newUser.email, newUser.username);
    }

    /**
     * returns a List of all User
     * TODO: wollen wir hier wirklich das pw mit zurück geben?
     */
    public List<UserDto> findAllUser() {

        List<UserDto> userList = new ArrayList<>();
        Iterable<UserEntity> allUserEntitys = repository.findAll();

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

        Iterable<UserEntity> allUserEntitys = repository.findAll();

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

        Iterable<UserEntity> allUserEntitys = repository.findAll();

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
                repository.save(userEntity);
                return;
            }
        }
        throw new ResourceNotFound(" User Email |" + email + "| not found");

    }

    public void updateByUserID(UserDto userDto, String userID) {

        UUID u = UUID.fromString(userID);

        Optional<UserEntity> entity = repository.findById(userID);

        if (!repository.existsById(userID)) {
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

                        return repository.save(user);
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

        Iterable<UserEntity> allUserEntitys = repository.findAll();

        for (UserEntity userEntity : allUserEntitys) {
            if (userEntity.email.equals(email)) {
                Optional<UserEntity> tmpEnt = repository.findById(userEntity.id);
                if (tmpEnt.isEmpty()) {
                    throw new ResourceNotFound("User email |" + email + "| not found");
                } else {
                    repository.deleteById(tmpEnt.get().id);
                }
                return;
            }
        }
    }


    public void deleteUserById(String userID) {
        Optional<UserEntity> entity = repository.findById(userID);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else {
            repository.deleteById(userID);
        }

    }
}
