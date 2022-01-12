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

    private ProductDto emptyProductDto;
    private ProductDto tmpProductDto1;
    private ProductDto updateDto;
    private ProductDto preProductDto;
    private ProductDto fullProductDto;
    private ProductDto productDtoVariation;

    private ProductAreaDto fullProductAreaDto;
    private ProductAreaDto preProductAreaDto;

    private ProductEntity preProductEntity;

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

        tmpProductDto1 = new ProductDto();
        tmpProductDto1.productName = "Swaps";

        projectEntity = new ProjectEntity();
        projectEntity.id = 1;
        projectEntity.name = "DKB";

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
        preProductEntity = new ProductEntity();
        preProductEntity.id = 42;
        preProductEntity.name = "preProduct";
        preProductEntity.productarea = preProductAreaEntity;
        preProductEntity.project = preProjectEntity;

        preProductAreaEntities = new ArrayList<>();
        preProductAreaEntities.add(preProductAreaEntity);

        preProductAreaDto = new ProductAreaDto(
                42,
                "examplePreAreaName",
                "examplePreCat"
        );

        preProductDto = new ProductDto();
        preProductDto.productName = "preProduct";
        preProductDto.productID = 42;
        preProductDto.projectID = 1;
        preProductDto.productArea = preProductAreaDto;

        fullProductAreaDto = new ProductAreaDto(1, "exampleAreaName", "exampleCat");

        fullProductDto = new ProductDto();
        fullProductDto.productName = "exampleProduct";
        fullProductDto.productID = 1;
        fullProductDto.productArea = fullProductAreaDto;
        fullProductDto.projectID = 1;

        productDtoVariation = new ProductDto();
        productDtoVariation.productName = "exampleProductVariation";
        productDtoVariation.productID = 1;
        productDtoVariation.productArea = fullProductAreaDto;
        productDtoVariation.projectID = 1;

        updateDto = new ProductDto();
        updateDto.productName = "updatedDto";
        updateDto.productID = 111;
        updateDto.productArea = preProductAreaDto;
        updateDto.projectID = 1;

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
        when(repository.findById(preProductDto.productID)).thenReturn(Optional.of(preProductEntity));

        // Step 2: execute findById()
        ProductDto out = service.findById(preProductDto.productID);

        assertAll("find Product",
                () -> assertEquals(preProductDto.productID, out.productID),
                () -> assertEquals(preProductDto.productName, out.productName),
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
     * testCreateProduct7: input contains full Product
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
    public void testCreateProduct3_projectNameMissing() {
        // Step 0: init test object
        fullProductDto.productName = null;

        // Step 1: provide knowledge
        when(repository.existsByProjectAndProductarea(
                projectRepository.getById(1),
                productAreaRepository.getById(1))).thenReturn(true);

        // Step 2: execute and assert createProduct()
        assertNull(service.createProduct(1, fullProductDto));
    }

    @Test
    public void testCreateProduct4_withoutProductVariations() {

        // Step 1: provide knowledge

        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(repository.existsByProjectAndProductarea(
                projectRepository.getById(1),
                productAreaRepository.getById(1))).thenReturn(true);



        // Step 2: execute and assert createProduct()
        List<ProductDto> out = service.wrapper_createProduct(1, fullProductDto);

        List<ProductDto> emptyList = new ArrayList<>();
        assertNotEquals(emptyList, out);

        for(ProductDto productDtoOut : out) {
            assertAll("create Product",
                    () -> assertNotNull(productDtoOut.productID),
                    () -> assertEquals(fullProductDto.productID, productDtoOut.productID),
                    () -> assertEquals(fullProductDto.productName, productDtoOut.productName),
                    () -> assertEquals(fullProductDto.projectID, productDtoOut.projectID),
                    () -> assertEquals(fullProductDto.productArea.id, productDtoOut.productArea.id),
                    () -> assertEquals(fullProductDto.productVariations, productDtoOut.productVariations),
                    () -> assertNull(productDtoOut.ratings)
            );
        }
    }


    @Test
    public void testCreateProduct5_withProductVariations() {

        // Step 1: provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(repository.existsByProjectAndProductarea(
                projectRepository.getById(1),
                productAreaRepository.getById(1))).thenReturn(true);

        fullProductDto.productVariations = new ArrayList<>();
        fullProductDto.productVariations.add(productDtoVariation);

        // Step 2: execute and assert createProduct()
        List<ProductDto> out = service.wrapper_createProduct(1, fullProductDto);

        List<ProductDto> emptyList = new ArrayList<>();
        assertNotEquals(emptyList, out);

       for(ProductDto productDtoOut : out) {
           assertAll("create Product",
                   () -> assertNotNull(productDtoOut.productID),
                   () -> assertEquals(fullProductDto.productName, productDtoOut.productName),
                   () -> assertEquals(1, productDtoOut.projectID),
                   () -> assertEquals(1, productDtoOut.productArea.id),
                   () -> assertEquals(fullProductDto.productVariations, productDtoOut.productVariations),
                   () -> assertNull(productDtoOut.ratings)
           );
       }
    }

    //TODO: implement when multiple creations is posible
    @Test
    @Disabled
    public void testCreateMultipleProducts1_withVariations(){

        // Step 1: provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(repository.existsByProjectAndProductarea(
                projectRepository.getById(1),
                productAreaRepository.getById(1))).thenReturn(true);


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

        // Step 2: execute and assert createProduct()
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

        // Step 2: execute and assert createProduct()
        assertNull(service.updateById(emptyProductDto, 1).productName);
    }

    @Test
    public void testUpdateById3_success() {

        // Step 1: provide knowledge
        when(repository.existsById(42)).thenReturn(true);
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        // Step 2: execute and assert createProduct()
        preProductDto.productName = "updatedName";
        preProductDto.comment = "updateComment";
        ProductDto out = service.updateById(preProductDto, 42);

        assertEquals("updatedName", out.productName);
        assertEquals("updateComment", out.comment);
    }


    @Test
    public void testUpdateById4_updateComment_success(){

        // Step 1: provide knowledge
        when(repository.existsById(42)).thenReturn(true);
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        preProductDto.comment = "updateComment";
        preProductDto.productName = null;

        // Step 2: execute and assert createProduct()
        ProductDto out = service.updateById(preProductDto, 42);


        //TODO: klären ob einzelne updates von Name und comment möglich sein sollen oder jewails nur als ganzes bztw als löschen
        assertAll("update Product",
                () -> assertNotNull(out.productID),
                //() -> assertEquals(preProductDto.productName, out.productName),
                () -> assertEquals("updateComment", out.comment),
                () -> assertEquals(1, out.projectID),
                () -> assertEquals(42, out.productArea.id),
                () -> assertNull(out.ratings));


    }

    @Test
    public void testUpdateById4_updateProductName_success(){

        // Step 1: provide knowledge
        when(repository.existsById(42)).thenReturn(true);
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        preProductDto.productName = "updatedName";
        preProductDto.comment = null;

        // Step 2: execute and assert createProduct()
        ProductDto out = service.updateById(preProductDto, 42);

        //TODO: klären ob einzelne updates von Name und comment möglich sein sollen oder jewails nur als ganzes bztw als löschen
        assertAll("update Product",
                () -> assertNotNull(out.productID),
                () -> assertEquals(preProductDto.productName, out.productName),
                //() -> assertEquals("updateComment", out.comment),
                () -> assertEquals(1, out.projectID),
                () -> assertEquals(42, out.productArea.id),
                () -> assertNull(out.ratings));
    }

    @Test
    public void testGetProductsByProjectId_IdNotFound(){

        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.getProductsByProjectId(404));

        String expectedMessage = "project 404 does not exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @Disabled
    public void testGetProductsByProjectId_succsess(){

        //step 1: provide knowledge
        //TODO: find out how to create an Iterable
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(preProductEntity);

        when(projectRepository.existsById(preProjectEntity.id)).thenReturn(true);
        when(repository.findByProject(projectRepository.findById(preProjectEntity.id).get())).thenReturn(productEntities);

        List<ProductDto> out = service.getProductsByProjectId(preProjectEntity.id);

        assertEquals(productEntities, out);
    }

    @Test
    //TODO: Discuss getProductsByProjectIdAndProductAreaId
    public void testGetProductsByProjectIdAndProductAreaId_NotFound() {

        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.getProductsByProjectIdAndProductAreaId(404, 404));

        String expectedMessage = "project 404 does not exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

}
