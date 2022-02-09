package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.ProductAreaController;
import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductAreaControllerTest {

    static Logger log = Logger.getLogger(ProductAreaControllerTest.class.getName());

    @Mock
    private ProductAreaService service;

    private ProductAreaController controller;

    private ProductAreaDto dto1;
    private ProductAreaDto dto2;
    private List<ProductAreaDto> listDtos;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductAreaControllerTest.class");

        controller = new ProductAreaController(service);

        String name1 = "Kredit";
        String name2 = "Payment";
        String category1 = "Privat";
        String category2 = "Business";

        dto1 = new ProductAreaDto();
        dto1.name = name1;
        dto1.category = category1;

        dto2 = new ProductAreaDto();
        dto2.name = name2;
        dto2.category = category2;

        listDtos = new ArrayList<>();
    }


    /**
     * Tests for findALL()
     *
     * test_findALL_resourceNotFound: no productAreas exist in db --> return ResourceNotFound
     * test_findALL_resourcesExist: productAreas exist in db --> return List<ProductAreaDto>
     */
    @Test
    public void test_findALL_resourceNotFound() {
        // Step 1: provide knowledge
        when(service.getAllProductAreas()).thenReturn(new ArrayList<>());

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.findALL());

        String expectedMessage = "No Product Areas found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_findALL_resourceExist() {
        listDtos.add(dto1);
        listDtos.add(dto2);

        // Step 1: provide knowledge
        when(service.getAllProductAreas()).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<ProductAreaDto> out = controller.findALL();

        assertEquals(out.size(), listDtos.size());
    }

    /**
     * Tests for createProductArea()
     *
     * test_createProductArea_badRequest: name and category missing --> return BadRequest
     * test_createProductArea_success: input correct --> return created product area
     */
    @Test
    public void test_createProductArea_badRequest(){
        ProductAreaDto emptyDto = new ProductAreaDto();

        // Step 1: provide knowledge
        when(service.createProductArea(emptyDto)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.createProductArea(emptyDto));

        String expectedMessage = "ProductArea cannot be created due to missing information.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_createProductArea_success() {
        // Step 1: provide knowledge
        when(service.createProductArea(dto1)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProductAreaDto out = controller.createProductArea(dto1);

        assertAll("create productAreas",
                () -> assertNotNull(out.name),
                () -> assertNotNull(out.category));
    }

}
