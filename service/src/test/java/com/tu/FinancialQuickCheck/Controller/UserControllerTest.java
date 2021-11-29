package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.dto.UserDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest{

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();


    private String host =  "http://localhost:";
    private String users = "/users";

    private String preUserBody =  "{\"username\":\"preUser\", \"email\":\"preUser@mail.com\", \"password\":\"1234\"}";
    private String testUserBody = "{\"username\":\"testUser\", \"email\":\"testUser@mail.com\", \"password\":\"4321\"}";
    private String nonExistentUser = "{\"username\":\"preUser\", \"email\":\"preUser@mail.com\", \"password\":\"abcdefg\"}";



    //creates a user for testing (preUser)
    @BeforeEach
    public void initEach(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(preUserBody, headers);

        String responseBody = restTemplate.exchange(
                host + port + users,
                HttpMethod.POST,
                request,
                String.class).getBody();

        System.out.println("Init: " + responseBody);

    }


    //delete preUser
    @AfterEach
    public void cleanup(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(preUserBody, headers);

        String tmp = restTemplate.exchange(
                host + port + users,
                HttpMethod.DELETE,
                request,
                String.class).getBody();
    }

    @Test
    //create new user
    public void postNewUser(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(testUserBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    //find existing user
    public void findUserByEmail() throws JSONException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + "/email" + "/preUser@mail.com",
                HttpMethod.GET,
                request,
                String.class);

        String[] bodyStringList = Objects.requireNonNull(response.getBody()).split(",");

        assertThat(bodyStringList[1]).isEqualTo("\"username\":\"preUser\"");
        assertThat(bodyStringList[2]).isEqualTo("\"email\":\"preUser@mail.com\"");
        assertThat(bodyStringList[3]).isEqualTo("\"password\":null");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    //try to find non existing user
    public void findNonExistingUserByEmail(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + "/email" + "/testUser404@mail.com",
                HttpMethod.GET,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void updateUserById(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getRequest = new HttpEntity<>("", headers);

        ResponseEntity<String> getResponse = restTemplate.exchange(
                host + port + users + "/email" + "/preUser@mail.com",
                HttpMethod.GET,
                getRequest,
                String.class);

        String[] bodyStringList = Objects.requireNonNull(getResponse.getBody()).split(",");
        String[] idStringList = bodyStringList[0].split(":");
        String userId = idStringList[1].substring(1, idStringList[1].length()-1);

        HttpEntity<String> putRequest = new HttpEntity<>(
                "{\"username\":\"preUser\", \"email\":\"preUser@mail.com\", \"password\":\"abcdefg\"}",
                headers);

        ResponseEntity<String> putResponse = restTemplate.exchange(
                host + port + users + "/" + userId,
                HttpMethod.PUT,
                putRequest,
                String.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void updateUserByEmail(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{\"username\":\"preUser\", \"email\":\"preUser@mail.com\", \"password\":\"abcdefg\"}",
                headers);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + "/email" + "/preUser@mail.com",
                HttpMethod.PUT,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateNonExistingUserByEmail(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                nonExistentUser,
                headers);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + "/email" + "/testUser404@mail.com",
                HttpMethod.PUT,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}