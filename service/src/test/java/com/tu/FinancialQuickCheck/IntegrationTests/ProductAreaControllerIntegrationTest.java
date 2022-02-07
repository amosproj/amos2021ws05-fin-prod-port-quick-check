package com.tu.FinancialQuickCheck.IntegrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The current test class verifies the functionalities of the Product Area Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductAreaControllerIntegrationTest {

    static Logger log = Logger.getLogger(ProductAreaControllerIntegrationTest.class.getName());

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private ProductAreaRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String productAreas = "/productareas";

    private HttpHeaders header = new HttpHeaders();
    private String jsonStringCorrect = "{\"category\":\"PRIVAT\", \"name\":\"KREDIT\"}";
    private String jsonStringEmpty = "{}";
    private String jsonStringMissingCategory = "{\"name\":\"KREDIT\"}";
    private String jsonStringMissingName = "{\"category\":\"PRIVAT\"}";

    private ProductAreaEntity entity;

    /**
     * This annotated method should be executed before each invocation of @Test
     */
    @BeforeEach
    public void init(){
        log.info("@BeforeEach - setup for Tests in ProductAreaControllerIntegrationTest.class");

        header.setContentType(MediaType.APPLICATION_JSON);

        entity = new ProductAreaEntity();
        entity.category = "PRIVAT";
        entity.name = "KREDIT";
        repository.save(entity);
    }

    /**
     * The method should be run after every @Test
     */
    @AfterEach
    public void reset(){
        repository.deleteAll();
    }

    /**
     * This test tries to get a product area from database, but there is no product area in database
     *
     * @result The status code that the product area wasn't found in database
     */
    @Test
    public void test1_getProductAreas_resourceNotFound() {
        // delete all existing productAreas
        repository.deleteAll();

        ResponseEntity<String> response = restTemplate.exchange(host + port + productAreas,
                HttpMethod.GET,null, String.class);

        log.info("@Test 1 - getProductAreas_resourceNotFound - no product areas in db");
        log.info("@Test 1 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * This test tries to get all product area from database
     *
     * @result The status code that the product area was found in database
     */
    @Test
    public void test2_getProductAreas_success_200(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + productAreas,
                HttpMethod.GET,null, String.class);

        log.info("@Test 2 - getProductAreas_success_200");
        log.info("@Test 2 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * This test tries to store product areas in database
     *
     * @result Indicators that the created product area is stored in database
     */
    @Test
    public void test3_postProductAreas_created_201() throws JsonProcessingException {
        HttpEntity<String> request = new HttpEntity<>(jsonStringCorrect, header);

        ResponseEntity<String> response = restTemplate.exchange(host + port + productAreas,
                HttpMethod.POST, request, String.class);

        log.info("@Test 3 - postProductAreas_created_201");
        log.info("@Test 3 - Response Body: " + response.getBody());

        ProductAreaEntity out = new ObjectMapper().readValue(response.getBody(), ProductAreaEntity.class);
        assertEquals(entity.id + 1, out.id);
        assertEquals("KREDIT", out.name);
        assertEquals("PRIVAT", out.category);
        assertNull(out.productEntities);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /**
     * This test tries to store product areas in database
     *
     * @result Indicators that the created product area wasn't stored in database
     */
    @Test
    public void test4_postProductAreas_badRequest(){
        String[] testObjects = new String[]{"", jsonStringEmpty, jsonStringMissingName, jsonStringMissingCategory};
        for (String jsonString: testObjects) {
            ResponseEntity<String> response = restTemplate.exchange(host + port + productAreas,
                    HttpMethod.POST, new HttpEntity<>(jsonString, header), String.class);

            log.info("@Test 4 - postProductAreas_expectBadRequest");
            log.info("@Test 4 - Request Body: " + jsonString);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * The test indicates that the server knows the request method, but the target resource doesn't support this
     * method.
     *
     * @result The status code indicates that the method was not allowed.
     */
    @Test
    public void test5_notAllowedMethodsProductAreas() {
        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.PUT, HttpMethod.DELETE};
        for (HttpMethod httpMethod : notAllowed) {
            ResponseEntity<String> response = restTemplate.exchange(host + port + productAreas,
                    httpMethod, null, String.class);

            log.info("@Test 5 - notAllowedMethodsProductAreas  - method: " + httpMethod);
            log.info("@Test 5 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}