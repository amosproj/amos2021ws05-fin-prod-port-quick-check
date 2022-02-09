package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.db.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.*;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    static Logger log = Logger.getLogger(UserControllerIntegrationTest.class.getName());

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();


    private String host =  "http://localhost:";
    private String users = "/users";
    private String email = "/email/";

    private HttpHeaders header = new HttpHeaders();
    private String jsonStringCorrect = "{\"userName\":\"testUser\", \"userEmail\":\"testUser@mail.com\", \"password\":\"4321\"}";
    private String jsonStringEmpty = "{}";
    private String jsonStringInvalidEmail = "{\"userName\":\"testUser\", \"userEmail\":\"testUsermail.com\", \"password\":\"4321\"}";
    private String jsonStringMissingPW = "{\"userName\":\"testUser\", \"userEmail\":\"testUser@mail.com\"}";
    private String jsonStringMissingEmail = "{\"userName\":\"testUser\", \"password\":\"4321\"}";
    private String jsonStringMissingUsername = "{\"userEmail\":\"testUser@mail.com\", \"password\":\"4321\"}";
    private String jsonStringUserDoesNotExist = "{\"userName\":\"user404\", \"userEmail\":\"user404@mail.com\", \"password\":\"abcdefg\"}";

    private List<UserEntity> entities;

    @BeforeEach
    public void initEach(){
        log.info("@BeforeEach - setup for Tests in UserControllerIntegrationTest.class");

        entities = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            UserEntity user = new UserEntity();
            user.id = UUID.randomUUID().toString();
            user.username = "username " + i;
            user.email = "username" + i  + "@test.com";
            user.password = "initialPW";
            repository.save(user);
            entities.add(user);
        }

        header.setContentType(MediaType.APPLICATION_JSON);
    }

    @AfterEach
    public void reset(){
        repository.deleteAll();

        log.info("@AfterEach - db reset");
    }

    public void assertResponseBody(ResponseEntity<String> response, String userEmail, String userName){
        String[] bodyStringList = Objects.requireNonNull(response.getBody()).split("},");

        for(int i = 0; i < bodyStringList.length; i++){
            bodyStringList[i] = bodyStringList[i]
                    .replace("[{", "")
                    .replace("{", "")
                    .replace("}]", "")
                    .replace("}", "")
                    .replace("\"", "")
                    .replace("userID:", "")
                    .replace("userEmail:", "")
                    .replace("userName:", "")
                    .replace("password:", "");
            String[] object = Objects.requireNonNull(bodyStringList[i]).split(",");
            Optional<UserEntity> user = repository.findById(object[0]);
            assertFalse(user.isEmpty());
            assertTrue(object[1].contains(userEmail));
            assertTrue(object[2].contains(userName));
            assertEquals("null", object[3]);
        }
    }

    public void assertUserEntity(UserEntity userBefore, String currentEmail, Boolean checkUsername, Boolean checkEmail,
                                 Boolean checkPW, String testNumber,String logMessage){
        log.info("@Test " + testNumber + logMessage);
        log.info("@Test " + testNumber + " userBefore: " + userBefore.username + ", " + userBefore.email + ", " + userBefore.password);
        Optional<UserEntity> userAfter = repository.findByEmail(currentEmail);

        if(userAfter.isPresent()){
            log.info("@Test " + testNumber + " userAfter: " + userAfter.get().username + ", " + userAfter.get().email + ", " + userAfter.get().password);
            if(checkUsername){assertNotEquals(userBefore.username, userAfter.get().username);}
            if(checkEmail){assertNotEquals(userBefore.email, userAfter.get().email);}
            if(checkPW){assertNotEquals(userBefore.password, userAfter.get().password);}
        }else{
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    public void test1_findAllUser_success_returnNonEmptyString(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.GET, new HttpEntity<>(null, header), String.class);


        log.info("@Test 1 - findAllUsers_success");
        log.info("@Test 1 - Response Body: " + response.getBody());
        assertResponseBody(response, "@test.com", "username");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test2_findAllUser_success_returnEmptyString(){
        repository.deleteAll();

        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.GET, new HttpEntity<>(null, header), String.class);

        log.info("@Test 2 - findAllUsers_success - return empty string");
        log.info("@Test 2 - Response Body: " + response.getBody());
        assertEquals("[]", response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test3_createUser_success(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.POST, new HttpEntity<>(jsonStringCorrect, header), String.class);

        assertResponseBody(response, "testUser@mail.com", "testUser");

        log.info("@Test 3 - createUser_success - success");
        log.info("@Test 3 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void test4_createUser_badRequest_InvalidEmail(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.POST, new HttpEntity<>(jsonStringInvalidEmail, header), String.class);

        log.info("@Test 4 - createUser_badRequest - invalid email");
        log.info("@Test 4 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test5_createUser_badRequest_MissingPW(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.POST, new HttpEntity<>(jsonStringMissingPW, header), String.class);

        log.info("@Test 5 - createUser_badRequest - pw missing");
        log.info("@Test 5 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test6_createUser_badRequest_MissingEmail(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.POST, new HttpEntity<>(jsonStringMissingEmail, header), String.class);

        log.info("@Test 6 - createUser_badRequest - email missing");
        log.info("@Test 6 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test7_createUser_badRequest_MissingUsername(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users,
                HttpMethod.POST, new HttpEntity<>(jsonStringMissingUsername, header), String.class);

        log.info("@Test 7 - createUser_badRequest - userName missing");
        log.info("@Test 7 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test8_findUserByEmail_success(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + email + entities.get(0).email,
                HttpMethod.GET, new HttpEntity<>(null, header), String.class);

        assertResponseBody(response, "@test.com", "username");

        log.info("@Test 8 - findUserByEmail - success");
        log.info("@Test 8 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test9_findUserByEmail_resourceNotFound_userEmail(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + email + "/testUser404@mail.com",
                HttpMethod.GET, new HttpEntity<>(null, header), String.class);

        log.info("@Test 9 - findUserByEmail_resourceNotFound - user does not exist in db");
        log.info("@Test 9 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test10_updateUserByEmail_success_fullUpdate(){
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + email + entities.get(0).email,
                HttpMethod.PUT, new HttpEntity<>(jsonStringCorrect, header), String.class);

        String logMessage = " - updateUserByEmail_success - update of all values";
        assertUserEntity(entities.get(0), "testUser@mail.com", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
                "10", logMessage);

        log.info("@Test 10 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test11_updateUserByEmail_success_partialUpdate_MissingEmail(){
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + email + entities.get(0).email,
                HttpMethod.PUT, new HttpEntity<>(jsonStringMissingEmail, header), String.class);

        String logMessage = " - updateUserByEmail_success - partial update of userName and password";
        assertUserEntity(entities.get(0), entities.get(0).email, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE,
                "11", logMessage);

        log.info("@Test 11 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test12_updateUserByEmail_success_partialUpdate_MissingEmail(){
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + email + entities.get(0).email,
                HttpMethod.PUT, new HttpEntity<>(jsonStringMissingUsername, header), String.class);

        String logMessage = " - updateUserByEmail_success - partial update of email and password";
        assertUserEntity(entities.get(0), "testUser@mail.com", Boolean.FALSE, Boolean.TRUE, Boolean.TRUE,
                "12", logMessage);

        log.info("@Test 12 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test13_updateUserByEmail_success_partialUpdate_MissingPassword(){
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + email + entities.get(0).email,
                HttpMethod.PUT, new HttpEntity<>(jsonStringMissingPW, header), String.class);

        String logMessage = " - updateUserByEmail_success - partial update of email and username";
        assertUserEntity(entities.get(0), "testUser@mail.com", Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
                "13", logMessage);

        log.info("@Test 13 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test14_updateUserByEmail_badRequest_emptyJSON(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + email + entities.get(0).email,
                HttpMethod.PUT, new HttpEntity<>(jsonStringEmpty, header), String.class);

        log.info("@Test 14 - updateUserByEmail_badRequest - json string empty");
        log.info("@Test 14 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test15_updateUserByEmail_badRequest_invalidEmail(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + email + "testUsermail.com",
                HttpMethod.PUT, new HttpEntity<>(jsonStringInvalidEmail, header), String.class);

        log.info("@Test 15 - updateUserByEmail_badRequest - invalid email");
        log.info("@Test 15 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test16_updateUserByEmail_resourceNotFound_userEmail(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + email + "testUser@mail.com",
                HttpMethod.PUT, new HttpEntity<>(jsonStringCorrect, header), String.class);

        log.info("@Test 16 - updateUserByEmail_resourceNotFound - user does not exist in db");
        log.info("@Test 16 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test17_deleteByUserId_resourceNotFound(){
        String userID = "/" + UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + userID,
                HttpMethod.DELETE, new HttpEntity<>(null, header), String.class);

        log.info("@Test 17 - deleteByUserId_resourceNotFound - user does not exist in db");
        log.info("@Test 17 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void test18_deleteByUserId_badRequest(){
        String userID = "/" + "123456";
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + userID,
                HttpMethod.DELETE, new HttpEntity<>(null, header), String.class);

        log.info("@Test 18 - deleteByUserId_badRequest - userID is not a UUID");
        log.info("@Test 18 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void test19_deleteByUserId_success(){
        String userID = "/" + entities.get(0).id;
        ResponseEntity<String> response = restTemplate.exchange(host + port + users + userID,
                HttpMethod.DELETE, new HttpEntity<>(null, header), String.class);

        log.info("@Test 19 - deleteByUserId_success - user was succesfully deleted");
        log.info("@Test 19 - Response Body: " + response.getBody());
        Optional<UserEntity> user = repository.findById(userID);
        assertEquals(Optional.empty(), user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}