package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ListOfRatingDto;
import com.tu.FinancialQuickCheck.dto.ListOfUserDto;
import com.tu.FinancialQuickCheck.dto.UserDto;
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
     * returns a List of all Users without passwords
     */
    public List<UserDto> getAllUsers() {
        return new ListOfUserDto(this.repository.findAll()).users;
    }


    /**
     * Search for User by given email
     *
     * @param email
     * @return userDto
     */
    public UserDto findByEmail(String email) {
        Optional<UserEntity> entity = repository.findByEmail(email);

        if(entity.isPresent()){
            return new UserDto(entity.get());

        }else{
            return null;
        }
    }


    /**
     * create new User and saves in (user)repository
     *
     * @param userDto
     * @return UserDto
     */

    //TODO: (prio: low) add constraints for input --> check if String is empty else return Bad Request
    public UserDto createUser(UserDto userDto) {

        if (userDto.userName != null && userDto.userEmail != null && userDto.password != null
                && userDto.validateEmail(userDto.userEmail) && !repository.existsById(userDto.userEmail)) {
            UserEntity newUser = new UserEntity();
            newUser.id = UUID.randomUUID().toString();
            newUser.username = userDto.userName;
            newUser.email = userDto.userEmail;
            newUser.password = userDto.password;
            repository.save(newUser);

            return new UserDto(UUID.fromString(newUser.id), newUser.email, newUser.username);
        }else{
            return null;
        }
    }



    //TODO: (prio: medium) Discuss Update By ID and Update By Email with Frontend, should it be possible to update userEmail --> yes
    //TODO: (prio: low) add constraints for input --> check if String is empty else return Bad Request
    public UserDto updateUserByEmail(UserDto userDto, String email) {

        if ((userDto.userEmail == null && userDto.userName == null && userDto.password == null) ||
                (userDto.userEmail != null && !userDto.validateEmail(userDto.userEmail))){
            throw new BadRequest("Input is missing/incorrect");
        }

        Optional<UserEntity> entity = repository.findByEmail(email);

        if (entity.isEmpty()) {
            return null;
        } else {
            entity.map(
                    user -> {
                        if (userDto.userEmail != null) {
                            user.email = userDto.userEmail;
                        }

                        if (userDto.password != null) {
                            user.password = userDto.password;
                        }

                        if (userDto.userName != null) {
                            user.username = userDto.userName;
                        }

                        return repository.save(user);
                    });

            return new UserDto(entity.get());
        }
    }


    /**
     * Deletes User
     *
     * @param userID
     */
    public Boolean deleteUserById(UUID userID) {

        if (!repository.existsById(userID.toString())) {
            return null;
        } else {
            repository.deleteById(userID.toString());
            return Boolean.TRUE;
        }
    }


}
