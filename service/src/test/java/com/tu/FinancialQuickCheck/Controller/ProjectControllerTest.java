package com.tu.FinancialQuickCheck.Controller;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String projects = "/projects";
    private String projectIDExistsNot = "/0";
    private String projectID1 = "/1";



    @BeforeEach
    public void initEach(){
        // creates an entry with projectID 1 in test db
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{\"id\":1,\"name\": \"neuer Bankname\",\"creatorID\":1,\"members\":[1,2,3],\"productAreas\":[]}",
                headers
        );

        String tmp = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class).getBody();

        System.out.println("Before Result: " + tmp);

    }

    @AfterEach
    public void afterwards(){
        // should delete all data entries in test db
        restTemplate.delete(host + port + projects);
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