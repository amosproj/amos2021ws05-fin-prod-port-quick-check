package com.tu.FinancialQuickCheck.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String projects = "/projects";
    private String projectID1 = "/1";
    private String testProjectID;
    private String users = "/users";
    private String productAreas = "/productareas";
    private String initProductArea = "{\"category\":\"PRIVAT\", \"name\":\"KREDIT\"}";
    private String testUserID;
    private String productAreaID;


    // creates an entry with projectID 1 in test db and creates a user
    @BeforeEach
    public void initEach(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(initProductArea, headers);

        String responseBody = restTemplate.exchange(
                host + port + productAreas,
                HttpMethod.POST,
                request,
                String.class).getBody();

        System.out.println("Product Area: " + responseBody);

        String[] bodyStringListID = responseBody.split(",");

        String tmpID = bodyStringListID[0].split(":")[1];
        productAreaID = tmpID;

        //create Project
        HttpEntity<String> createProjectRequest = new HttpEntity<>(
                "{\"creatorID\":\"bf6d44d2-52bc-11ec-bf63-0242ac130002\"," +
                        "\"projectName\":\"new_Project\"," +
                        "\"productAreas\":[" + productAreaID + "]}",
                headers);

        String createProjectResponse = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                createProjectRequest,
                String.class).getBody();

        String[] bodyStringList = createProjectResponse.split(",");
        testProjectID = bodyStringList[0].split(":")[1];

        System.out.println("Create Project: " + createProjectResponse);

        //create User
        HttpEntity<String> createUserRequest = new HttpEntity<>(
                "{\"userName\":\"preUser\"," +
                        "\"userEmail\":\"preUser@mail.com\", " +
                        "\"password\":\"1234\"}",
                headers);

        String createUserResponse = restTemplate.exchange(
                host + port + users,
                HttpMethod.POST,
                createUserRequest,
                String.class).getBody();

        System.out.println("Create User: " + createUserResponse);

        //get UserID
        HttpEntity<String> userIdRequest = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + users + "/email" + "/preUser@mail.com",
                HttpMethod.GET,
                userIdRequest,
                String.class);

        bodyStringList = Objects.requireNonNull(response.getBody()).split(",");

        String tmp = bodyStringList[2].split(":")[1];
        testUserID = tmp.substring(1, tmp.length()-1);

        System.out.println("Get UserID: " + testUserID);
    }


    // delete all data entries in test db
    @AfterEach
    public void afterwards(){

        //delete project
        restTemplate.delete(host + port + projects);

        //delete user
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{\"username\":\"preUser\"," +
                        "\"email\":\"preUser@mail.com\", " +
                        "\"password\":\"1234\"}",
                headers);

        String tmp = restTemplate.exchange(
                host + port + users,
                HttpMethod.DELETE,
                request,
                String.class).getBody();
    }

    @Test
    public void createProjectUserTest(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{\"userID\":" + "\"" + testUserID + "\"" + "," +
                        "\"projectID\":" + "\"" + testProjectID + "\"" + "," +
                        "\"userName\":\"preUser\"," +
                        "\"userEmail\":\"preUser@mail.com\"," +
                        "\"role\":\"CLIENT\"}",
                headers
        );

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/" + testProjectID + users,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updateProjectUserTest(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{\"userID\":" + "\"" + testUserID + "\"" + "," +
                        "\"projectID\":" + "\"" + testProjectID + "\"" + "," +
                        "\"userName\":\"testUser\"," +
                        "\"userEmail\":\"preUser@mail.com\"," +
                        "\"role\":\"ADMIN\"}",
                headers
        );

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/" + testProjectID + users,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }



}
