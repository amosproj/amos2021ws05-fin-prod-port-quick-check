package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.UserEntity;
import com.tu.FinancialQuickCheck.db.UserRepository;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Underlying Service for User- aka. member-management.
 * Acts as connection between presentation Layer and persistence layer.
 */

@Service
public class UserService {

    private UserRepository userRepository;

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
        //TODO: we need to make sure that randomUUID produces unique values --> we could move this into the UserEntity
//        newUser.id = UUID.randomUUID();
        newUser.email = userDto.email;
//        newUser.password = userDto.password;
//        newUser.role = userDto.role;
        userRepository.save(newUser);
        UserDto newUserDto = new UserDto(newUser.id, newUser.email);
        return newUserDto;
    }

    /**
     * returns a List of all User
     */
    public List<UserDto> findAllUser(){

        List<UserDto> userList = new ArrayList<>();
        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys){
            UserDto userDto = new UserDto(userEntity.id, userEntity.email);
            //userDto.id = userEntity.id;
            //userDto.password = userEntity.password;
            userList.add(userDto);
        }
        return userList;
    }

    /**
     * Search for User by given ID
     * ID is UUID generated out of user-email
     * TODO: find faster way to accsess Users other than itterate over all of them
     *
     * @param  userID
     * @return userDto
     */
    public UserDto findById(UUID userID) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys) {
            if(userEntity.id == userID) {
                UserDto userDto = new UserDto(userEntity.email);
                return userDto;
            }
        }
        throw new ResourceNotFound("User ID " + userID + " not found");
    }

    /**
     * search for ID in repository and updates if found
     *
     * @param userDto
     * @param userID
     */
    public void updateById(UserDto userDto, UUID userID) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys) {
            if(userEntity.id == userID) {
                userEntity.email = userDto.email;
                userEntity.password = userDto.password;
                userRepository.save(userEntity);
                break;
            }
            throw new ResourceNotFound(" UserID " + userID + " not found");
        }
    }

    public void deleteUser(UUID userID) {

        Iterable<UserEntity> allUserEntitys = userRepository.findAll();

        for(UserEntity userEntity : allUserEntitys) {
            if(userEntity.id == userID) {
                userRepository.delete(userEntity);
                break;
            }
            throw new ResourceNotFound("User ID " + userID + " not found");
        }

    }

}
