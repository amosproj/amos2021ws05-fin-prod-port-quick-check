package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.*;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    static Logger log = Logger.getLogger(ProductAreaControllerIntegrationTest.class.getName());

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductAreaRepository productAreaRepository;
    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";
    private String products = "/products/";

    private String jsonStringCorrect = "{\"productName\":\"Neuer Produkt Name\", \"comment\":\"Neuer Kommentar\", \"resources\": [\"string\"]}";
    private String jsonStringEmpty = "{}";
    private String jsonStringMissingName = "{\"comment\":\"Neuer Kommentar\", \"resources\": [\"string\"]}";
    private String jsonStringMissingComment = "{\"productName\":\"Neuer Produkt Name\", \"resources\": [\"string\"]}";
    private String jsonStringMissingResources = "{\"productName\":\"Neuer Produkt Name\", \"comment\":\"Neuer Kommentar\"}";


    HttpHeaders header = new HttpHeaders();

    ProductEntity product;
    ProductEntity productVariant;
    ProjectEntity project;
    List<ProductAreaEntity> productAreaEntities;


    @BeforeEach
    public void initEach(){
        log.info("@BeforeEach - setup for Tests in ProjectUserControllerIntegrationTest.class");

        productAreaEntities = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            ProductAreaEntity area = new ProductAreaEntity();
            area.category = "category " + i;
            area.name = "name" + i;
            productAreaEntities.add(area);
        }
        productAreaRepository.saveAll(productAreaEntities);

        project = new ProjectEntity();
        projectRepository.save(project);

        product = new ProductEntity();
        product.project = project;
        product.name = "Produkt";
        product.productarea = productAreaEntities.get(0);
        repository.save(product);

        productVariant = new ProductEntity();
        productVariant.project = project;
        productVariant.name = "Produkt Variante";
        productVariant.productarea = productAreaEntities.get(0);
        productVariant.parentProduct = product;
        repository.save(productVariant);

        header.setContentType(MediaType.APPLICATION_JSON);
    }

    @AfterEach
    public void reset() {
        repository.deleteAll();
        projectRepository.deleteAll();
        productAreaRepository.deleteAll();

        log.info("@AfterEach - db reset");
    }

    public void assertResponseBody(ResponseEntity<String> response, ProductEntity product){
        ProductDto tmp = new ProductDto(product);
        String[] object = Objects.requireNonNull(response.getBody()).split(",");

        for(int i = 0; i < object.length; i++){
            object[i] = object[i]
                    .replace("[{", "")
                    .replace("{", "")
                    .replace("}]", "")
                    .replace("}", "")
                    .replace("\"", "");
        }

        assertEquals("productID:" + tmp.productID, object[0]);
        assertEquals("productName:" + tmp.productName, object[1]);
        assertEquals("productArea:id:" + tmp.productArea.id , object[2]);
        assertEquals("name:" + tmp.productArea.name , object[3]);
        assertEquals("category:" + tmp.productArea.category , object[4]);
        assertEquals("projectID:" + tmp.projectID , object[5]);
        assertEquals("parentID:" + tmp.parentID , object[6]);
        assertEquals("progressComplexity:0.0" , object[7]);
        assertEquals("progressEconomic:0.0" , object[8]);
        assertEquals("ratings:" + tmp.ratings , object[9]);
        assertEquals("productVariations:" + tmp.productVariations , object[10]);
        assertEquals("comment:" + tmp.comment , object[11]);
        assertEquals("resources:" + tmp.resources , object[12]);
    }

    @Test
    public void test1_findById_resourceNotFound() {
        // delete all existing entries
        repository.deleteAll();

        // test object
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + "0",
                HttpMethod.GET,null, String.class);

        log.info("@Test 1 - findById_resourceNotFound - productID not in db");
        log.info("@Test 1 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test2_findById_success_product() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id,
                HttpMethod.GET,null, String.class);

        log.info("@Test 2 - findById_success - product ");
        log.info("@Test 2 - Response Body: " + response.getBody());
        assertResponseBody(response, product);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test3_findById_success_productVariant() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + productVariant.id,
                HttpMethod.GET,null, String.class);

        log.info("@Test 3 - findById_success - product variant");
        log.info("@Test 3 - Response Body: " + response.getBody());
        assertResponseBody(response, productVariant);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test4_updateProduct_badRequest_emptyJSON() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id,
                HttpMethod.PUT,new HttpEntity<>("{}", header), String.class);

        log.info("@Test 4 - updateProduct_badRequest - json string is empty - nothing to update");
        log.info("@Test 4 - Response Body: " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test5_updateProduct_resourceNotFound() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + "0",
                HttpMethod.PUT,new HttpEntity<>(jsonStringCorrect, header), String.class);

        log.info("@Test 5 - updateProduct_resourceNotFound - productID not in db");
        log.info("@Test 5 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test6_updateProduct_success_fullUpdate() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id,
                HttpMethod.PUT,new HttpEntity<>(jsonStringCorrect, header), String.class);

        log.info("@Test 6 - updateProduct_success - name, comment and resources of product are updated");
        log.info("@Test 6 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: test output against original entity
    }

    @Test
    public void test7_updateProduct_success_partialUpdate_MissingName() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id,
                HttpMethod.PUT,new HttpEntity<>(jsonStringMissingName, header), String.class);

        log.info("@Test 7 - updateProduct_success - comment and resources of product are updated");
        log.info("@Test 7 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: test output against original entity
    }

    @Test
    public void test8_updateProduct_success_partialUpdate_MissingComment() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id,
                HttpMethod.PUT,new HttpEntity<>(jsonStringMissingComment, header), String.class);

        log.info("@Test 8 - updateProduct_success - name and resources of product are updated");
        log.info("@Test 8 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: test output against original entity
    }

    @Test
    public void test9_updateProduct_success_partialUpdate_MissingResources() {
        ResponseEntity<String> response = restTemplate.exchange(host + port + products + product.id,
                HttpMethod.PUT,new HttpEntity<>(jsonStringMissingResources, header), String.class);

        log.info("@Test 9 - updateProduct_success - name and comment of product are updated");
        log.info("@Test 9 - Response Body: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //TODO: test output against original entity
    }
}
