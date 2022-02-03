package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.UserService;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The UserController manages and processes requests for creating, updating or finding users
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Constructor for class UserController.
     *
     * @param userService The different services for the user.
     */
    public UserController(UserService userService){this.service = userService;}

    /**
     * Retrieves all existing users from db.
     *
     * @return A list of users, is empty if no users exists.
     */
    @GetMapping(produces = "application/json")
    public List<UserDto> findAllUser(){
        return service.getAllUsers();
    }

    /**
     * Creates and persists a user entity to db.
     *
     * @param userDto The user object contains the necessary information.
     * @throws BadRequest When userName, userEmail and password of user are missing or userEmail is not valid.
     * @return The created user incl. unique identifier.
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        UserDto tmp = service.createUser(userDto);

        if (tmp == null) {
            throw new BadRequest("Input is missing/incorrect");
        }else {
            return tmp;
        }
    }

    /**
     * Retrieves a user from db.
     *
     * @param email The email of the user.
     * @throws ResourceNotFound if user does not exist
     * @return user
     */
    @GetMapping("/email/{email}")
    public UserDto findByEmail(@PathVariable String email){
        UserDto tmp = service.findByEmail(email);

        if(tmp == null){
            throw new ResourceNotFound("User not found");
        }else{
            return tmp;
        }
    }

    /**
     * Updates user information in db.
     *
     * Attributes that can be updated: userEmail, userName, password
     *
     * @param userDto The user object contains the necessary information.
     * @param email The email of the user used as a unique identifier for search
     * @throws ResourceNotFound if user does not exist
     */
    @PutMapping("/email/{email}")
    public void updateUserByEmail(@RequestBody UserDto userDto, @PathVariable String email) {

        if (service.updateUserByEmail(userDto, email) == null){
            throw new ResourceNotFound("User not found");
        }
    }

    /**
     * Removes user from db.
     *
     * @param userID The unique identifier of the user.
     * @throws ResourceNotFound if user does not exist in db.
     */
    @DeleteMapping("/{userID}")
    public void deleteByUserId(@PathVariable UUID userID){

        if(service.deleteUserById(userID) == null){
            throw new ResourceNotFound("User not found");
        }
    }
}
