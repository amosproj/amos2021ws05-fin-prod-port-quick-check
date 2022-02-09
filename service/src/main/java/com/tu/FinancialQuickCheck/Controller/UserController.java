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
     * This method can return all users.
     *
     * @return A list of all users.
     */
    @GetMapping(produces = "application/json")
    public List<UserDto> findAllUser(){
        return service.getAllUsers();
    }

    /**
     * This method can create users.
     *
     * @param userDto The user data transfer object.
     * @throws BadRequest When the user cannot be created due to missing or incorrect information.
     * @return The created user entity in database.
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
     * This method is finding users by their email.
     *
     * @param email The email of the user who can be found.
     * @return The user who had to be found.
     */
    //TODO: (prio: low) change path-var to request-body userDto & change path
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
     * This method is updating user information by their email.
     *
     * @param userDto The user data transfer object.
     * @throws BadRequest When the user cannot be updated due to missing or incorrect information.
     * @param email The email of the user for which information can be updated.
     */
    //TODO: (prio: low) change path-var to request-body userDto & change path
    @PutMapping("/email/{email}")
    public void updateUserByEmail(@RequestBody UserDto userDto, @PathVariable String email) {

        if (service.updateUserByEmail(userDto, email) == null){
            throw new ResourceNotFound("User not found");
        }
    }

    /**
     * This method is deleting users by their ID.
     *
     * @param userID The ID of the user who can be deleted.
     */
    @DeleteMapping("/{userID}")
    public void deleteByUserId(@PathVariable UUID userID){

        if(service.deleteUserById(userID) == null){
            throw new ResourceNotFound("User not found");
        }
    }
}
