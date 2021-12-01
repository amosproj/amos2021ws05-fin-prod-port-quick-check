package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Underlying Service for User- aka. member-management.
 * Acts as connection between presentation Layer and persistence layer.
 */

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    /**
     * returns a List of all User
     */
    public List<UserDto> findAllUser() {

        List<UserDto> userList = new ArrayList<>();
        Iterable<UserEntity> allUserEntitys = repository.findAll();

        for (UserEntity userEntity : allUserEntitys) {
            UserDto userDto = new UserDto(UUID.fromString(userEntity.id), userEntity.email, userEntity.username);
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
                userDto.username = userEntity.username;
                userDto.id = UUID.fromString(userEntity.id);
                return userDto;
            }
        }
        throw new ResourceNotFound("User Email " + email + " not found");
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

        if(userDto.username != null && userDto.email != null && userDto.password != null
                && validateEmail(userDto.email)){
            UserEntity newUser = new UserEntity();
            newUser.id = UUID.randomUUID().toString();
            newUser.username = userDto.username;
            newUser.email = userDto.email;
            newUser.password = userDto.password;
            repository.save(newUser);
            return new UserDto(UUID.fromString(newUser.id), newUser.email, newUser.username);
        }else{
            return null;
        }
    }


    /**
     * search for ID in repository and updates if found
     *
     * @param userDto
     * @param userID
     */
    public UserDto updateByUserID(UserDto userDto, String userID) {

        Optional<UserEntity> entity = repository.findById(userID);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else if ((userDto.email == null && userDto.username == null
                && userDto.password == null) || (userDto.email != null && !validateEmail(userDto.email))){
            return null;
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

            return new UserDto(UUID.fromString(entity.get().id), entity.get().email, entity.get().username);

        }
    }

    /**
     * Deletes User
     *
     * @param userID
     */
    public void deleteUserById(String userID) {
        Optional<UserEntity> entity = repository.findById(userID);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else {
            repository.deleteById(userID);
        }
    }


    public boolean validateEmail(String emailAddress){
        // regexPattern from RFC 5322 for Email Validation
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
