package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    @Test
    public void createUserTest()
    {
        String testEmail = "test@test.de";
        Role testRole = Role.ADMIN;
        UserDto user = new UserDto(testEmail,testRole);
        assertEquals(testEmail,user.email);
        assertEquals(testRole,user.role);
        assertEquals(UUID.fromString(testEmail),user.id);
    }

}