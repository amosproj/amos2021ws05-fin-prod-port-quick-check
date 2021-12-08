package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
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
     * @param email
     * @return userDto
     */
    public UserDto findByEmail(String email) {

        if(validateEmail(email)){
            Optional<UserEntity> entity = repository.findByEmail(email);

            if(entity.isPresent()){
                return new UserDto(UUID.fromString(entity.get().id), entity.get().email, entity.get().username);

            }else{
                throw new ResourceNotFound("User Email " + email + " not found");
            }

        }else{
            throw new BadRequest("Incorrect Input");
        }
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

        if (userDto.userName != null && userDto.userEmail != null && userDto.password != null
                && validateEmail(userDto.userEmail) && !repository.existsById(userDto.userEmail)) {
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
     * search for ID in repository and updates if found
     *
     * @param userDto
     * @param userID
     */
    /**public UserDto updateByUserID(UserDto userDto, UUID userID) {

        Optional<UserEntity> entity = repository.findById(userID.toString());

        if (entity.isEmpty()) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else if ((userDto.userEmail == null && userDto.userName == null
                && userDto.password == null) || (userDto.userEmail != null && !validateEmail(userDto.userEmail))){
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

            return new UserDto(UUID.fromString(entity.get().id), entity.get().email, entity.get().username);

        }
    }**/

    public UserDto updateUserByEmail(UserDto userDto, String email) {

        Optional<UserEntity> entity = repository.findById(email);

        if (entity.isEmpty()) {
            throw new ResourceNotFound("user email: " + email + " not found");
        } else if ((userDto.userEmail == null && userDto.userName == null
                && userDto.password == null) || (userDto.userEmail != null && !validateEmail(userDto.userEmail))){
            return null;
        } else {
            entity.map(
                    user -> {
                        if (userDto.password != null) {
                            user.password = userDto.password;
                        }

                        if (userDto.userName != null) {
                            user.username = userDto.userName;
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
    public void deleteUserById(UUID userID) {

        if (!repository.existsById(userID.toString())) {
            throw new ResourceNotFound("userID " + userID + " not found");
        } else {
            repository.deleteById(userID.toString());
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
