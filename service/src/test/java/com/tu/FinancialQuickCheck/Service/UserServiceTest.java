package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.UserEntity;
import com.tu.FinancialQuickCheck.db.UserRepository;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


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
    private String keineEmail;
    private String pw1;
    private String pw2;

    private UserDto dto1;
    private UserDto dto2;
    private UserDto dto3;
    private UserDto emptyDto;

    private UserEntity entity1;
    private UserEntity entity2;
    private List<UserEntity> entities;



    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in RatingServiceTest.class");

        service = new UserService(repository);

        username1 = "Max Mustermann";
        username2 = "Nina Nichtmustermann";

        email1 = "max@mustermann.com";
        email2 = "NineNichtmustermann@gmail.de";
        keineEmail = "test";
        pw1 = "advnkjbgdvj";
        pw2 = "nvkjavdfihvs";

        dto1 = new UserDto();
        dto1.username = username1;
        dto1.email = email1;
        dto1.password = pw1;

        dto2 = new UserDto();
        dto2.username = username2;
        dto2.email = email2;
        dto2.password = pw2;

        dto3 = new UserDto();
        dto3.username = username1;
        dto3.email = keineEmail;
        dto3.password = pw1;

        emptyDto = new UserDto();

        entity1 = new UserEntity();
        entity1.username = username1;
        entity1.password = pw1;
        entity1.email = email1;

        entity2 = new UserEntity();
        entity2.username = username2;
        entity2.password = pw2;
        entity2.email = email2;

        entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);
    }
    
    
    /**
     * tests for createUser()
     *
     * testCreateUser1: input contains necessary information -> return UserDto with created userID
     * testCreateUser2: input is missing information -> throw BadRequest Exception
     * testCreateUser3: input attributes do not comply with requirements -> throw BadRequest Exception
     */
    @Test
    public void testCreateUser1() {
        for(int i = 1; i <= 11; i++){
            // Step 1: init test object
            dto1.id = UUID.randomUUID();

            // Step 2: execute createUser
            log.info("@Test createUser() - test object : " + dto1.username);
            UserDto out = service.createUser(dto1);
            log.info("@Test createUser() - return object id : " + out.id.toString());

            // Step 3: assert result
            assertAll("create User",
                    () -> assertEquals(dto1.username, out.username),
                    () -> assertEquals(dto1.email, out.email),
                    () -> assertNotEquals(dto1.password, out.password),
                    () -> assertNotEquals(dto1.id, out.id)
            );
        }
    }

    @Test
    public void testCreateUser2a() {
        // Step 1: init test object
        dto1.password = null;
        dto2.username = null;
        dto3.email = null;


        // Step 2 and 3: execute createUser and assert result
        assertNull(service.createUser(dto1));
        assertNull(service.createUser(dto2));
        assertNull(service.createUser(dto3));
        assertNull(service.createUser(emptyDto));

    }

    @Test
    public void testCreateUser2b() {
        // Step 1: init test object
        dto1.password = null;
        dto1.username = null;

        dto2.email = null;
        dto2.username = null;

        dto3.email = null;
        dto3.password = null;

        // Step 2 and 3: execute createUser and assert result
        assertNull(service.createUser(dto1));
        assertNull(service.createUser(dto2));
        assertNull(service.createUser(dto3));
    }

    @Test
    public void testCreateUser3a() {
        // TODO: define requirements for input attribute pw, e.g. pw length
        // Test: attribute password

        // Test: attribute email
        assertNull(service.createUser(dto3));

    }


    /**
     * tests for updateByUserID()
     *
     * testUpdateByUserID1: userId does not exist -> throw ResourceNotFound Exception
     * testUpdateByUserID2: userId exists, misses attributes to update -> throw BadRequest Exception
     * testUpdateByUserID3: userID exists, attributes do not comply with requirements -> throw BadRequest Exception
     * testUpdateByUserID4: userID exists, input correct -> return updated UserDto
     */
    @Test
    @Disabled
    public void testUpdateUser1() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }

    @Test
    @Disabled
    public void testUpdateUser2() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }

    @Test
    @Disabled
    public void testUpdateUser3() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }

    @Test
    @Disabled
    public void testUpdateUser4() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }

    /**
     * tests for findByID()
     *
     * testFindByID1: userId does not exist -> throw ResourceNotFound Exception
     * testFindByID2: userId exists, but not a UUID -> throw BadRequest Exception
     * testFindByID3: userID exists -> return UserDto for userID
     */
    @Test
    @Disabled
    public void testFindByID1() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }

    @Test
    @Disabled
    public void testFindByID2() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }

    @Test
    @Disabled
    public void testFindByID3() {
        // Step 1: init test object


        // Step 2: provide knowledge


        // Step 3: execute getAllRatings()

    }
}
