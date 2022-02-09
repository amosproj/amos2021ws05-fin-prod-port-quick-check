package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Service.UserService;
import com.tu.FinancialQuickCheck.db.UserEntity;
import com.tu.FinancialQuickCheck.db.UserRepository;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    static Logger log = Logger.getLogger(UserServiceTest.class.getName());

    @Mock
    UserRepository repository;

    private UserService service;

    private String username1;
    private String username2;
    private String email1;
    private String email2;
    private String email404;
    private String keineEmail;
    private String pw1;
    private String pw2;
    private UUID userID1;
    private UUID userID2;

    private UserDto dto1;
    private UserDto dto2;
    private UserDto dto3;
    private UserDto emptyDto;

    private UserEntity entity1;
    private UserEntity entity2;
    private List<UserEntity> entities;



    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in UserServiceTest.class");

        service = new UserService(repository);

        username1 = "Max Mustermann";
        username2 = "Nina Nichtmustermann";

        email1 = "max@mustermann.com";
        email2 = "NinaNichtmustermann@gmail.de";
        email404 = "user404@mail.com";
        keineEmail = "test";
        pw1 = "advnkjbgdvj";
        pw2 = "nvkjavdfihvs";

        userID1 = UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429");
        userID2 = UUID.fromString("4710db7c-e875-4e63-9e03-7f6ad85cc428");

        dto1 = new UserDto();
        dto1.userName = username1;
        dto1.userEmail = email1;
        dto1.password = pw1;

        dto2 = new UserDto();
        dto2.userName = username2;
        dto2.userEmail = email2;
        dto2.password = pw2;

        dto3 = new UserDto();
        dto3.userName = username1;
        dto3.userEmail = keineEmail;
        dto3.password = pw1;

        emptyDto = new UserDto();

        entity1 = new UserEntity();
        entity1.id = userID1.toString();
        entity1.username = username1;
        entity1.password = pw1;
        entity1.email = email1;

        entity2 = new UserEntity();
        entity2.id = userID2.toString();
        entity2.username = username2;
        entity2.password = pw2;
        entity2.email = email2;

        entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);
    }

    /**
     * tests for getAllUsers()
     *
     * testGetAllUsers1: no users exist --> return empty List<UserDto>
     * testGetAllUsers2: users exist --> return List<UserDto>
     */
    @Test
    public void testGetAllUsers1_noUsersExist() {
        // Step 1: init test object
        List<UserEntity> userEntities = Collections.EMPTY_LIST;

        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(userEntities);

        // Step 3: execute getProjectById()
        List<UserDto> out = service.getAllUsers();
        List<UserDto> expected = new ArrayList<>();

        assertEquals(expected, out);
    }

    @Test
    public void testGetAllUsers2_UsersExist() {
        // Step 1: provide knowledge
        when(repository.findAll()).thenReturn(entities);

        // Step 2: execute getAllUsers()
        List<UserDto> out = service.getAllUsers();

        out.forEach(
                user -> assertAll("get Users",
                        () -> assertNotNull(user.userName),
                        () -> assertNotNull(user.userEmail),
                        () -> assertNotNull(user.userID),
                        () -> assertNull(user.password)
                )
        );

        assertThat(out.size()).isEqualTo(2);
    }


    /**
     * tests for findByEmail()
     *
     * testFindByEmail1: incorrect input --> return null
     * testFindByEmail2: email does not exist --> throw ResourceNotFound Exception
     * testFindByEmail3: input correct --> return UserDto
     */
    @Test
    public void testFindByEmail1_incorrectInput() {
        assertNull(service.findByEmail(keineEmail));
    }

    @Test
    public void testFindByEmail2_emailNotFound() {
        assertNull(service.findByEmail(email1));
    }

    @Test
    public void testFindByEmail3_emailFound() {

        // Step 1: provide knowledge
        when(repository.findByEmail(email1)).thenReturn(Optional.of(entity1));

        // Step 2: execute updateByUserID()
        UserDto out = service.findByEmail(email1);

        assertAll("get User",
                () -> assertEquals(dto1.userName, out.userName),
                () -> assertEquals(dto1.userEmail, out.userEmail),
                () -> assertNotNull(out.userID),
                () -> assertNull(out.password));

    }

    
    /**
     * tests for createUser()
     *
     * testCreateUser1: input contains necessary information -> return UserDto with created userID
     * testCreateUser2: input is missing information -> return null
     * testCreateUser3: input attributes do not comply with requirements -> return null
     */
    @Test
    public void testCreateUser1_succesfulCreation() {
        for(int i = 1; i <= 11; i++){
            // Step 1: init test object
            dto1.userID = UUID.randomUUID();

            // Step 2: execute createUser
            log.info("@Test createUser() - test object : " + dto1.userName);
            UserDto out = service.createUser(dto1);
            log.info("@Test createUser() - return object id : " + out.userID.toString());

            // Step 3: assert result
            assertAll("create User",
                    () -> assertEquals(dto1.userName, out.userName),
                    () -> assertEquals(dto1.userEmail, out.userEmail),
                    () -> assertNotEquals(dto1.password, out.password),
                    () -> assertNotEquals(dto1.userID, out.userID)
            );
        }
    }

    @Test
    public void testCreateUser2a_missingOneAttribute() {
        // Step 1: init test object
        dto1.password = null;
        dto2.userName = null;
        dto3.userEmail = null;


        // Step 2 and 3: execute createUser and assert result
        assertNull(service.createUser(dto1));
        assertNull(service.createUser(dto2));
        assertNull(service.createUser(dto3));
        assertNull(service.createUser(emptyDto));

    }

    @Test
    public void testCreateUser2b_missingTwoAttributes() {
        // Step 1: init test object
        dto1.password = null;
        dto1.userName = null;

        dto2.userEmail = null;
        dto2.userName = null;

        dto3.userEmail = null;
        dto3.password = null;

        // Step 2 and 3: execute createUser and assert result
        assertNull(service.createUser(dto1));
        assertNull(service.createUser(dto2));
        assertNull(service.createUser(dto3));
    }

    @Test
    public void testCreateUser2c_missingAllAttributes() {
        // Step 2 and 3: execute createUser and assert result
        assertNull(service.createUser(emptyDto));
    }

    @Test
    public void testCreateUser3a_invalidEmail() {
        assertNull(service.createUser(dto3));
    }

    @Test
    @Disabled("implement after requirements for pw are clear")
    public void testCreateUser3b_invalidPassword() {
        // TODO: define requirements for input attribute pw, e.g. pw length
        // Test: attribute password
    }


    /**
     * tests for updateByByEmail()
     *
     * testUpdateByUserID1: userId does not exist -> throw ResourceNotFound Exception
     * testUpdateByUserID2: userId exists, misses attributes to update -> return null
     * testUpdateByUserID3: userID exists, attributes do not comply with requirements -> return  null
     * testUpdateByUserID4: userID exists, input correct -> return updated UserDto
     * testUpdateByUserID5: userID exists, partial update -> return updated UserDto
     *
     * UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429")
     */
    @Test
    public void testUpdateUser1_userMailNotFound() {
        // Step 1: provide knowledge
        when(repository.findByEmail(email404)).thenReturn(Optional.empty());

        // Step 2: execute and assert test method
        assertNull(service.updateUserByEmail(dto1, email404));
    }

    @Test
    public void testUpdateUser2_NothingToUpdate() {
        // execute updateByUserID() and assert result
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> service.updateUserByEmail(emptyDto, email1));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testUpdateUser4_fullValidUpdate() {
        // Step 2: provide knowledge
        when(repository.findByEmail(email1)).thenReturn(Optional.of(entity1));

        // Step 2 and 3: execute updateByUserID() and assert result
        UserDto out = service.updateUserByEmail(dto2, email1);

        assertAll("update User",
                () -> assertNull(out.password),
                () -> assertEquals(dto2.userName, out.userName),
                () -> assertEquals(dto2.userEmail, out.userEmail)
        );
    }

    @Test
    public void testUpdateUser5a_partialUpdate_email() {
        // Step 1: init test object
        dto2.userName = null;
        dto2.password = null;

        // Step 2: provide knowledge
        when(repository.findByEmail(email1)).thenReturn(Optional.of(entity1));

        // Step 3: execute updateByUserID() and assert result
        UserDto out = service.updateUserByEmail(dto2, email1);

        assertAll("update User",
                () -> assertNull(out.password),
                () -> assertEquals(entity1.username, out.userName),
                () -> assertEquals(dto2.userEmail, out.userEmail)
        );
    }

    @Test
    public void testUpdateUser5b_partialUpdate_username() {
        // Step 1: init test object
        dto2.userEmail = null;
        dto2.password = null;

        // Step 2: provide knowledge
        when(repository.findByEmail(email1)).thenReturn(Optional.of(entity1));

        // Step 3: execute updateByUserID() and assert result
        UserDto out = service.updateUserByEmail(dto2, email1);

        assertAll("update User",
                () -> assertNull(out.password),
                () -> assertEquals(dto2.userName, out.userName),
                () -> assertEquals(entity1.email, out.userEmail)
        );
    }

    @Test
    public void testUpdateUser5c_partialUpdate_password() {
        // Step 1: init test object
        dto2.userEmail = null;
        dto2.userName = null;

        // Step 2: provide knowledge
        when(repository.findByEmail(email1)).thenReturn(Optional.of(entity1));

        // Step 3: execute updateByUserID() and assert result
        UserDto out = service.updateUserByEmail(dto2, email1);

        assertAll("update User",
                () -> assertNull(out.password),
                () -> assertEquals(entity1.username, out.userName),
                () -> assertEquals(entity1.email, out.userEmail)
        );
    }

    @Test
    public void testUpdateUser5e_partialUpdate_usernameAndPassword() {
        // Step 1: init test object
        dto2.userEmail = null;

        // Step 2: provide knowledge
        when(repository.findByEmail(email1)).thenReturn(Optional.of(entity1));

        // Step 3: execute updateByUserID() and assert result
        UserDto out = service.updateUserByEmail(dto2, email1);

        assertAll("update User",
                () -> assertNull(out.password),
                () -> assertEquals(dto2.userName, out.userName),
                () -> assertEquals(entity1.email, out.userEmail)
        );
    }

    /**
     * tests for deleteUserById()
     *
     * testDeleteUserById1: userId does not exist -> throw ResourceNotFound Exception
     * testDeleteUserById2: userId exists -> void
     */
    @Test
    public void testDeleteUserById1_userIdNotFound()
    {
        // Step 1: provide knowledge
        when(repository.existsById(userID1.toString())).thenReturn(false);

        // Step 2: execute updateByUserID()
        assertNull(service.deleteUserById(UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429")));
    }

    @Test
    public void testDeleteUserById2_userIdExists()
    {
        // Step 1: provide knowledge
        when(repository.existsById(userID1.toString())).thenReturn(true);

        // Step 2: execute updateByUserID()
        Boolean out = service.deleteUserById(UUID.fromString("5710db7c-e875-4e63-9e03-7f6ad85cc429"));

        assertTrue(out);
    }
}
