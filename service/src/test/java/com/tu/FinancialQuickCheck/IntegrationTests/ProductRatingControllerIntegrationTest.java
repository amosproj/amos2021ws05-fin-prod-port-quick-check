package com.tu.FinancialQuickCheck.IntegrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The current test class verifies the functionalities of the Product Rating Controller.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductRatingControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String projects = "/projects";
    private String productArea = "/productareas";
    private String products = "/products";
    private String ratings = "/ratings";
    private String complexity = "?ratingArea=COMPLEXITY";
    private String economic = "?ratingArea=ECONOMIC";
    private String ID1 = "/1";
    private String IDExistsNot = "/0";


    /**
     * This annotated method should be executed before each invocation of @Test
     */
    @BeforeEach
    public void initEach(){
        System.out.println("Initilize ProductRatingControllerTest.........");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        // creates entries in projectArea entity
        String[] newProductAreas = {"{\"name\": \"KREDIT\", \"category\": \"PRIVAT\"}",
                "{\"name\": \"KREDIT\", \"category\": \"BUSINESS\"}"};
        for(String productarea : newProductAreas){
            String tmpProductArea = restTemplate.exchange(
                    host + port + productArea,
                    HttpMethod.POST,
                    new HttpEntity<>(productarea, header),
                    String.class).getBody();
        }

        // creates an entry in project entity
        String tmpProject = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                new HttpEntity<>("{\"name\": \"neuer Bankname\",\"creatorID\":1,\"members\":[1,2,3], " +
                        "\"productAreas\":[1,2]}", header),
                String.class).getBody();


//        tmpProject.toString().split();

        System.out.println("Before Result: " + tmpProject);

        // creates an entry in product entity
        String tmpProduct = restTemplate.exchange(
                host + port + projects + ID1 + productArea + ID1 + products,
                HttpMethod.POST,
                new HttpEntity<>(
                        "{\"productName\": \"Optionen\"}",
                        header
                ),
                String.class).getBody();

        System.out.println("Before Result: " + tmpProduct);




    }
//
//    @AfterEach
//    public void afterwards(){
//        // should delete all data entries in test db
//        restTemplate.delete(host + port + projects);
//    }


    /**
     * The test indicates that the server knows the request method, but the target resource doesn't support this
     * method
     *
     * @result The status code indicates that the method was not allowed
     */
    @Test
    public void notAllowedMethodsProductRatings() throws Exception {

        String[] urls = {host + port + products + ID1 + ratings,
                host + port + products + ID1 + ratings + complexity,
                host + port + products + ID1 + ratings + economic};
        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(
                    tmpUrl,
                    HttpMethod.DELETE,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

    /**
     * This test tries to find a product rating ID, but the ID wasn't found
     *
     * @result The status code indicates that the method was not allowed
     */
    @Test
    public void allowedMethodsProductIdDoesNotExist() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                "{ }",
                headers
        );

        HttpMethod[] allowed = new HttpMethod[]{HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT};
        for (HttpMethod httpMethod : allowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + products + IDExistsNot + ratings,
                    httpMethod,
                    request,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            //        System.out.println("Response Status: " + response.getStatusCode());
        }
    }


    /**
     * This test  tries to return a Method object that reflects the specified public member method of the class
     * or interface
     *
     *  @result The status code indicates that the method was alright
     */
    @Test
    public void getMethod() throws Exception {

        String[] urls = {host + port + products + ID1 + ratings,
                host + port + products + ID1 + ratings + complexity,
                host + port + products + ID1 + ratings + economic};

        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(
                    tmpUrl,
                    HttpMethod.GET,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

}