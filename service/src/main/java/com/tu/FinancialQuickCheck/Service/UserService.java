package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.UserEntity;
import com.tu.FinancialQuickCheck.db.UserRepository;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Underlying Service for User- aka. member-management.
 * Acts as connection between presentation Layer and persistence layer.
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * create new User and saves in (user)repository
     *
     * @param  userDto
     * @return UserDto
     */
    public UserDto createUser(UserDto userDto) {

        UserEntity newUser = new UserEntity();

        newUser.email = userDto.email;
        newUser.password = userDto.password;
        userRepository.save(newUser);
        return new UserDto(newUser.id, newUser.email);
    }

    /**
     * returns a List of all User
     */
    public List<UserDto> findAllUser(){

        List<UserDto> userList = new ArrayList<>();
        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys){
            UserDto userDto = new UserDto(userEntity.id, userEntity.email);
            userDto.password = userEntity.password;
            userList.add(userDto);
        }
        return userList;
    }

    /**
     * Search for User by given ID
     * ID is UUID generated out of user-email
     * TODO: find faster way to access Users other than iterate over all of them
     *
     * @param  email
     * @return userDto
     */
    public UserDto findByEmail(String email) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys) {
            if(userEntity.email.equals(email)) {
                UserDto userDto = new UserDto(userEntity.email);
                userDto.id = userEntity.id;
                return userDto;
            }
        }
        throw new ResourceNotFound("User Email " + email + " not found");
    }

    /**
     * search for ID in repository and updates if found
     *
     * @param userDto
     * @param email
     */
    public void updateByEmail(UserDto userDto, String email) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys) {
            if(userEntity.email.equals(email)) {
                if (userDto.id != null){ userEntity.id = userDto.id;}
                if (userDto.password != null){ userEntity.password = userDto.password;}
                userRepository.save(userEntity);
                return;
            }
        }
        throw new ResourceNotFound(" User Email |" + email + "| not found");
    }

    /**
     * Deletes User
     *
     * @param email
     */

    public void deleteUser(String email) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys) {
            if(userEntity.email.equals(email)) {
                Optional<UserEntity> tmpEnt = userRepository.findById(userEntity.id);
                if(tmpEnt.isEmpty()){
                    throw new ResourceNotFound("User email |" + email + "| not found");
                }else {
                    userRepository.deleteById(tmpEnt.get().id);
                }
                return;
            }
        }
    }

}
