package com.tu.FinancialQuickCheck.Service;

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

    private String name1;
    private String name2;
    private String category1;
    private String category2;

    private ProductAreaDto dto1;
    private ProductAreaDto dto2;
    private ProductAreaDto emptyDto;

    private ProductAreaEntity productArea1;
    private ProductAreaEntity productArea2;
    private List<ProductAreaEntity> productAreas;

    /**
     * This method should be executed before each @test method in the current test class.
     */
    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductAreaServiceTest.class");

        service = new ProductAreaService(repository);

        name1 = "Kredit";
        name2 = "Payment";

        category1 = "Privat";
        category2 = "Business";

        dto1 = new ProductAreaDto();
        dto1.name = name1;
        dto1.category = category1;

        dto2 = new ProductAreaDto();
        dto2.name = name2;
        dto2.category = category2;

        emptyDto = new ProductAreaDto();

        productArea1 = new ProductAreaEntity();
        productArea1.name = name1;
        productArea1.category = category1;
        productArea2 = new ProductAreaEntity();
        productArea2.name = name2;
        productArea2.category = category2;

        productAreas = new ArrayList<>();
        productAreas.add(productArea1);
        productAreas.add(productArea2);
    }


    /**
     * Tests for returning all product areas.
     *
     * @result testGetAllProductAreas1: no productAreas exist --> return empty List<ProductAreaDto>
     * @result testGetAllProductAreas2: productAreas exist --> return List<ProductAreaDto>
     */
    @Test
    public void testGetAllProductAreas1() {
        // Step 1: init test object
        List productAreaEntities = Collections.EMPTY_LIST;

        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(productAreaEntities);

        // Step 3: execute getAllProductAreas()
        List<ProductAreaDto> projectsOut = service.getAllProductAreas();
        List<ProductAreaDto> expected = new ArrayList<>();

        assertEquals(expected, projectsOut);
    }

    /**
     * Tests for returning all projects.
     *
     *
     *
     */
    @Test
    public void testGetAllProjects2() {
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
     * Tests for creating a product area.
     *
     * @result testCreateProductArea1: input contains required information
     *                      --> productArea is created correctly and productAreaID returned
     * @result testCreateProductArea2: input missing required information
     *                      --> output == null
     * @result testCreateProductArea3: input contains more than required information
     *                      --> productArea is created correctly, productAreaID returned and
     *                      additional information is ignored
     */
    @Test
    public void testCreateProductArea1() {
        for(int i = 0; i <= 10; i++){
            // Step 1: execute createProductArea()
            log.info("@Test createProductArea() - test object : " + dto1.name);
            ProductAreaDto out = service.createProductArea(dto1);
            log.info("@Test createProductArea() - return object id : " + out.id);

            // Step 2: assert result
            assertAll("create productArea",
                    () -> assertEquals(dto1.name, out.name),
                    () -> assertEquals(dto1.category, out.category),
                    () -> assertNotNull(out)
            );
        }
    }


    @Test
    public void testCreateProject2() {
        // Step 1: init test object
        dto1.name = null;
        dto2.category = null;

        // Step 2 and 3: execute createProductArea and assert result
        assertNull(service.createProductArea(dto1));
        assertNull(service.createProductArea(dto2));
        assertNull(service.createProductArea(emptyDto));
    }


    @Test
    public void testCreateProject3() {
        for(int i = 1; i <= 11; i++){
            // Step 1: init test object
            dto1.id = i;

            // Step 2: execute createProductArea
            log.info("@Test createProductArea() - test object : " + dto1.name);
            ProductAreaDto out = service.createProductArea(dto1);
            log.info("@Test createProductArea() - return object id : " + out.id);

            // Step 3: assert result
            assertAll("create productArea",
                    () -> assertEquals(dto1.name, out.name),
                    () -> assertEquals(dto1.category, out.category),
                    () -> assertNotEquals(dto1.id, out.id)
            );
        }
    }

}
