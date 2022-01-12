package com.tu.FinancialQuickCheck.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    static Logger log = Logger.getLogger(UserDtoTest.class.getName());

    List<String> valid_emails;
    List<String> invalid_emails;
    UserDto user1;
    UserDto user1_copy;
    UserDto user2;
    UserDto user2_copy;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectUserDto.class");

        // Define UserEntities
        user1 = new UserDto();
        user1.userEmail = "user1@email.com";
        user2 = new UserDto();
        user2.userEmail = "user2@email.com";

        user1_copy = new UserDto();
        user1_copy.userEmail = "user1@email.com";
        user2_copy = new UserDto();
        user2_copy.userEmail = "user2@email.com";
    }

    /**
     * tests for equals()
     *
     * testEqualsTrue: two objects are equal --> return True
     * testEqualsTrue: two objects are  NOT equal --> return False
     */
    @Test
    public void testEquals_true() {
        assertTrue(user1.equals(user1_copy));
        assertTrue(user2.equals(user2_copy));
    }

    @Test
    public void testEquals_false() {
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
    }

    /**
     * tests for hashCode()
     *
     * testHashCode: hashCodes of the same object are equal
     * testHashCode: hashCodes of the two different objects are NOT equal
     */
    @Test
    public void testHashCode_equal() {
        assertEquals(user1.hashCode(), user1_copy.hashCode());
        assertEquals(user2.hashCode(), user2_copy.hashCode());
    }

    @Test
    public void testHashCode_notEqual() {
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }


    /**
     * tests for validateEmail()
     *
     * testValidEmail: email is valid --> return True
     * testInvalidEmail: email is NOT valid --> return False
     */
    @Test
    public void testValidEmail_true() {

        valid_emails = new ArrayList();
        valid_emails.add("test@googlemail.com");
        valid_emails.add("test.test@gmail.de");
        valid_emails.add("test_test@gmail.de");
        valid_emails.add("test1234@gmail.de");
        valid_emails.add("123456789@gmail.de");

        for(String email: valid_emails){
            UserDto dto = new UserDto();
            assertTrue(dto.validateEmail(email));
        }
    }

    @Test
    public void testInvalidEmail_false() {

        invalid_emails = new ArrayList();
        invalid_emails.add("testgooglemail.com");
        invalid_emails.add("test.test@");
        invalid_emails.add("@gmail");
        invalid_emails.add(("Contrary to popular belief, Lorem Ipsum is not simply random text. " +
                "It has roots in a piece of classical Latin literature from 45 BC, making it " +
                "over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney " +
                "College in Virginia, looked up one of the more obscure Latin words, consectetur, " +
                "from a Lorem Ipsum passage, and going through the cites of the word in classical literature, " +
                "discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of" +
                "de Finibus Bonorum et Malorum (The Extremes of Good and Evil) by Cicero, written in 45 BC. " +
                "This book is a treatise on the theory of ethics, very popular during the Renaissance. " +
                "The first line of Lorem Ipsum,Lorem ipsum dolor sit amet..comes from a line in section 1.10.32.")
                .replace(" ", ""));

        for(String email: invalid_emails){
            ProjectUserDto dto = new ProjectUserDto();
            assertFalse(dto.validateEmail(email));
        }
    }
}