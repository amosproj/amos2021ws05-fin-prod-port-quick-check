package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * The current test class verifies the functionalities of the Product Area Service
 */
@ExtendWith(MockitoExtension.class)
public class ProductAreaServiceTest {

    static Logger log = Logger.getLogger(ProductAreaServiceTest.class.getName());

    @Mock
    ProductAreaRepository repository;

    private ProductAreaService service;

    private ProductAreaDto dto1;
    private ProductAreaDto dto2;
    private ProductAreaDto emptyDto;

    private List<ProductAreaEntity> productAreas;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductAreaServiceTest.class");

        service = new ProductAreaService(repository);

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

        emptyDto = new ProductAreaDto();

        ProductAreaEntity productArea1 = new ProductAreaEntity();
        productArea1.name = name1;
        productArea1.category = category1;
        ProductAreaEntity productArea2 = new ProductAreaEntity();
        productArea2.name = name2;
        productArea2.category = category2;

        productAreas = new ArrayList<>();
        productAreas.add(productArea1);
        productAreas.add(productArea2);
    }


    /**
     * Tests for getAllProductAreas()
     *
     * test_getAllProductAreas: no productAreas exist --> return empty List<ProductAreaDto>
     * test_getAllProductAreas: productAreas exist --> return List<ProductAreaDto>
     */
    @Test
    public void test_getAllProductAreas_returnEmptyList() {
        // Step 1: init test object
        List<ProductAreaEntity> productAreaEntities = new ArrayList<>();

        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(productAreaEntities);

        // Step 3: execute getAllProductAreas()
        List<ProductAreaDto> projectsOut = service.getAllProductAreas();
        List<ProductAreaDto> expected = new ArrayList<>();

        assertEquals(expected, projectsOut);
    }

    @Test
    public void test_getAllProjects_returnProductAreas() {
        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(productAreas);

        // Step 3: execute getAllProductAreas()
        List<ProductAreaDto> productAreasOut = service.getAllProductAreas();

        productAreasOut.forEach(
                areas -> assertAll("get productAreas",
                        () -> assertNotNull(areas.name),
                        () -> assertNotNull(areas.category)
                )
        );

        assertThat(productAreasOut.size()).isGreaterThanOrEqualTo(2);
    }


    /**
     * Tests for createProductArea()
     *
     * testCreateProductArea: input contains required information
     *                      --> productArea is created correctly and productAreaID returned
     * testCreateProductArea: input missing required information
     *                      --> output == null
     */
    @Test
    public void test_createProductArea_returnCreatedProductArea() {
        for(int i = 0; i <= 10; i++){
            // Step 1: execute test method
            ProductAreaDto out = service.createProductArea(dto1);

            // Step 2: assert result
            assertAll("create productArea",
                    () -> assertEquals(dto1.name, out.name),
                    () -> assertEquals(dto1.category, out.category),
                    () -> assertNotNull(out)
            );
        }
    }

    @Test
    public void test_createProject_returnNull() {
        // Step 1: init test object
        dto1.name = null;
        dto2.category = null;

        // Step 2 and 3: execute test method and assert result
        assertNull(service.createProductArea(dto1));
        assertNull(service.createProductArea(dto2));
        assertNull(service.createProductArea(emptyDto));
    }

}
