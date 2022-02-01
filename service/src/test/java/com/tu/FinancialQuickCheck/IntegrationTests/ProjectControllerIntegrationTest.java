package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectControllerIntegrationTest {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAreaRepository productAreaRepository;

    @Autowired
    private ProductRatingRepository productRatingRepository;


    private String host = "http://localhost:";
    private String projects = "/projects";
    private String projectIDExistsNot = "/0";
    private String projectID1 = "/1";

    HttpHeaders header = new HttpHeaders();

    String jsonStringEmpty = "{}";
    String jsonStringIncorrectFormat = "{\"category\":\"PRIVAT\", \"name\":\"KREDIT\"}";
    String jsonStringIncorrectData =
            "{" +
            "\"projectID\":\"66\"," +
            " \"creatorID\":\"666\", " +
            "\"projectName:\" : \"Test Project\"," +
            "\"members\" : [404]" +
            "\"productAreas\" : [404]" +
            "}";
    String jsonStringCorrect =
            "{" +
                    "\"projectID\":\"66\"," +
                    " \"creatorID\":\"666\", " +
                    "\"projectName:\" : \"Test Project\"," +
                    "\"members\" : [6]" +
                    "\"productAreas\" : [12]" +
                    "}";

    private ProductAreaEntity tmpArea;
    private ProductEntity tmpProduct;
    private ProductRatingEntity tmpRating;
    private ProductRatingId tmpRatingID;
    private UserEntity tmpUser;
    private ProjectUserEntity tmpProjectUser;
    private ProjectUserId tmpUserID;
    private ProjectEntity tmp;

    private ProductAreaEntity afterAreaEntity;
    private ProjectEntity afterEntity;
    private ProductEntity afterProductEntity;
    private ProjectUserRepository projectUserRepository;

    //@BeforeEach
    public void init(){

        //create Product Area
        tmpArea = new ProductAreaEntity();
        tmpArea.id = 12;
        tmpArea.name = "KREDIT";
        tmpArea.category = "PRIVAT";

        productAreaRepository.save(tmpArea);

        List<ProductAreaEntity> tmpAreout = productAreaRepository.findAll();
        afterAreaEntity = new ProductAreaEntity();
        afterAreaEntity.id = tmpAreout.get(0).id;
        afterAreaEntity.name = tmpAreout.get(0).name;
        afterAreaEntity.category = tmpAreout.get(0).category;

        //create ratingID
        tmpRatingID = new ProductRatingId();

        //create Product Ratings
        tmpRating = new ProductRatingEntity();
        tmpRating.productRatingId = tmpRatingID;
        tmpRating.comment = "example rating Comment";
        tmpRating.score = null;

        List<ProductRatingEntity> tmpRatingList = new ArrayList<>();
        tmpRatingList.add(tmpRating);

        //create Product
        /**tmpProduct = new ProductEntity();
        tmpProduct.id = 24;
        tmpProduct.name = "exampleProduct";
        tmpProduct.productarea = tmpArea;
        tmpProduct.comment = "example Product Comment";
        tmpProduct.project = tmp;
        tmpProduct.productRatingEntities = tmpRatingList;

        productRepository.save(tmpProduct);

        List<ProductEntity> tmpProductOut = productRepository.findAll();
        afterProductEntity = new ProductEntity();
        afterProductEntity.id = tmpProductOut.get(0).id;
        afterProductEntity.name = tmpProductOut.get(0).name;
        afterProductEntity.parentProduct = tmpProductOut.get(0).parentProduct;
        afterProductEntity.productarea = tmpProductOut.get(0).productarea;
        afterProductEntity.comment = tmpProductOut.get(0).comment;

        List<ProductEntity> tmpProductList = new ArrayList<>();
        tmpProductList.add(tmpProduct);**/

        //create Project
        tmp = new ProjectEntity();
        tmp.id = 42;
        tmp.name = "exampleProject";
        //tmp.productEntities = tmpProductList;

        repository.save(tmp);

        List<ProjectEntity> out = repository.findAll();
        afterEntity = new ProjectEntity();
        afterEntity.id = out.get(0).id;
        afterEntity.name = out.get(0).name;
        afterEntity.productEntities = out.get(0).productEntities;

        //create User
        tmpUser = new UserEntity();
        tmpUser.id = "4";
        tmpUser.username = "exampleUsername";
        tmpUser.email = "exampleUsername@web.de";
        tmpUser.password = "12345";

        tmpUserID = new ProjectUserId(tmp, tmpUser);

        tmpProjectUser = new ProjectUserEntity();

        tmpProjectUser.projectUserId = tmpUserID;

        projectUserRepository.save(tmpProjectUser);


    }

    //@AfterEach
    public void reset(){
        repository.delete(afterEntity);
        productAreaRepository.delete(afterAreaEntity);
        projectUserRepository.delete(tmpProjectUser);
        //productRepository.delete(afterProductEntity);
    }

    @Test
    public void testCreateProject_1_noRequestBody(){

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateProject_2_wrongRequestBody(){

        HttpEntity<String> request = new HttpEntity<>(jsonStringIncorrectFormat, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateProject_3_wrongData(){

        HttpEntity<String> request = new HttpEntity<>(jsonStringIncorrectData, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateProject_4(){

        HttpEntity<String> request = new HttpEntity<>(jsonStringCorrect, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void putProjectIdExists() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{\"id\":1,\"name\": \"neuer Bankname\",\"creatorID\":1,\"members\":[1,2,3],\"productAreas\":[]}",
                headers
        );


        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + projectID1,
                HttpMethod.PUT,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //        System.out.println("Response Status: " + response.getStatusCode());

    }

    @Test
    public void getProjects() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("Response Status: " + response.getStatusCode());
    }

    @Test
    public void notAllowedMethodsProjects() throws Exception {

        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.PUT, HttpMethod.DELETE};
        for (HttpMethod httpMethod : notAllowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + projects,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

    @Test
    public void allowedMethodsProjectIdDoesNotExist() throws Exception {
        HttpMethod[] allowed = new HttpMethod[]{HttpMethod.GET, HttpMethod.DELETE};
        for (HttpMethod httpMethod : allowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + projects + projectIDExistsNot,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            //        System.out.println("Response Status: " + response.getStatusCode());
        }
    }

    @Test
    public void notAllowedMethodsProjectId() throws Exception {

        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.POST};
        for (HttpMethod httpMethod : notAllowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + projects + projectID1,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

    @Test
    public void allowedMethodsProjectIdExists() throws Exception {
        HttpMethod[] allowed = new HttpMethod[]{HttpMethod.GET, HttpMethod.DELETE};
        for (HttpMethod httpMethod : allowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + projects + projectID1,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

    @Test
    public void putProjectIdDoesNotExist() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{ }",
                headers
        );


        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + projectIDExistsNot,
                HttpMethod.PUT,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        //        System.out.println("Response Status: " + response.getStatusCode());
    }

}