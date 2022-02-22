package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ListOfUserDto;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.springframework.stereotype.Service;

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

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    /**
     * Retrieves all existing users from db without passwords.
     *
     * @return A list of users, is empty if no users exists.
     */
    public List<UserDto> getAllUsers() {
        return new ListOfUserDto(this.repository.findAll()).users;
    }

    /**
     * Retrieves a user from db without password.
     *
     * @param email The email of the user used as a unique identifier for search
     * @return user if exists, else null
     */
    public UserDto findByEmail(String email) {
        Optional<UserEntity> entity = repository.findByEmail(email);

        return entity.map(UserDto::new).orElse(null);
    }

    /**
     * Creates and persists a user entity to db if userName, userEmail and password are provided and userEmail is valid.
     *
     * @param userDto The user object contains the necessary information.
     * @return The created user incl. unique identifier or null if input is missing/incorrect
     */
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

    /**
     * Updates user information in db.
     *
     * Attributes that can be updated: userEmail, userName, password
     *
     * @param userDto The user object contains the necessary information.
     * @param email The email of the user used as a unique identifier for search
     * @throws BadRequest if userEmail, userName, password are null or if new userEmail is not valid
     * @return userDto with updated information
     */
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
     * Removes user from db.
     *
     * @param userID The unique identifier of the user.
     * @return True if user was deleted
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
