package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.util.*;
import java.util.logging.Logger;

import static com.tu.FinancialQuickCheck.Role.PROJECT_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    static Logger log = Logger.getLogger(ProductServiceTest.class.getName());

    @Mock
    ProductRepository repository;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProductAreaRepository productAreaRepository;

    private ProductService service;

    private String productName1;
    private String productName2;
    private String preProductName;
    private String updateComment;
    private int productID;
    private int preProductId;

    private ProductDto emptyProductDto;
    private ProductDto tmpProductDto1;
    private ProductDto dto2;
    private ProductDto dto3;
    private ProductDto updateDto;
    private ProductDto preProductDto;
    private ProductDto fullProductDto;
    private ProductDto productDtoVariation;

    private ProductAreaDto fullProductAreaDto;
    private ProductAreaDto preProductAreaDto;

    private ProductEntity emptyProductEntity;
    private ProductEntity preProductEntity;
    private ProductEntity entity1;
    private ProductEntity entity2;
    private ProductEntity entity3;

    private ProjectEntity projectEntity;
    private ProjectEntity preProjectEntity;

    private List<ProjectEntity> preProjectEntities;
    private List<ProductAreaEntity> preProductAreaEntities;

    private ProductAreaEntity preProductAreaEntity;


    @BeforeEach
    public void init() {

        log.info("@BeforeEach - setup for Tests in ProductServiceTest.class");

        // init ProjectService
        service = new ProductService(repository, projectRepository, productAreaRepository);
        // init empty test object
        emptyProductDto = new ProductDto();
        // init necessary information for test objects
        productID = 1;
        productName1 = "Swaps";
        productName2 = "Optionen";
        updateComment = "updatedComment";

        //dto1 with name only
        tmpProductDto1 = new ProductDto();
        tmpProductDto1.productName = productName1;

        //dto2 with name and variation
        dto2 = new ProductDto();
        dto2.productName = productName2;
        dto2.productVariations = new ArrayList<>();
        dto2.productVariations.add(tmpProductDto1);

        updateDto = new ProductDto();
        updateDto.productName = productName2;
        updateDto.comment = updateComment;

        projectEntity = new ProjectEntity();
        projectEntity.id = 1;
        projectEntity.name = "DKB";

        entity1 = new ProductEntity();
//        entity1.product_id = productID;
        entity1.name = productName1;
        entity1.project = projectEntity;
        //TODO: anpassen
//        entity1.productareaid = 1;


        List<ProductEntity> emptyProductEntitiesList = new ArrayList<>();
        List<ProjectUserEntity> emptyProjectUserEntitiesList = new ArrayList<>();

        //create preProject for Testing
        preProjectEntity = new ProjectEntity();
        preProjectEntity.id = 1;
        preProjectEntity.creatorID = "74c31a72-7c9e-4095-a1d6-ec208b57ff1a";
        preProjectEntity.name = "Bank4";
        preProjectEntity.productEntities = emptyProductEntitiesList;
        preProjectEntity.projectUserEntities = emptyProjectUserEntitiesList;

        preProjectEntities = new ArrayList<>();
        preProjectEntities.add(preProjectEntity);

        //create preProductArea for Testing
        preProductAreaEntity = new ProductAreaEntity();
        preProductAreaEntity.id = 1;
        preProductAreaEntity.category = "exampleCat";
        preProductAreaEntity.name = "exampleAreaName";

        //create preProduct for Testing
        preProductName = "preProduct";
        preProductId = 1;

        preProductEntity = new ProductEntity();
        preProductEntity.id = preProductId;
        preProductEntity.name = preProductName;
        preProductEntity.productarea = preProductAreaEntity;
        preProductEntity.project = preProjectEntity;

        preProductAreaEntities = new ArrayList<>();
        preProductAreaEntities.add(preProductAreaEntity);

        preProductDto = new ProductDto();
        preProductDto.productName = preProductName;
        preProductDto.productID = preProductId;

        fullProductAreaDto = new ProductAreaDto(1, "exampleAreaName", "exampleCat");

        fullProductDto = new ProductDto();
        fullProductDto.productName = "exampleProduct";
        fullProductDto.productID = 1;
        fullProductDto.productArea = fullProductAreaDto;
        fullProductDto.projectID = 1;

        productDtoVariation = fullProductDto;
        productDtoVariation.productName = "exampleProductVariation";


    }


    /**
     * tests for findById()
     *
     * testFindById1: productID doesnt exist --> throw ResourceNotFound Exception
     * testFindById2: productID exists --> return List<ProductDto>
     */
    @Test
    public void testFindById1_productNotFound() {

        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.findById(1));

        assertTrue(exception.getMessage().contains("productID 1 not found"));
    }

    @Test
    public void testFindById2_productExists() {

        // Step 1: provide knowledge
        when(repository.findById(preProductId)).thenReturn(Optional.of(preProductEntity));

        // Step 2: execute findById()
        ProductDto out = service.findById(preProductId);

        assertAll("find Product",
                () -> assertEquals(preProductId, out.productID),
                () -> assertEquals(preProductName, out.productName),
                () -> assertEquals(preProductEntity.project.id, out.projectID),
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

        // Step 1: execute and assert createProduct()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProduct(1, fullProductDto));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testCreateProduct2_productAreaIdNotFound() {

        // Step 1: provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.ofNullable(preProjectEntity));
        when(productAreaRepository.existsById(1)).thenReturn(false);

        // Step 2: execute and assert createProduct()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProduct(1, fullProductDto));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Disabled
    public void testCreateProduct3_projectNameMissing() {
        // Step 0: init test object
        fullProductDto.productName = null;

        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(true);
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));

        // Step 2: execute and assert createProduct()
        assertNull(service.createProduct(1, fullProductDto));
    }

    @Test
    @Disabled
    public void testCreateProduct4_withoutProductVariations() {

        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(true);
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.findById(1)).thenReturn(Optional.of(preProductAreaEntity));


        // Step 2: execute and assert createProduct()
        List<ProductDto> out = service.wrapper_createProduct(1, fullProductDto);
        ProductDto firstOut = out.get(0);
        System.out.println(out);
        //TODO: anpassen auf List output
        assertAll("create Product",
                () -> assertNotNull(firstOut),
                () -> assertEquals(fullProductDto.productName, firstOut.productName),
                () -> assertEquals(1, firstOut.projectID),
                //TODO: anpassen
                () -> assertNull(firstOut.productVariations),
                () -> assertNull(firstOut.ratings)
        );
    }



    @Test
    @Disabled
    //TODO: create working variations
    public void testCreateProduct5_withProductVariations() {
        // Step 1: provide knowledge
        when(projectRepository.existsById(1)).thenReturn(true);
        when(productAreaRepository.existsById(1)).thenReturn(true);
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));

        fullProductDto.productVariations.add(tmpProductDto1);

        // Step 2: execute and assert createProduct()
        List<ProductDto> out = service.wrapper_createProduct(1, dto2);

       for(ProductDto productDtoOut : out) {
           assertAll("create Product",
                   () -> assertNotNull(productDtoOut.productID),
                   () -> assertEquals(dto2.productName, productDtoOut.productName),
                   () -> assertEquals(1, productDtoOut.projectID),
                   //TODO: anpassen
                   () -> assertEquals(1, productDtoOut.productArea.id),
                   () -> assertEquals(dto2.productVariations, productDtoOut.productVariations),
                   () -> assertNull(productDtoOut.ratings)
           );
       }
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
                () -> service.updateById(tmpProductDto1, 1));

        String expectedMessage = "productID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testUpdateById2_inputMissing() {

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.ofNullable(preProductEntity));


        //TODO: add remaining parts check if an empty dt schuld be the result

        // Step 2: execute and assert creaateProduct()
        assertNull(service.updateById(emptyProductDto, 1).productName);
    }

    @Test
    @Disabled
    public void testUpdateById3_success() {
        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(preProductEntity));

        // Step 2: execute and assert creaateProduct()
        ProductDto out = service.updateById(updateDto, 1);
        //TODO: output evtl. anpassen
        assertEquals(updateDto.productName, out.productName);
        assertEquals(updateDto.comment, out.comment);
    }


}
