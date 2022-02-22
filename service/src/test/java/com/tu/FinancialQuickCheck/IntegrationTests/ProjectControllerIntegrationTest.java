package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.Role;
import com.tu.FinancialQuickCheck.db.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private String host = "http://localhost:";
    private String projects = "/projects";
    private String projectIDExistsNot = "/0";
    private String projectID1 = "/1";

    HttpHeaders header = new HttpHeaders();

    private String jsonStringEmpty = "{}";
    private String jsonStringIncorrectFormat = "{\"category\":\"PRIVAT\", \"name\":\"KREDIT\"}";
    private String jsonStringIncorrectData =
            "{" +
            "\"projectID\":\"66\"," +
            " \"creatorID\":\"666\", " +
            "\"projectName:\" : \"Test Project\"," +
            "\"members\" : [404]" +
            "\"productAreas\" : [404]" +
            "}";
    private String jsonStringCorrect;
    private String jsonStringUpdateCorrect;
    private String jsonStringProductCorrect;



    private ProductAreaEntity tmpArea;
    private ProductEntity tmpProduct;
    private ProductRatingEntity tmpProductRating;
    private RatingEntity tmpRating;
    private ProductRatingId tmpRatingID;
    private UserEntity tmpUser;
    private UserEntity tmpUpdatedUser;
    private ProjectUserEntity tmpProjectUser;
    private ProjectUserId tmpUserID;
    private ProjectEntity tmp;

    private ProductAreaEntity afterAreaEntity;
    private ProjectEntity afterEntity;
    private ProductEntity afterProductEntity;



    @BeforeEach
    public void init(){

        productAreaRepository.deleteAll();
        projectUserRepository.deleteAll();
        userRepository.deleteAll();
        repository.deleteAll();
        productRepository.deleteAll();

        header.setContentType(MediaType.APPLICATION_JSON);

        //create Product Area
        tmpArea = new ProductAreaEntity();
        tmpArea.id = 1;
        tmpArea.name = "KREDIT";
        tmpArea.category = "PRIVAT";

        productAreaRepository.save(tmpArea);

        List<ProductAreaEntity> tmpAreout = productAreaRepository.findAll();
        afterAreaEntity = new ProductAreaEntity();
        afterAreaEntity.id = tmpAreout.get(0).id;
        afterAreaEntity.name = tmpAreout.get(0).name;
        afterAreaEntity.category = tmpAreout.get(0).category;

        //create Project
        tmp = new ProjectEntity();
        tmp.id = 1;
        tmp.name = "exampleProject";
        tmp.creatorID = UUID.randomUUID().toString();
        //tmp.productEntities = tmpProductList;

        repository.save(tmp);

        List<ProjectEntity> out = repository.findAll();
        afterEntity = new ProjectEntity();
        afterEntity.id = out.get(0).id;
        afterEntity.name = out.get(0).name;
        afterEntity.creatorID = out.get(0).creatorID;
        afterEntity.productEntities = out.get(0).productEntities;

        //create Product
        /**tmpProduct = new ProductEntity();
        //tmpProduct.id = 1;
        tmpProduct.name = "exampleProduct";
        tmpProduct.productarea = tmpArea;
        tmpProduct.comment = "exampleProductComment";
        tmpProduct.project = tmp;
        //tmpProduct.productRatingEntities = tmpRatingList;

        List<ProductEntity> tmpProductList = new ArrayList<>();
        tmpProductList.add(tmpProduct);

        productRepository.save(tmpProduct);

        List<ProductEntity> tmpProductOut = productRepository.findAll();
        afterProductEntity = new ProductEntity();
        afterProductEntity.id = tmpProductOut.get(0).id;
        afterProductEntity.name = tmpProductOut.get(0).name;
        afterProductEntity.parentProduct = tmpProductOut.get(0).parentProduct;
        afterProductEntity.productarea = tmpProductOut.get(0).productarea;
        afterProductEntity.comment = tmpProductOut.get(0).comment;

        //create Ratings
        tmpRating = new RatingEntity();

        ratingRepository.save(tmpRating);

        //create ratingID
        tmpRatingID = new ProductRatingId();
        tmpRatingID.setProduct(tmpProduct);
        tmpRatingID.setRating(tmpRating);

        //create Product Ratings
        tmpProductRating = new ProductRatingEntity();
        tmpProductRating.productRatingId = tmpRatingID;
        tmpProductRating.comment = "exampleProductRatingComment";
        tmpProductRating.score = null;

        List<ProductRatingEntity> tmpRatingList = new ArrayList<>();
        tmpRatingList.add(tmpProductRating);

        productRatingRepository.save(tmpProductRating);**/


        //create User 1
        tmpUser = new UserEntity();

        tmpUser.id = "185fd119-ac2a-42ab-a1bf-8a891003ab0e";
        tmpUser.username = "exampleUsername";
        tmpUser.email = "exampleUsername@web.de";

        //create User 2
        tmpUpdatedUser = new UserEntity();

        tmpUpdatedUser.id = "185fd119-ac2a-42ab-a1bf-8a891003ab0e";
        tmpUpdatedUser.username = "updatedUsername";
        tmpUpdatedUser.email = "updatedUsername@web.de";

        userRepository.save(tmpUser);
        //userRepository.save(tmpUpdatedUser);

        //create Project User
        tmpProjectUser = new ProjectUserEntity();
        tmpProjectUser.projectUserId = new ProjectUserId();
        tmpProjectUser.projectUserId.setUser(tmpUser);
        tmpProjectUser.projectUserId.setProject(tmp);
        tmpProjectUser.role = Role.CLIENT;

        //projectUserRepository.save(tmpProjectUser);

        jsonStringCorrect = "{" +
                "\"projectID\" : \"66\"," +
                "\"creatorID\" : \"7bc6cbbf-5455-4c8e-946c-495ee8a993e5\"," +
                "\"projectName\" : \"TestProject\"," +
                "\"productAreas\" : [{" +
                "\"id\" : " + afterAreaEntity.id + "," +
                "\"name\" : \"KREDIT\"," +
                "\"category\" : \"PRIVAT\"" +
                "}]," +
                "\"members\": [{" +
                "\"userID\" : \"185fd119-ac2a-42ab-a1bf-8a891003ab0e\"," +
                "\"userEmail\" : \"exampleUsername@web.de\"," +
                "\"userName\" : \"exampleUser\"," +
                "\"role\" : \"CLIENT\"" +
                "}]" +
                "}";

        jsonStringUpdateCorrect =
                "{" +
                        "\"projectID\" : \"" + afterEntity.id + "\"," +
                        "\"creatorID\" : \"" + afterEntity.creatorID + "\"," +
                        "\"projectName\" : \"Updated Project\"," +
                        "\"productAreas\" : [{" +
                        "\"id\" : " + afterAreaEntity.id + "," +
                        "\"name\" : \"KREDIT\"," +
                        "\"category\" : \"PRIVAT\"" +
                        "}]," +
                        "\"members\": [{" +
                        "\"userID\" : \"185fd119-ac2a-42ab-a1bf-8a891003ab0e\"," +
                        "\"userEmail\" : \"exampleUsername@web.de\"," +
                        "\"userName\" : \"exampleUser\"," +
                        "\"role\" : \"CLIENT\"" +
                        "}]" +
                        "}";

        jsonStringProductCorrect =
                "{" +
                        "\"productID\" : 42," +
                        "\"productName\" : \"exampleProduct\"," +
                        "\"productArea\" : {\"id\": " + afterAreaEntity.id + "}," +
                        "\"projectID\" : \"" + afterEntity.id + "\"" +
                        "}";

    }

    @AfterEach
    public void reset(){
        productAreaRepository.deleteAll();
        projectUserRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.deleteAll();
        productRatingRepository.deleteAll();
        productRepository.deleteAll();
        ratingRepository.deleteAll();
        repository.deleteAll(repository.findAll());
    }

    @Test
    public void testPOSTCreateProject_1_emptyBody(){

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void testPOSTCreateProject_2_emptyJson(){

        HttpEntity<String> request = new HttpEntity<>(jsonStringEmpty, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTCreateProject_3_wrongRequestBody(){

        HttpEntity<String> request = new HttpEntity<>(jsonStringIncorrectFormat, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTCreateProject_4_wrongData(){

        HttpEntity<String> request = new HttpEntity<>(jsonStringIncorrectData, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Disabled("Fix testcase")
    public void testPOSTCreateProject_5_success(){
        HttpEntity<String> request = new HttpEntity<>(jsonStringCorrect, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    @Disabled("Fix testcase")
    public void testGETFindAll_6_emptyRepo() {
        productRatingRepository.deleteAll();
        productRepository.deleteAll();
        productAreaRepository.deleteAll();
        projectUserRepository.deleteAll();
        userRepository.deleteAll();
        repository.deleteAll();

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getBody()).isEqualTo("[]");

    }

    @Test
    @Disabled("Fix testcase")
    public void testGETFindAll_7_success() {

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getBody()).isEqualTo("[{\"projectID\":" + afterEntity.id +",\"projectName\":\"exampleProject\"}]");

    }

    @Test
    @Disabled("Fix testcase")
    public void testGetFindByID_8_emptyRepo() {

        productAreaRepository.deleteAll();
        projectUserRepository.deleteAll();
        userRepository.deleteAll();
        repository.deleteAll();

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/1",
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testGETFindByID_9_IdNotExisting() {

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/404",
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testGETFindByID_10_IdExisting() {

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/" + afterEntity.id,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGETFindByID_11_IdWrongFormat() {

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/zweulf",
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPUTUpdateByID_12_incorrectJSON() {

        HttpEntity<String> request = new HttpEntity<>(jsonStringIncorrectFormat, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPUTUpdateByID_13_areaNotFound() {

        String jsonString = "{" +
                "\"projectID\" : \"66\"," +
                "\"creatorID\" : \"7bc6cbbf-5455-4c8e-946c-495ee8a993e5\"," +
                "\"projectName\" : \"TestProject\"," +
                "\"productAreas\" : [{" +
                "\"id\" : " + 404 + "," +
                "\"name\" : \"KREDIT\"," +
                "\"category\" : \"PRIVAT\"" +
                "}]," +
                "\"members\": [{" +
                "\"userID\" : \"185fd119-ac2a-42ab-a1bf-8a891003ab0e\"," +
                "\"userEmail\" : \"exampleUsername@web.de\"," +
                "\"userName\" : \"exampleUser\"," +
                "\"role\" : \"CLIENT\"" +
                "}]" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(jsonString, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects,
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Disabled("fix testcase")
    public void testPUTUpdateByID_14_success() {

        HttpEntity<String> request = new HttpEntity<>(jsonStringUpdateCorrect, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/" + afterEntity.id,
                HttpMethod.PUT,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Disabled("initialisierung von Producten hinbekommen dann Tests schrieben")
    public void testGETFindProductsByProject(){

    }

    @Test
    @Disabled("fix testcase")
    public void testPOSTCreateProduct() {

        HttpEntity<String> request = new HttpEntity<>(jsonStringProductCorrect, header);

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + projects + "/" + afterEntity.id + "/products",
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    @Disabled("Implement")
    public void testPOSTCreateProjectUser() {

    }


}