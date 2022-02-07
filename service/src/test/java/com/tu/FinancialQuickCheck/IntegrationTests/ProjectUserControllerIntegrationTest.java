package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.Role;
import com.tu.FinancialQuickCheck.db.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The current test class verifies the functionalities of the Project User Controller
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectUserControllerIntegrationTest {

    static Logger log = Logger.getLogger(ProductAreaControllerIntegrationTest.class.getName());

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private ProjectUserRepository repository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String projects = "/projects/";
    private String users = "/users";

    HttpHeaders header = new HttpHeaders();

    ProjectEntity project;
    List<UserEntity> usersEntities;
    List<ProjectUserEntity> entities;

    UserEntity userNotAssignedToProject;

    /**
     * This annotated method should be executed before each invocation of @Test
     */
    @BeforeEach
    public void initEach(){
        log.info("@BeforeEach - setup for Tests in ProjectUserControllerIntegrationTest.class");

        project = new ProjectEntity();
        projectRepository.save(project);

        usersEntities = new ArrayList<>();
        entities = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            UserEntity user = new UserEntity();
            user.id = UUID.randomUUID().toString();
            user.username = "username " + i;
            user.email = "username" + i  + "@test.com";
            userRepository.save(user);
            usersEntities.add(user);

            ProjectUserEntity entity = new ProjectUserEntity();
            entity.projectUserId = new ProjectUserId();
            entity.projectUserId.setUser(user);
            entity.projectUserId.setProject(project);
            entity.role = Role.CLIENT;
            repository.save(entity);
            entities.add(entity);
        }

        userNotAssignedToProject = new UserEntity();
        userNotAssignedToProject.id = UUID.randomUUID().toString();
        userNotAssignedToProject.username = "unassigned user";
        userNotAssignedToProject.email = "unassigned@test.com";
        userRepository.save(userNotAssignedToProject);

        header.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * The method should be run after every @Test
     */
    @AfterEach
    public void reset() {
        repository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();

        log.info("@AfterEach - db reset");
    }

    /**
     * This test tries to find a project users for a project by its ID, but the there are no users
     *
     * @result The status code that the project users wasn't found in database
     */
    @Test
    public void test1_findProjectUsersByProjectId_expectResourceNotFound() {
        // delete all existing entries
        repository.deleteAll();
        projectRepository.deleteAll();

        // test object
        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.GET,null, String.class);

        log.info("@Test 1 - findProjectUsersByProjectId_expectResourceNotFound");
        log.info("@Test 1 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to get all project users from database by the project ID
     *
     * @result The status code that the project users were found in database
     */
    @Test
    public void test2_findProjectUsersByProjectId_expectSuccess_200() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.GET, null, String.class);

        log.info("@Test 2 - findProjectUsersByProjectId_expectSuccess_200");
        log.info("@Test 2 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * This test tries to update a single project user by its ID, but there is no user
     *
     * @result The status code that the project user wasn't updated in database
     */
    @Test
    public void test3_updateProjectUser_resourceNotFound_userID_singleUser(){
        String jsonString = "[{\"userID\":\"0fef539d-69be-4013-9380-6a12c3534c67\", \"role\":\"CLIENT\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 3 - updateProjectUser_expectResourceNotFound - user does not exist");
        log.info("@Test 3 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to update a multiple project user by its ID, but there are no project user with that IDs
     *
     * @result The status code that the project users were not found by their IDs
     */
    @Test
    public void test4_updateProjectUse_resourceNotFound_userID_multipleUsers(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id +  "\", \"role\":\"CLIENT\"}," +
                "{\"userID\":\"0fef539d-69be-4013-9380-6a12c3534c67\", \"role\":\"CLIENT\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 4 - updateProjectUser_expectResourceNotFound - second user does not exist");
        log.info("@Test 4 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to update a project user but the respective project ID wasn't found
     *
     * @result The status code that the project user was not found by its project ID
     */
    @Test
    public void test5_updateProjectUser_resourceNotFound_projectID(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id +  "\", \"role\":\"CLIENT\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + "0" + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 5 - updateProjectUser_expectResourceNotFound - projectID");
        log.info("@Test 5 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to update a project user but the user is not assigned to the project
     *
     * @result The status code that the project user is not assigned to the respective project
     */
    @Test
    public void test6_updateProjectUser_badRequest_userNotAssignedToProject_singleUser(){
        String jsonString = "[{\"userID\":\" " + userNotAssignedToProject.id + "\", \"role\":\"CLIENT\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 6 - updateProjectUser_expectBadRequest - user not assigned to project");
        log.info("@Test 6 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * This test tries to update multiple project users but the users are not assigned to the project
     *
     * @result The status code that the project users are not assigned to the respective project
     */
    @Test
    public void test7_updateProjectUser_badRequest_userNotAssignedToProject_multipleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\", \"role\":\"CLIENT\"}, " +
                "{\"userID\":\" " + userNotAssignedToProject.id + "\", \"role\":\"CLIENT\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 7 - updateProjectUser_expectBadRequest - second user not assigned to project");
        log.info("@Test 7 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * This test tries to update a project user
     *
     * @result The status code that the project user is updated
     */
    @Test
    public void test8_updateProjectUser_success_singleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\", \"role\":\"ADMIN\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 8 - updateProjectUser_success - single user");
        log.info("@Test 8 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: check output against input to db
    }

    /**
     * This test tries to update multiple project users
     *
     * @result The status code that the project users are updated
     */
    @Test
    public void test9_updateProjectUser_success_multipleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\", \"role\":\"ADMIN\"}, " +
                "{\"userID\":\" " + usersEntities.get(1).id + "\", \"role\":\"PROJECT_MANAGER\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 9 - updateProjectUser_success - multiple users");
        log.info("@Test 9 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: check output against input to db
    }

    /**
     * This test tries to update a project user, but not the role
     *
     * @result The status code that the project user is updated except the role
     */
    @Test
    public void test10_updateProjectUser_success_singleUser_withoutRole(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 10 - updateProjectUser_success - single user - nothing to update");
        log.info("@Test 10 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: check output against input to db
    }

    /**
     * This test tries to update  project users, but not their roles
     *
     * @result The status code that the project users are updated except the roles
     */
    @Test
    public void test11_updateProjectUser_success_multipleUser_withoutRole(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}, " +
                "{\"userID\":\" " + usersEntities.get(1).id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.PUT, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 11 - updateProjectUser_success - multiple users - nothing to update");
        log.info("@Test 11 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: check output against input to db
    }

    /**
     * This test tries to delete a project user but the user is not assigned to the project
     *
     * @result The status code that the user wasn't found for the project
     */
    @Test
    public void test12_deleteProjectUser_resourceNotFound_userNotAssignedToProject_singleUser(){
        String jsonString = "[{\"userID\":\" " + userNotAssignedToProject.id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 12 - deleteProjectUser_resourceNotFound - user not assigned to project");
        log.info("@Test 12 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to delete multiple project users but the users are not assigned to the project
     *
     * @result The status code that the users weren't found for the project
     */
    @Test
    public void test13_deleteProjectUser_resourceNotFound_userNotAssignedToProject_multipleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}, " +
                "{\"userID\":\" " + userNotAssignedToProject.id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 13 - deleteProjectUser_resourceNotFound - second user not assigned to project");
        log.info("@Test 13 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to delete a user for a project, but the ID wasn't found
     *
     * @result The status code that project ID wasn't found, therefore the user wasn't deleted
     */
    @Test
    public void test14_deleteProjectUser_badRequest_projectID(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + 0 + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 14 - deleteProjectUser_badRequest - projectID");
        log.info("@Test 14 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * This test tries to delete users for a project, but the ID wasn't found
     *
     * @result The status code that project ID wasn't found, therefore the users weren't deleted
     */
    @Test
    public void test15_deleteProjectUsers_badRequest_projectID(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + 0 + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 14 - deleteProjectUsers_badRequest - projectID");
        log.info("@Test 14 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * This test tries to delete a user for a project, but the user isn't assigned to the project
     *
     * @result The status code that the user for the project wasn't found because he wasn't assigned
     */
    @Test
    public void test16_deleteProjectUser_resourceNotFound_userNotAssignedToProject_singleUser(){
        String jsonString = "[{\"userID\":\" " + userNotAssignedToProject.id + "\", \"role\":\"CLIENT\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 16 - deleteProjectUser_resourceNotFound - user not assigned to project");
        log.info("@Test 16 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to delete users for a project, but the users aren't assigned to the project
     *
     * @result The status code that the users for the project weren't found because they weren't assigned
     */
    @Test
    public void test17_deleteProjectUser_resourceNotFound_userNotAssignedToProject_multipleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}, " +
                "{\"userID\":\" " + userNotAssignedToProject.id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 17 - deleteProjectUser_resourceNotFound - second user not assigned to project");
        log.info("@Test 17 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This method tries to delete a user from project
     *
     * @result The status code that the user is deleted from project
     */
    @Test
    public void test18_deleteProjectUser_success_userNotAssignedToProject_multipleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 18 - deleteProjectUser_success - multiple users");
        log.info("@Test 18 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * This method tries to delete users from project
     *
     * @result The status code that the users are deleted from project
     */
    @Test
    public void test19_deleteProjectUsers_success_userNotAssignedToProject_multipleUser(){
        String jsonString = "[{\"userID\":\" " + usersEntities.get(0).id + "\"}, " +
                "{\"userID\":\" " + usersEntities.get(1).id + "\"}]";

        ResponseEntity<String> response = restTemplate.exchange(host + port + projects + project.id + users,
                HttpMethod.DELETE, new HttpEntity<>(jsonString, header), String.class);

        log.info("@Test 19 - deleteProjectUsers_success - multiple users");
        log.info("@Test 19 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
