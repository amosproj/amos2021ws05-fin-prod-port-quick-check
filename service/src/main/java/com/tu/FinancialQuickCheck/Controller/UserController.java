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

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("email/{email}")
    public UserDto findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @PutMapping("email/{email}")
    public void updateUserByEmail(@RequestBody UserDto userDto, @PathVariable String email){
        userService.updateByEmail(userDto, email);
    }

    @DeleteMapping("email/{email}")
    void deleteByEmail(@PathVariable String email){
        userService.deleteUser(email);
    }

}
