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
 * The current test class verifies the functionalities of the Rating Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RatingControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String ratings = "/ratings";

    /**
     * This test tries to find all ratings
     *
     * @result The status code that the ratings are found
     */
    @Test
    public void getAllRatings() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + ratings,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("Response Status: " + response.getStatusCode());
    }

    /**
     * This test tries to find all ratings for a rating area
     *
     * @result The status code that the ratings for the rating area are found
     */
    public void getRatingsByRatingArea() throws Exception {

        String[] ratingAreas = {"?ratingArea=COMPLEXITY", "?ratingArea=ECONOMIC"};

        for(String ratingArea: ratingAreas){
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + ratings + ratingArea,
                    HttpMethod.GET,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("Response Status: " + response.getStatusCode());
        }
    }
    /**
     * The test indicates that the server knows the request method, but the target resource doesn't support this
     * method.
     *
     * @result The status code indicates that the method was not allowed.
     */
    @Test
    public void notAllowedMethodsProductAreas() throws Exception {

        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.POST,HttpMethod.PUT, HttpMethod.DELETE};
        for (HttpMethod httpMethod : notAllowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + ratings,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

}