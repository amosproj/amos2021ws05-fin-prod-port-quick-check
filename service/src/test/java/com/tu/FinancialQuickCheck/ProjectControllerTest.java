package com.tu.FinancialQuickCheck;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private String projectIDExists = "/1";

    @Test
    public void notAllowedHTTPMethodsProjects() throws Exception {

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
    public void listenHTTPMethodGETProjects() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("Response Status: " + response.getStatusCode());
    }


    @Test
    public void listenHTTPMethodPOSTProjects() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        System.out.println("Response Status: " + response.getStatusCode());
    }


    @Test
    public void notAllowedHTTPMethodsProjectId() throws Exception {

        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.POST};
        for (HttpMethod httpMethod : notAllowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + projects + "/" + projectIDExistsNot,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }


    @Test
    public void listenHTTPMethodProjectIdDoesNotExist() throws Exception {
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
    public void listenHTTPMethodProjectIdExists() throws Exception {
        // TODO: wie kann ich sicherstellen, dass projectID immer existiert?
        // Mögliche Lösung: selbst einfügen (evtl Problem: unnötiger Datensatz in der DB?)

        // TODO: HttpMethod.PUT --> benötigt JSON Input
        HttpMethod[] allowed = new HttpMethod[]{HttpMethod.GET, HttpMethod.DELETE};
        for (HttpMethod httpMethod : allowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + projects + projectIDExists,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            //        System.out.println("Response Status: " + response.getStatusCode());
        }
    }

//    @Test
//    public void listenHttpPUTProjectIdDoesNotExist() throws Exception {
//        // TODO: change requestEntity to expected JSON Input
//        ResponseEntity<String> response = restTemplate.exchange(
//                host + port + projects + projectIDExistsNot,
//                HttpMethod.PUT,
//                null,
//                String.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        //        System.out.println("Response Status: " + response.getStatusCode());
//    }
//
//
//    @Test
//    public void listenHTTPMethodProjectIdExists() throws Exception {
//        // TODO: wie kann ich sicherstellen, dass projectID immer existiert?
//        // Mögliche Lösung: selbst einfügen (evtl Problem: unnötiger Datensatz in der DB?)
//
//        // TODO: change requestEntity to expected JSON input
//        ResponseEntity<String> response = restTemplate.exchange(
//                host + port + projects + projectIDExists,
//                HttpMethod.PUT,
//                null,
//                String.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        //        System.out.println("Response Status: " + response.getStatusCode());
//    }

}