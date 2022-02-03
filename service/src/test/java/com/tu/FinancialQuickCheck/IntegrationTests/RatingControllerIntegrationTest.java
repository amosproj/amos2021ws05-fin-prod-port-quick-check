package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
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
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The current test class verifies the functionalities of the Product Area Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RatingControllerIntegrationTest {

    static Logger log = Logger.getLogger(RatingControllerIntegrationTest.class.getName());

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private RatingRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String ratings = "/ratings";
    private String complexity = "?ratingArea=COMPLEXITY";
    private String economic = "?ratingArea=ECONOMIC";

    private HttpHeaders header = new HttpHeaders();
    private String jsonStringEmpty = "{}";

    private List<RatingEntity> entities;
    private List<RatingEntity> entitiesEconomic;
    private List<RatingEntity> entitiesComplexity;

    @BeforeEach
    public void init(){
        log.info("@BeforeEach - setup for Tests in RatingControllerIntegrationTest.class");

        header.setContentType(MediaType.APPLICATION_JSON);

        entities = new ArrayList<>();
        entitiesEconomic = new ArrayList<>();
        entitiesComplexity = new ArrayList<>();

        for(int i = 1; i < 48; i++){
            RatingEntity ratingEntity = new RatingEntity();
            ratingEntity.criterion = "criterion" + i;
            ratingEntity.category = "category";
            ratingEntity.ratingarea = (i < 9) ? RatingArea.ECONOMIC : RatingArea.COMPLEXITY;

            entities.add(ratingEntity);

            if(i < 9){
                entitiesEconomic.add(ratingEntity);
            }else{
                entitiesComplexity.add(ratingEntity);
            }
        }

        repository.saveAll(entities);
    }

    @AfterEach
    public void reset(){
        repository.deleteAll();
    }

    @Test
    public void test1_getRatings_resourceNotFound() {
        repository.deleteAll();

        ResponseEntity<String> response = restTemplate.exchange(host + port + ratings,
                HttpMethod.GET,null, String.class);

        log.info("@Test 1 - getRatings_resourceNotFound - no ratings in db");
        log.info("@Test 1 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test2_getRatings_success() {
        String[] urls = {host + port + ratings, host + port + ratings + complexity, host + port + ratings + economic};

        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(tmpUrl, HttpMethod.GET,null, String.class);

            log.info("@Test 2 - getRatings_success - ratings retrieved from db");
            log.info("@Test 2 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            //TODO: test output against db entries
        }
    }
}