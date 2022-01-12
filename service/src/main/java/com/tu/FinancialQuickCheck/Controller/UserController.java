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
 * Presentation layer
 */

@CrossOrigin
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    public UserController(UserService userService){this.service = userService;}

    @GetMapping(produces = "application/json")
    public List<UserDto> findAllUser(){
        return service.getAllUsers();
    }

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

    //TODO: (prio: low) change path-var to request-body userDto & change path
    @GetMapping("email/{email}")
    public UserDto findByEmail(@PathVariable String email){
        UserDto tmp = service.findByEmail(email);

        if(tmp == null){
            throw new ResourceNotFound("User not found");
        }else{
            return tmp;
        }
    }

    //TODO: (prio: low) change path-var to request-body userDto & change path
    @PutMapping("email/{email}")
    public void updateUserByEmail(@RequestBody UserDto userDto, @PathVariable String email) {

        if (service.updateUserByEmail(userDto, email) == null){
            throw new ResourceNotFound("User not found");
        }
    }

    @DeleteMapping("/{userID}")
    public void deleteByUserId(@PathVariable UUID userID){

        if(service.deleteUserById(userID) == null){
            throw new ResourceNotFound("User not found");
        }
    }
}
