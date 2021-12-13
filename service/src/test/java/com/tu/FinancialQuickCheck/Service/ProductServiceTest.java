package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.logging.Logger;

import static com.tu.FinancialQuickCheck.Role.PROJECT_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    static Logger log = Logger.getLogger(ProductServiceTest.class.getName());

    @Mock
    ProductRepository repository;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProductAreaRepository productAreaRepository;

    private ProductService service;

    private String name1;
    private String name2;
    private int productID;

    private ProductDto emptyDto;
    private ProductDto dto1;
    private ProductDto dto2;
    private ProductDto dto3;
    private ProductDto updateDto;

    private ProductEntity entity1;
    private ProductEntity entity2;
    private ProductEntity entity3;

    private ProjectEntity projectEntity;


    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductServiceTest.class");
        // init ProjectService
        service = new ProductService(repository, projectRepository, productAreaRepository);
        // init empty test object
        emptyDto = new ProductDto();
        // init necessary information for test objects
        productID = 1;
        name1 = "Swaps";
        name2 = "Optionen";

        dto1 = new ProductDto();
        dto1.productName = name1;

        dto2 = new ProductDto();
        dto2.productName = name2;
        dto2.productVariations = new ArrayList<>();
        dto2.productVariations.add(dto1);

        updateDto = new ProductDto();
        updateDto.productName = name2;

        projectEntity = new ProjectEntity();
        projectEntity.id = 1;
        projectEntity.name = "DKB";

        entity1 = new ProductEntity();
//        entity1.product_id = productID;
        entity1.name = name1;
        entity1.project = projectEntity;
        //TODO: anpassen
//        entity1.productareaid = 1;




    }


    /**
     * tests for findById()
     *
     * testFindById1: productID doesnt exist --> throw ResourceNotFound Exception
     * testFindById2: productID exists --> return List<ProductDto>
     */
    @Test
    public void testFindById1_productNotFound() {
        Exception exception = assertThrows(ResourceNotFound.class, () -> service.findById(1));

        String expectedMessage = "productID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindById2_productExists() {
        // Step 1: provide knowledge
        when(repository.findById(productID)).thenReturn(Optional.of(entity1));

        // Step 2: execute findById()
        ProductDto out = service.findById(productID);

        assertAll("find Product",
                () -> assertEquals(productID, out.productID),
                () -> assertEquals(name1, out.productName),
                () -> assertEquals(entity1.project.id, out.projectID),
                //TODO: anpassen
//                () -> assertEquals(entity1.productareaid, out.productAreaID),
                () -> assertNull(out.ratings),
                () -> assertNull(out.productVariations)
        );
    }


    /**
     * tests for createProduct()
     *
     * testCreateProduct1: projectID does not exist -> throw ResourseNotFound Exception
     * testCreateProduct2: projectID exists, productAreaID does not exist -> throw ResourseNotFound Exception
     * testCreateProduct3: projectName missing -> return null
     * testCreateProduct4: input without productVariations
     *                     --> return ProductDto without productVariations
     * testCreateProduct5: input with productVariations
     *                     --> return ProductDto with productVariations
     * testCreateProduct6: input contains more than required information
     *                      --> product is created correctly, productID returned and additional information is ignored
     */
    @Test
    public void testCreateProduct1_projectIdNotFound() {
        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(false);

        // Step 2: execute and assert creaateProduct()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProduct(1, dto1));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testCreateProduct2_productAreaIdNotFound() {
        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(false);

        // Step 2: execute and assert creaateProduct()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProduct(1, dto1));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateProduct3_projectNameMissing() {
        // Step 0: init test object
        dto1.productName = null;

        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(true);

        // Step 2: execute and assert creaateProduct()
        assertNull(service.createProduct(1, dto1));
    }

    @Test
    public void testCreateProduct4_withoutProductVariations() {
        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(true);
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));

        // Step 2: execute and assert creaateProduct()
        List<ProductDto> out = service.wrapper_createProduct(1, dto1);

        //TODO: anpassen auf List output
//        assertAll("create Product",
//                () -> assertNotNull(out.productID),
//                () -> assertEquals(dto1.productName, out.productName),
//                () -> assertEquals(1, out.projectID),
//                //TODO: anpassen
////                () -> assertEquals(1, out.productAreaID),
//                () -> assertNull(out.productVariations),
//                () -> assertNull(out.ratings)
//        );
    }

    @Test
    @Disabled
    public void testCreateProduct5_withProductVariations() {
        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(true);
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));

        // Step 2: execute and assert creaateProduct()
        List<ProductDto> out = service.wrapper_createProduct(1, dto2);

        //TODO: anpassen auf List output
//        assertAll("create Product",
//                () -> assertNotNull(out.productID),
//                () -> assertEquals(dto2.productName, out.productName),
//                () -> assertEquals(1, out.projectID),
//                //TODO: anpassen
////                () -> assertEquals(1, out.productAreaID),
//                () -> assertEquals(dto2.productVariations, out.productVariations),
//                () -> assertNull(out.ratings)
//        );
    }

    /**
     * tests for updateById()
     *
     * testUpdateById1: productID does not exist -> throw ResourseNotFound Exception
     * testUpdateById2: productID exists, input missing -> return null
     * testUpdateById3: correct input -> attributes changed according to input
     */
    @Test
    public void testUpdateById1_productIdNotFound() {
        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(false);

        // Step 2: execute and assert creaateProduct()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.updateById(dto1, 1));

        String expectedMessage = "productID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testUpdateById2_inputMissing() {
        // Step 0: init test object
        dto1.productName = null;

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);

        // Step 2: execute and assert creaateProduct()
        assertNull(service.updateById(dto1, 1));
    }

    @Test
    public void testUpdateById3_success() {
        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity1));

        // Step 2: execute and assert creaateProduct()
        ProductDto out = service.updateById(updateDto, 1);
        //TODO: output evtl. anpassen
//        assertEquals(updateDto.productName, out.productName);
    }


}
