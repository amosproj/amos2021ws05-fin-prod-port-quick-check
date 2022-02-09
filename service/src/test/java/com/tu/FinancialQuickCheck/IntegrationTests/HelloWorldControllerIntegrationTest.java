package com.tu.FinancialQuickCheck.IntegrationTests;

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

/**
 * The current test class verifies the functionalities of the Hello World Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloWorldControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Greeting should return default message.
     *
     * @result Test should return default message "Hello World".
     */
    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/helloworld",
                String.class)
        ).contains("Hello World!");
    }

    /**
     * The test indicates that the server knows the request method, but the target resource doesn't support this
     * method.
     *
     * @result The status code indicates that the method was not allowed.
     */
    @Test
    public void notAllowedHTTPMethod() throws Exception{
        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
        for(HttpMethod httpMethod: notAllowed){
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:" + port + "/helloworld",
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    /**
     * The test indicates that the server knows the request method "PUT", but the target resource doesn't support this
     * method.
     *
     * @result The status code indicates that the method was not allowed.
     */
    @Test
    public void notAllowedHTTPMethodPOST() throws Exception {
        assertThat(this.restTemplate.postForObject(
                "http://localhost:" + port + "/helloworld",
                null, String.class)
        ).contains("405");
    }
}