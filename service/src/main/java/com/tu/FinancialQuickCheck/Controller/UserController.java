package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.UserService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
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
    private UserService userService;

    @GetMapping(produces = "application/json")
    public List<UserDto> findAllUser(){
        return userService.findAllUser();
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{email}")
    public UserDto findById(@PathVariable String email){
        return userService.findById(UUID.fromString(email));
    }

    @PutMapping("/{email}")
    public void updateUserById(@RequestBody UserDto userDto, @PathVariable String email){
        userService.updateById(userDto, UUID.fromString(email));
    }

    @DeleteMapping("/{email}")
    void deleteById(@PathVariable String email){
        userService.deleteUser(UUID.fromString(email));
    }

}
