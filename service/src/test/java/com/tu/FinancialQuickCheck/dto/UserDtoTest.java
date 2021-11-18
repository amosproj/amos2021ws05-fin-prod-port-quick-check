package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    @Test
    public void createUserTest()
    {
        // String testEmail = "test@test.de";
        String stringUUID = "123e4567-e89b-12d3-a456-426614174000";
        Role testRole = Role.ADMIN;
        UserDto user = new UserDto(stringUUID,testRole);
        assertEquals(stringUUID,user.email);
        assertEquals(testRole,user.role);
// assertEquals(UUID.fromString(stringUUID),user.id);
    }

}