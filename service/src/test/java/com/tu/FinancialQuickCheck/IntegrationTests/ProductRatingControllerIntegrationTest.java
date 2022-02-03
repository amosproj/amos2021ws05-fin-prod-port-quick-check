package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Score;
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
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductRatingControllerIntegrationTest {

    static Logger log = Logger.getLogger(ProductRatingControllerIntegrationTest.class.getName());

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRatingRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String products = "/products/";
    private String ratings = "/ratings";
    private String complexity = "?ratingArea=COMPLEXITY";
    private String economic = "?ratingArea=ECONOMIC";

    private String jsonStringCorrectOneEntry = "{\"ratings\": [{\"ratingID\": 1, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 1\",\"score\": \"GERING\"}]}";
    private String jsonStringCorrectTwoEntries = "{\"ratings\": [{\"ratingID\": 1, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 1\",\"score\": \"GERING\"}," +
            "{\"ratingID\": 2, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 2\",\"score\": \"MITTEL\"}]}";
    private String jsonStringEmpty = "{}";
    private String jsonStringInvalidRatingID = "{\"ratings\": [{\"ratingID\": 100, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 1\",\"score\": \"GERING\"}]}";
    private String jsonStringCorrectTwoEntriesInvalidRatingID = "{\"ratings\": [{\"ratingID\": 1, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 1\",\"score\": \"GERING\"}," +
            "{\"ratingID\": 200, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 2\",\"score\": \"MITTEL\"}]}";
    private String jsonStringMissingAnswer = "{\"ratings\": [{\"ratingID\": 1, \"comment\": \"new comment 1\",\"score\": \"GERING\"}]}";
    private String jsonStringMissingComment = "{\"ratings\": [{\"ratingID\": 1, \"answer\": \"700 Mio. EUR\",\"score\": \"GERING\"}]}";
    private String jsonStringMissingScore = "{\"ratings\": [{\"ratingID\": 1, \"answer\": \"700 Mio. EUR\",\"comment\": \"new comment 1\"}]}";

    HttpHeaders header = new HttpHeaders();

    private ProjectEntity project;
    private ProductEntity product;
    private ProductEntity productWithoutRatings;

    private List<RatingEntity> ratingEntities;
    private List<RatingEntity> econimicRatingEntities;
    private List<RatingEntity> complexityRatingEntities;

    private List<ProductRatingEntity> entities;
    private List<ProductRatingEntity> entitiesComplexity;
    private List<ProductRatingEntity> entitiesEconomic;


    @BeforeEach
    public void initEach(){
        System.out.println("Initilize ProductRatingControllerTest.........");

        header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        project = new ProjectEntity();
        projectRepository.save(project);

        product = new ProductEntity();
        product.project = project;
        productRepository.save(product);

        productWithoutRatings = new ProductEntity();
        productWithoutRatings.project = project;
        productRepository.save(productWithoutRatings);

        ratingEntities = new ArrayList<>();
        econimicRatingEntities = new ArrayList<>();
        complexityRatingEntities = new ArrayList<>();

        entities  = new ArrayList<>();
        entitiesComplexity = new ArrayList<>();
        entitiesEconomic = new ArrayList<>();

        for(int i = 1; i < 48; i++){
            ProductRatingEntity tmp = new ProductRatingEntity();
            tmp.answer = "answer" + i;
            tmp.score = Score.HOCH;
            tmp.comment = "comment" + i;

            Optional<RatingEntity> ratingEntity = ratingRepository.findById(i);
            if(ratingEntity.isEmpty()){
                RatingEntity rating = new RatingEntity();
                rating.criterion = "criterion" + i;
                rating.category = "category";
                rating.ratingarea = (i < 9) ? RatingArea.ECONOMIC : RatingArea.COMPLEXITY;
                ratingEntities.add(rating);
                tmp.productRatingId = new ProductRatingId(product, rating);
                if(i < 9){
                    econimicRatingEntities.add(rating);
                }else{
                    complexityRatingEntities.add(rating);
                }
            }else{
                tmp.productRatingId = new ProductRatingId(product, ratingEntity.get());
            }

            entities.add(tmp);

            if(i < 9){
                entitiesEconomic.add(tmp);
            }else{
                entitiesComplexity.add(tmp);
            }
        }

        ratingRepository.saveAll(ratingEntities);
        repository.saveAll(entities);
    }

    @AfterEach
    public void reset(){
        List<ProductRatingEntity> tmp = repository.findAll();
        if(!tmp.isEmpty()){
            for(ProductRatingEntity pr: tmp){
                repository.deleteById(pr.productRatingId);
            }
        }
    }

    @Test
    public void test1_getProductRatings_resourceNotFound_productID(){
        // test object
        String[] urls = {host + port + products + "0" + ratings, host + port + products + "0" + ratings + complexity,
                host + port + products + "0" + ratings + economic};

        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(tmpUrl, HttpMethod.GET,null, String.class);

            log.info("@Test 1 - getProductRatings_resourceNotFound - productID not in db");
            log.info("@Test 1 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        }
    }

    @Test
    public void test2_getProductRatings_success_returnEmptyList(){
        // test object
        String[] urls = {host + port + products + productWithoutRatings.id + ratings,
                host + port + products + productWithoutRatings.id + ratings + complexity,
                host + port + products + productWithoutRatings.id + ratings + economic};

        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(tmpUrl, HttpMethod.GET,null, String.class);

            log.info("@Test 2 - getProductRatings_success - product has no ratings - return empty list");
            log.info("@Test 2 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test
    public void test3_getProductRatings_success(){
        // test object
        String[] urls = {host + port + products + product.id + ratings,
                host + port + products + product.id + ratings + complexity,
                host + port + products + product.id + ratings + economic};

        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(tmpUrl, HttpMethod.GET,null, String.class);

            log.info("@Test 2 - getProductRatings_success - product has ratings");
            log.info("@Test 2 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            // TODO: check output against db
        }
    }

    @Test
    public void test4_updateProductRatings_resourceNotFound_productID(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + "0" + ratings,
                HttpMethod.PUT,new HttpEntity<>(jsonStringCorrectOneEntry, header), String.class);

        log.info("@Test 4 - updateProductRatings_resourceNotFound - productID not in db");
        log.info("@Test 4 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test5_updateProductRatings_resourceNotFound_ratingID(){
        String[] testobjects = {jsonStringInvalidRatingID, jsonStringCorrectTwoEntriesInvalidRatingID};

        for(String jsonString: testobjects){
            ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id + ratings,
                    HttpMethod.PUT,new HttpEntity<>(jsonString, header), String.class);

            log.info("@Test 5 - updateProductRatings_resourceNotFound - ratingID not in db");
            log.info("@Test 5 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void test6_updateProductRatings_badRequest_emptyJSON(){
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id + ratings,
                HttpMethod.PUT,new HttpEntity<>(jsonStringEmpty, header), String.class);

        log.info("@Test 6 - updateProductRatings_badRequest - json string is empty");
        log.info("@Test 6 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test7_updateProductRatings_success_fullUpdate(){
        String[] testobjects = {jsonStringCorrectOneEntry, jsonStringCorrectTwoEntries};

        for(String jsonString: testobjects){
            ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id + ratings,
                    HttpMethod.PUT,new HttpEntity<>(jsonString, header), String.class);

            log.info("@Test 7 - updateProductRatings_success - ratingID not in db");
            log.info("@Test 7 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            //TODO: check output against original db entry
        }
    }

    @Test
    public void test8_updateProductRatings_success_partialUpdate_missingAnswer(){
//        String[] testobjects = {jsonStringMissingAnswer};
        List<RatingEntity> test = ratingRepository.findAll();
        List<ProductRatingEntity> tmp = repository.findAll();
//        for(String jsonString: testobjects){
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id + ratings,
                HttpMethod.PUT,new HttpEntity<>(jsonStringMissingAnswer, header), String.class);

        log.info("@Test 8 - updateProductRatings_success - comment and score of one product rating entity updated");
        log.info("@Test 8 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //TODO: check output against original db entry
//        }
    }

    @Test
    public void test9_updateProductRatings_success_partialUpdate_missingComment(){
        String[] testobjects = {jsonStringMissingComment};

        for(String jsonString: testobjects){
            ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id + ratings,
                    HttpMethod.PUT,new HttpEntity<>(jsonString, header), String.class);

            log.info("@Test 9 - updateProductRatings_success - answer and score of one product rating entity updated");
            log.info("@Test 9 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            //TODO: check output against original db entry
        }
    }

    @Test
    public void test10_updateProductRatings_success_partialUpdate_missingScore(){
        String[] testobjects = {jsonStringMissingScore};

        for(String jsonString: testobjects){
            ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id + ratings,
                    HttpMethod.PUT,new HttpEntity<>(jsonString, header), String.class);

            log.info("@Test 10 - updateProductRatings_success - answer and comment of one product rating entity updated");
            log.info("@Test 10 - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            //TODO: check output against original db entry
        }
    }

    @Test
    public void notAllowedMethodsProductRatings() {
        String[] urls = {host + port + products + product.id + ratings,
                host + port + products + product.id + ratings + complexity,
                host + port + products + product.id + ratings + economic};
        for (String tmpUrl : urls) {
            ResponseEntity<String> response = restTemplate.exchange(
                    tmpUrl, HttpMethod.DELETE, null, String.class);

            log.info("@Test xx - not allowed methods on Product Rating Controller");
            log.info("@Test xx - Response Body: " + response.getBody());
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}