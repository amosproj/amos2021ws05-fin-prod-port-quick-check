package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.ProductRatingController;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.ProductRatingService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductRatingControllerTest {

    static Logger log = Logger.getLogger(ProductRatingControllerTest.class.getName());

    @Mock
    private ProductRatingService service;

    private ProductRatingController controller;

    private ProductDto dto1;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductRatingControllerTest.class");

        controller = new ProductRatingController(service);

        dto1 = new ProductDto();
        dto1.productID = 1;
        
    }

    @Test
    public void testGetProductRatings_resourceNotFound() {
        int productID = 1;
        // Step 1: provide knowledge
        when(service.getProductRatings(productID, null)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.getProductRatings(productID, null));

        String expectedMessage = "productID 1 does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetProductRatings_resourceExists_ratingAreaNull() {
        int productID = 1;
        // Step 1: provide knowledge
        when(service.getProductRatings(productID, null)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProductDto out = controller.getProductRatings(productID, null);

        assertTrue(out.productID == productID);
    }

    @Test
    public void testGetProductRatings_resourceExists_ratingAreaNotNull() {
        List<RatingArea> ratingAreas = new ArrayList<>();
        ratingAreas.add(RatingArea.ECONOMIC);
        ratingAreas.add(RatingArea.COMPLEXITY);
        for(RatingArea r: ratingAreas){
            int productID = 1;
            // Step 1: provide knowledge
            when(service.getProductRatings(productID, r)).thenReturn(dto1);

            // Step 2: execute test method and assert
            ProductDto out = controller.getProductRatings(productID, r);

            assertTrue(out.productID == productID);
        }
    }

    @Test
    public void testUpdateProductRatings_resourceNotFound() {
        int productID = 1;
        // Step 1: provide knowledge
        when(service.updateProductRatings(dto1, productID)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.updateProductRatings(dto1, productID));

        String expectedMessage = "productID 1 does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateProductRatings_resourceExists() {
        int productID = 1;
        // Step 1: provide knowledge
        when(service.updateProductRatings(dto1, productID)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProductDto out = controller.updateProductRatings(dto1, productID);

        assertTrue(out.productID == productID);
    }

}
