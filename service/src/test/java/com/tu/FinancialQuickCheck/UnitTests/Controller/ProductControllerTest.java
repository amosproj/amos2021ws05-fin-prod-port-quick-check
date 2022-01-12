package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.ProductAreaController;
import com.tu.FinancialQuickCheck.Controller.ProductController;
import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    static Logger log = Logger.getLogger(ProductControllerTest.class.getName());

    @Mock
    private ProductService service;

    private ProductController controller;

    private ProductDto dto1;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductControllerTest.class");

        controller = new ProductController(service);

        String name1 = "Produkt 1";
        String comment1 = "comment1";
        List resources = new ArrayList<>();

        dto1 = new ProductDto();
        dto1.productName = name1;
        dto1.comment = comment1;
        dto1.resources = resources;
    }

    @Test
    public void testFindById_resourceNotFound() {
        int productId = 1;

        // Step 1: provide knowledge
        when(service.findById(productId)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.findById(productId));

        String expectedMessage = "productID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindById_resourceExists() {
        int productId = 1;

        // Step 1: provide knowledge
        when(service.findById(productId)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProductDto out = controller.findById(productId);

        assertAll("find product by id",
                () -> assertNotNull(out.productName),
                () -> assertNotNull(out.comment),
                () -> assertNotNull(out.resources));
    }

    @Test
    public void testUpdateProduct_resourceNotFound() {
        int productId = 1;

        // Step 1: provide knowledge
        when(service.updateById(dto1, productId)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.updateProduct(dto1, productId));

        String expectedMessage = "productID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateProduct_badRequest() {
        int productId = 1;

        // Step 1: execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.updateProduct(new ProductDto(), productId));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
