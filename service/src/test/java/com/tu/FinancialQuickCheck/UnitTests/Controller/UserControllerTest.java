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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    static Logger log = Logger.getLogger(UserControllerTest.class.getName());

    @Mock
    private UserService service;

    private UserController controller;

    private UserDto dto1;
    private List<UserDto> listDtos;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in UserControllerTest.class");

        controller = new UserController(service);

        dto1 = new UserDto();
        dto1.userEmail = "test@test.com";

        listDtos = new ArrayList<>();
    }

    @Test
    public void testFindAllUsers() {
        // Step 1: provide knowledge
        when(service.getAllUsers()).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<UserDto> out = controller.findAllUser();

        assertTrue(out.isEmpty());
    }

    @Test
    public void testCreateUser_badRequest() {
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
    public void testCreateUser_success() {
        // Step 1: provide knowledge
        when(service.createUser(dto1)).thenReturn(dto1);

        // Step 2: execute test method and assert
        UserDto out = controller.createUser(dto1);

        assertTrue(out.userEmail == dto1.userEmail);
    }

    @Test
    public void testFindByEmail_resourceNotFound() {
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
    public void testFindByEmail_resourceExists() {
        String email = "test@test.com";

        // Step 1: provide knowledge
        when(service.findByEmail(email)).thenReturn(dto1);

        // Step 2: execute test method and assert
        UserDto out = controller.findByEmail(email);

        assertTrue(out.userEmail == dto1.userEmail);
    }

    @Test
    public void testUpdateUser_resourceNotFound() {
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

    @Test
    public void testDeleteUser_resourceNotFound() {
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
    public void testDeleteUser_resourceExists() {
        UUID userID = UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429");
        // Step 1: provide knowledge
        when(service.deleteUserById(userID)).thenReturn(Boolean.TRUE);

        // Step 2: execute test method and assert
        controller.deleteByUserId(userID);
    }

}
