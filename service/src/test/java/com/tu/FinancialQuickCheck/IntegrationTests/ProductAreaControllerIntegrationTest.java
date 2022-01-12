package com.tu.FinancialQuickCheck.IntegrationTests;

import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductAreaControllerIntegrationTest {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    private ProductAreaRepository repository;

    private String host = "http://localhost:";
    private String productAreas = "/productareas";
    private String initProductArea = "{\"category\":\"PRIVAT\", \"name\":\"KREDIT\"}";


    @BeforeEach
    public void init(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(initProductArea, headers);

        String responseBody = restTemplate.exchange(
                host + port + productAreas,
                HttpMethod.POST,
                request,
                String.class).getBody();

        System.out.println("Init: " + responseBody);
    }


    @Test
    public void getProductAreas400() throws Exception {
        // delete all existing productAreas
        // TODO: wie können wir die Daten löschen ohne HHTP.Delete?

        ResponseEntity<String> response = restTemplate.exchange(
                host + port + productAreas,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        System.out.println("Response Status: " + response.getStatusCode());
    }

    @Test
    public void getProductAreas200() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                host + port + productAreas,
                HttpMethod.GET,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("Response Status: " + response.getStatusCode());
    }

    @Test
    @Disabled
    public void postProductAreas201(){
        // TODO: Implement
    }

    @Test
    @Disabled
    public void postProductAreas400(){
        // TODO: Implement BAD Requeest
    }


    @Test
    public void notAllowedMethodsProductAreas() throws Exception {

        HttpMethod[] notAllowed = new HttpMethod[]{HttpMethod.PUT, HttpMethod.DELETE};
        for (HttpMethod httpMethod : notAllowed) {
            ResponseEntity<String> response = restTemplate.exchange(
                    host + port + productAreas,
                    httpMethod,
                    null,
                    String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//            System.out.println("Response Status: " + response.getStatusCode());
        }
    }

}