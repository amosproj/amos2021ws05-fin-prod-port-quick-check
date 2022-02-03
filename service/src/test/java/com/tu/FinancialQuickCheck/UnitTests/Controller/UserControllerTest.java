package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.UserController;
import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.UserService;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    static Logger log = Logger.getLogger(UserControllerTest.class.getName());

    @Mock
    private UserService service;

    private UserController controller;

    private UserDto dto1;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in UserControllerTest.class");

        controller = new UserController(service);

        dto1 = new UserDto();
        dto1.userEmail = "test@test.com";

        List<UserDto> listDtos = new ArrayList<>();
    }

    /**
     * tests for findAllUsers()
     *
     * testFindAllUsers1: no users exist --> return empty List<UserDto>
     */
    @Test
    public void test_findAllUsers_returnEmptyList() {
        List<UserDto> out = controller.findAllUser();

        assertTrue(out.isEmpty());
    }


    /**
     * tests for createUser()
     *
     * testCreateUser: input is missing information -> return BadRequest
     * testCreateUser: input contains necessary information -> return UserDto with created userID
     */
    @Test
    public void test_createUser_badRequest() {
        // Step 1: provide knowledge
        when(service.createUser(dto1)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.createUser(dto1));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_createUser_success() {
        // Step 1: provide knowledge
        when(service.createUser(dto1)).thenReturn(dto1);

        // Step 2: execute test method and assert
        UserDto out = controller.createUser(dto1);

        assertEquals(out.userEmail, dto1.userEmail);
    }


    /**
     * tests for findByEmail()
     *
     * testFindByEmail: email does not exist --> throw ResourceNotFound Exception
     * testFindByEmail: input correct --> return UserDto
     */
    @Test
    public void test_findByEmail_resourceNotFound() {
        String email = "test@test.com";

        // Step 1: provide knowledge
        when(service.findByEmail(email)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.findByEmail(email));

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_findByEmail_resourceExists() {
        String email = "test@test.com";

        // Step 1: provide knowledge
        when(service.findByEmail(email)).thenReturn(dto1);

        // Step 2: execute test method and assert
        UserDto out = controller.findByEmail(email);

        assertEquals(out.userEmail, dto1.userEmail);
    }


    /**
     * tests for updateUser()
     *
     * testUpdateUser: email does not exist -> throw ResourceNotFound
     */
    @Test
    public void test_updateUser_resourceNotFound() {
        String email = "test@test.com";

        // Step 1: provide knowledge
        when(service.updateUserByEmail(dto1, email)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.updateUserByEmail(dto1, email));

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * tests for deleteUserById()
     *
     * testDeleteUserById1: userId does not exist -> throw ResourceNotFound Exception
     * testDeleteUserById2: userId exists -> void
     */
    @Test
    public void test_deleteUser_resourceNotFound() {
        UUID userID = UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429");

        // Step 1: provide knowledge
        when(service.deleteUserById(userID)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.deleteByUserId(userID));

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_deleteUser_resourceExists() {
        UUID userID = UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429");

        // Step 1: provide knowledge
        when(service.deleteUserById(userID)).thenReturn(Boolean.TRUE);

        // Step 2: execute test method and assert
        controller.deleteByUserId(userID);
    }

}
