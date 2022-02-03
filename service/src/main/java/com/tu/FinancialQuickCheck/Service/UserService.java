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
     * Returns a List of all Users without passwords.
     */
    public List<UserDto> getAllUsers() {
        return new ListOfUserDto(this.repository.findAll()).users;
    }

    /**
     * Search for User by given email.
     *
     * @param email The email of the user which should be found.
     * @throws ResourceNotFound When the user is not found.
     * @throws BadRequest When the input is incorrect or missing.
     * @return userDto The user data transfer object.
     */
    public UserDto findByEmail(String email) {
        Optional<UserEntity> entity = repository.findByEmail(email);

        return entity.map(UserDto::new).orElse(null);
    }

    /**
     * Create new User and saves in (user)repository.
     *
     * @param userDto The user data transfer object.
     * @return UserDto The user data transfer object.
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
     * This method is updating a user by its email.
     *
     * @param userDto The users data transfer object.
     * @param email The email of the user which should be updated.
     * @throws ResourceNotFound When the user cannot be find.
     * @return The updated user data transfer object.
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
     * Deletes user.
     *
     * @param userID The ID of the user which should be deleted by its ID.
     * @throws ResourceNotFound When the user ID cannot be found.
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
