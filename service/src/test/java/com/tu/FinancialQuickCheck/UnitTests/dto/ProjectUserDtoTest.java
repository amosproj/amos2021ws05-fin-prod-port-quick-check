package com.tu.FinancialQuickCheck.UnitTests.dto;

import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectUserDtoTest {

    static Logger log = Logger.getLogger(ProjectUserDtoTest.class.getName());

    List<String> valid_emails;
    List<String> invalid_emails;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectUserDto.class");

        // Define Email Addresses
        valid_emails = new ArrayList();
        invalid_emails = new ArrayList();

        valid_emails.add("test@googlemail.com");
        valid_emails.add("test.test@gmail.de");
        valid_emails.add("test_test@gmail.de");
        valid_emails.add("test1234@gmail.de");
        valid_emails.add("123456789@gmail.de");

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

    }

    /**
     * tests for validateEmail()
     *
     * testValidEmail: email is valid --> return True
     * testInvalidEmail: email is NOT valid --> return False
     */
    @Test
    public void testValidEmail_true() {

        for(String email: valid_emails){
            ProjectUserDto dto = new ProjectUserDto();
            assertTrue(dto.validateEmail(email));
        }
    }

    @Test
    public void testInvalidEmail_false() {

        for(String email: invalid_emails){
            ProjectUserDto dto = new ProjectUserDto();
            assertFalse(dto.validateEmail(email));
        }
    }
}