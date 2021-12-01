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

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    public UserController(UserService userService){this.service = userService;}

    @GetMapping(produces = "application/json")
    public List<UserDto> findAllUser(){
        List<UserDto> tmp = service.findAllUser();
        if(tmp.isEmpty()){
            throw new ResourceNotFound("No users exist.");
        }else{
            return tmp;
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        UserDto tmp = service.createUser(userDto);

        if (tmp == null) {
            throw new BadRequest("User cannot be created due to missing/incoorect information.");
        }else {
            return tmp;
        }
    }

    @GetMapping("email/{email}")
    public UserDto findByEmail(@PathVariable String email){
        return service.findByEmail(email);
    }


    @PutMapping("/{userID}")
    public void updateUserByUserID(@RequestBody UserDto userDto, @PathVariable UUID userID){

        if (service.updateByUserID(userDto, userID) == null) {
            throw new BadRequest("User cannot be updated due to missing/incorrect information.");
        }
    }


    @DeleteMapping("/{userID}")
    void deleteByUserId(@PathVariable UUID userID){
        service.deleteUserById(userID);
    }

}
