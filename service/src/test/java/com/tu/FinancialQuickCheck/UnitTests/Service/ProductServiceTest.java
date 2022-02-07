package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.logging.Logger;

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
    @Mock
    RatingRepository ratingRepository;
    @Mock
    ProductRatingRepository productRatingRepository;

    private ProductService service;

    private ProductDto emptyProductDto;
    private ProductDto tmpProductDto1;
    private ProductDto preProductDto;
    private ProductDto fullProductDto;
    private ProductDto productDtoVariation;

    private ProductEntity preProductEntity;

    private ProjectEntity project;
    private ProjectEntity preProjectEntity;

    private ProductAreaEntity preProductAreaEntity;

    private ProductEntity entity;
    private List<RatingEntity> ratingEntities;
    private List<RatingEntity> complexityRatingEntities;
    private ProductDto createDto;
    private ProductDto createEmptyDto;

    @BeforeEach
    public void init() {

        log.info("@BeforeEach - setup for Tests in ProductServiceTest.class");

        // init ProjectService
        service = new ProductService(repository, projectRepository, productAreaRepository,ratingRepository,productRatingRepository);
        // init empty test object
        emptyProductDto = new ProductDto();
        // init necessary information for test objects

        tmpProductDto1 = new ProductDto();
        tmpProductDto1.productName = "Swaps";

        project = new ProjectEntity();
        project.id = 1;
        project.name = "DKB";

        List<ProductEntity> emptyProductEntitiesList = new ArrayList<>();
        List<ProjectUserEntity> emptyProjectUserEntitiesList = new ArrayList<>();

        //create preProject for Testing
        preProjectEntity = new ProjectEntity();
        preProjectEntity.id = 1;
        preProjectEntity.creatorID = "74c31a72-7c9e-4095-a1d6-ec208b57ff1a";
        preProjectEntity.name = "Bank4";
        preProjectEntity.productEntities = emptyProductEntitiesList;
        preProjectEntity.projectUserEntities = emptyProjectUserEntitiesList;

        List<ProjectEntity> preProjectEntities = new ArrayList<>();
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

        List<ProductAreaEntity> preProductAreaEntities = new ArrayList<>();
        preProductAreaEntities.add(preProductAreaEntity);

        ProductAreaDto preProductAreaDto = new ProductAreaDto(
                42,
                "examplePreAreaName",
                "examplePreCat"
        );

        preProductDto = new ProductDto();
        preProductDto.productName = "preProduct";
        preProductDto.productID = 42;
        preProductDto.projectID = 1;
        preProductDto.productArea = preProductAreaDto;

        ProductAreaDto fullProductAreaDto = new ProductAreaDto(1, "exampleAreaName", "exampleCat");

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

        ProductDto updateDto = new ProductDto();
        updateDto.productName = "updatedDto";
        updateDto.productID = 111;
        updateDto.productArea = preProductAreaDto;
        updateDto.projectID = 1;

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.name = "DKB";

        String name = "Produkt";

        ProductRatingEntity productRatingEntity = new ProductRatingEntity();
        entity = new ProductEntity();
        entity.name = name;
        entity.project = projectEntity;
        entity.productRatingEntities = new ArrayList<>();
        ratingEntities = new ArrayList<>();
        List<RatingEntity> econimicRatingEntities = new ArrayList<>();
        complexityRatingEntities = new ArrayList<>();
        for(int i = 1; i < 20; i++){
            ProductRatingEntity tmp = new ProductRatingEntity();
            tmp.answer = "answer" + i;
            tmp.score = Score.HOCH;
            tmp.comment = "comment" + i;
            RatingEntity ratingEntity = new RatingEntity();
            ratingEntity.id = i;
            ratingEntity.criterion = "criterion" + i;
            ratingEntity.category = (i % 2 == 0) ? null : "category" + i;
            ratingEntity.ratingarea = (i < 9) ? RatingArea.ECONOMIC : RatingArea.COMPLEXITY;
            ratingEntities.add(ratingEntity);
            if(i < 9){
                econimicRatingEntities.add(ratingEntity);
            }else{
                complexityRatingEntities.add(ratingEntity);
            }
            tmp.productRatingId = new ProductRatingId(entity, ratingEntity);
            entity.productRatingEntities.add(tmp);
        }

        createDto = new ProductDto();
        createDto.productName = name;
        createDto.ratings = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            ProductRatingDto tmp = new ProductRatingDto();
            tmp.ratingID = i;
            tmp.answer = "answer" + i;
            tmp.score = Score.HOCH;
            tmp.comment = "comment" + i;
            createDto.ratings.add(tmp);
        }

        createEmptyDto = new ProductDto();
        createEmptyDto.productName = name;
        createEmptyDto.ratings = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            ProductRatingDto tmp = new ProductRatingDto();
            tmp.ratingID = i;
            createEmptyDto.ratings.add(tmp);
        }

    }


    /**
     * tests for findById()
     *
     * testFindById1: productID doesnt exist --> return null
     * testFindById2: productID exists --> return ProductDto
     */
    @Test
    public void test_findById_productNotFound_returnNull() {
        assertNull(service.findById(1));
    }

    @Test
    public void test_findById_productExists_returnProductDto() {
        // provide knowledge
        when(repository.findById(preProductDto.productID)).thenReturn(Optional.of(preProductEntity));

        // execute and assert test method
        ProductDto out = service.findById(preProductDto.productID);

        assertAll("find Product",
                () -> assertEquals(preProductDto.productID, out.productID),
                () -> assertEquals(preProductDto.productName, out.productName),
                () -> assertEquals(preProductEntity.project.id, out.projectID),
                () -> assertEquals(0, out.progressComplexity),
                () -> assertEquals(0, out.progressEconomic),
                () -> assertNull(out.ratings),
                () -> assertNull(out.productVariations)
        );
    }

    /**
     * tests for wrapperCreateProduct()
     *
     * testWrapper_createProduct: projectID/productAreaID doesnt exist --> throws resource not found
     * testWrapper_createProduct: projectID/productAreaID exists --> return List<ProductDto>
     */
    @Test
    public void test_wrapperCreateProduct_resourceNotFound_projectID() {
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.wrapperCreateProduct(1, fullProductDto));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_wrapperCreateProduct_resourceNotFound_productAreaID() {
        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        // execute and assert test method
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.wrapperCreateProduct(1, fullProductDto));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_wrapperCreateProduct_success_withoutProductVariations() {
        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.findById(1)).thenReturn(Optional.of(preProductAreaEntity));

        // execute and assert test method
        List<ProductDto> out = service.wrapperCreateProduct(1, fullProductDto);

        for(ProductDto productDtoOut : out) {
            assertAll("wrapper create product",
                    () -> assertEquals(fullProductDto.productID, productDtoOut.productID),
                    () -> assertEquals(fullProductDto.productName, productDtoOut.productName),
                    () -> assertEquals(fullProductDto.projectID, productDtoOut.projectID),
                    () -> assertEquals(fullProductDto.productArea.id, productDtoOut.productArea.id),
                    () -> assertEquals(fullProductDto.productVariations, productDtoOut.productVariations),
                    () -> assertEquals(0, productDtoOut.progressComplexity),
                    () -> assertEquals(0, productDtoOut.progressEconomic),
                    () -> assertNull(productDtoOut.ratings)
            );
        }
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
    public void test_createProduct_resourceNotFound_projectIdNotFound() {
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProduct(1, fullProductDto));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_createProduct_resurceNotFound_productAreaIdNotFound() {
        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));

        // execute and assert test method
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProduct(1, fullProductDto));

        String expectedMessage = "Resource not Found. ProjectID and/or ProjectAreaID does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_createProduct_badRequest_productNameMissing() {
        // init test object
        fullProductDto.productName = null;

        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.findById(1)).thenReturn(Optional.of(preProductAreaEntity));

        // execute and assert test method
        Exception exception = assertThrows(BadRequest.class,
                () -> service.createProduct(1, fullProductDto));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_createProduct_success_withoutProductVariations() {
        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.findById(1)).thenReturn(Optional.of(preProductAreaEntity));

        // execute and assert test method
        List<ProductDto> out = service.createProduct(1, fullProductDto);

        for(ProductDto productDtoOut : out) {
            assertAll("create Product",
                    () -> assertEquals(fullProductDto.productID, productDtoOut.productID),
                    () -> assertEquals(fullProductDto.productName, productDtoOut.productName),
                    () -> assertEquals(fullProductDto.projectID, productDtoOut.projectID),
                    () -> assertEquals(fullProductDto.productArea.id, productDtoOut.productArea.id),
                    () -> assertEquals(fullProductDto.productVariations, productDtoOut.productVariations),
                    () -> assertEquals(0, productDtoOut.progressEconomic),
                    () -> assertEquals(0, productDtoOut.progressComplexity),
                    () -> assertNull(productDtoOut.ratings)
            );
        }
    }


    @Test
    public void test_createProduct_success_withProductVariations() {
        fullProductDto.productVariations = new ArrayList<>();
        fullProductDto.productVariations.add(productDtoVariation);

        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.findById(1)).thenReturn(Optional.of(preProductAreaEntity));

        // execute and assert test method
        List<ProductDto> out = service.createProduct(1, fullProductDto);

       for(ProductDto productDtoOut : out) {
           assertAll("create Product",
                   () -> assertEquals(fullProductDto.productName, productDtoOut.productName),
                   () -> assertEquals(1, productDtoOut.projectID),
                   () -> assertEquals(1, productDtoOut.productArea.id),
                   () -> assertEquals(fullProductDto.productVariations, productDtoOut.productVariations),
                   () -> assertEquals(0, productDtoOut.progressEconomic),
                   () -> assertEquals(0, productDtoOut.progressComplexity),
                   () -> assertNull(productDtoOut.ratings)
           );
       }
    }

    @Test
    @Disabled("(discuss with Alex) was soll der Test machen?")
    public void test_createProductVariation_1(){
        fullProductDto.productID = 666;
        fullProductDto.parentID = 1;

        // provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.ofNullable(preProductEntity));
        when(repository.existsByProjectAndProductarea(
                projectRepository.getById(1),
                productAreaRepository.getById(1))).thenReturn(true);

        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));


        // execute and assert test method
        List<ProductDto> out = service.createProduct(1, fullProductDto);

        assertNotEquals(0, out.size());
    }

    @Test
    @Disabled("implement when multiple creations is posible")
    public void test_createMultipleProducts1_withVariations(){
        // provide knowledge
        when(projectRepository.findById(1)).thenReturn(Optional.of(preProjectEntity));

        // execute and assert test method
    }


    /**
     * tests for getParentEntity()
     *
     * testGetParentEntity: parentID does not exist -> throw BadRequest
     * testGetParentEntity: parentID does exist -> return parent entity
     */
    @Test
    public void test_getParentEntity_badRequest_parentIdNotFound() {
        fullProductDto.parentID = 1;

        // provide knowledge
        when(repository.findById(fullProductDto.parentID)).thenReturn(Optional.empty());

        // execute and assert test method
        Exception exception = assertThrows(BadRequest.class,
                () -> service.getParentEntity(fullProductDto));

        String expectedMessage = "Parent Id does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_getParentEntity_success_parentIdFound() {
        fullProductDto.parentID = 1;

        // provide knowledge
        when(repository.findById(fullProductDto.parentID)).thenReturn(Optional.of(preProductEntity));

        // execute and assert test method
        ProductEntity out =  service.getParentEntity(fullProductDto);

        assertNotNull(out);
    }

    /**
     * tests for updateById()
     *
     * testUpdateById: productID does not exist -> return null
     * testUpdateById: productID exists, input missing -> return unchanged dto
     * testUpdateById: correct input -> attributes changed according to input
     */
    @Test
    public void test_updateById_productIdNotFound_returnNull() {
        assertNull(service.updateById(tmpProductDto1, 1));
    }

    @Test
    public void test_updateById_success_inputMissing_nothingToUpdate() {
        // provide knowledge
        when(repository.findById(1)).thenReturn(Optional.ofNullable(preProductEntity));

        // execute and assert test method
        ProductDto out = service.updateById(emptyProductDto, 1);

        assertEquals(preProductEntity.name, out.productName);
        assertEquals(preProductEntity.comment, out.comment);
    }

    @Test
    public void test_updateById_success_updateNameAndComment() {
        preProductDto.productName = "updatedName";
        preProductDto.comment = "updateComment";

        // provide knowledge
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        // execute and assert test method
        ProductDto out = service.updateById(preProductDto, 42);

        assertEquals("updatedName", out.productName);
        assertEquals("updateComment", out.comment);
    }

    @Test
    public void test_updateById_success_updateComment(){
        preProductDto.comment = "updateComment";
        preProductDto.productName = null;

        // provide knowledge
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        // execute and assert test method
        ProductDto out = service.updateById(preProductDto, 42);

        assertAll("update Product",
                () -> assertEquals(preProductEntity.name, out.productName),
                () -> assertEquals("updateComment", out.comment),
                () -> assertNull(out.ratings));
    }

    @Test
    public void test_updateById_success_updateComment_nothingToUpdate(){
        preProductDto.comment = "";

        // provide knowledge
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        // execute and assert test method
        ProductDto out = service.updateById(preProductDto, 42);

        assertAll("update Product comment",
                () -> assertNull(out.comment)
        );
    }

    @Test
    public void test_updateById_updateProductName_success(){
        // provide knowledge
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        preProductDto.productName = "updatedName";
        preProductDto.comment = null;

        // execute and assert test method
        ProductDto out = service.updateById(preProductDto, 42);

        assertAll("update Product",
                () -> assertEquals(preProductDto.productName, out.productName),
                () -> assertEquals(preProductEntity.comment, out.comment),
                () -> assertNull(out.ratings));
    }

    @Test
    public void test_getProductsByProjectId_IdNotFound(){

        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.getProductsByProjectId(404));

        String expectedMessage = "project 404 does not exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void test_updateById_badRequest_productNameEmptyString() {
        preProductDto.productName = "";

        // provide knowledge
        when(repository.findById(42)).thenReturn(Optional.of(preProductEntity));

        // execute and assert test method
        Exception exception = assertThrows(BadRequest.class,
                () -> service.updateById(preProductDto, 42));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * tests for GetProductsByProjectId()
     */
    @Test
    public void test_getProductsByProjectId_resourceNotFound_NotFound() {

        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.getProductsByProjectId(404));

        String expectedMessage = "project 404 does not exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void test_getProductsByProjectId_succsess_withoutDUMMYProducts(){
        List<ProductEntity> productEntities = new ArrayList<>();
        preProductEntity.name = "Produkt1";
        productEntities.add(preProductEntity);

        // provide knowledge
        when(projectRepository.findById(preProjectEntity.id)).thenReturn(Optional.of(preProjectEntity));
        when(repository.findByProject(preProjectEntity)).thenReturn(productEntities);

        // execute and assert test method
        List<ProductDto> out = service.getProductsByProjectId(preProjectEntity.id);

        assertEquals(productEntities.size(), out.size());
        out.forEach( product -> assertNotEquals("DUMMY", product.productName));
    }

    @Test
    public void test_getProductsByProjectId_succsess_withDUMMYProducts(){
        List<ProductEntity> productEntities = new ArrayList<>();
        preProductEntity.name = "DUMMY";
        productEntities.add(preProductEntity);

        // provide knowledge
        when(projectRepository.findById(preProjectEntity.id)).thenReturn(Optional.of(preProjectEntity));

        // execute and assert test method
        List<ProductDto> out = service.getProductsByProjectId(preProjectEntity.id);

        assertNotEquals(productEntities.size(), out.size());
        out.forEach( product -> assertNotEquals("DUMMY", product.productName));
    }


    /**
     * tests for getProductsByProjectIdAndProductAreaId()
     */
    @Test
    public void test_getProductsByProjectIdAndProductAreaId_resourceNotFound_projectID() {
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.getProductsByProjectIdAndProductAreaId(404, 404));

        String expectedMessage = "project 404 does not exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void test_getProductsByProjectIdAndProductAreaId_succsess_withoutDUMMYProducts(){
        List<ProductEntity> productEntities = new ArrayList<>();
        preProductEntity.name = "Produkt1";
        productEntities.add(preProductEntity);

        // provide knowledge
        when(projectRepository.findById(preProjectEntity.id)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.getById(preProductAreaEntity.id)).thenReturn(preProductAreaEntity);
        when(repository.findByProjectAndProductarea(preProjectEntity, preProductAreaEntity)).thenReturn(productEntities);

        // execute and assert test method
        List<ProductDto> out = service.getProductsByProjectIdAndProductAreaId(preProjectEntity.id, 1);

        assertEquals(productEntities.size(), out.size());
        out.forEach( product -> assertNotEquals("DUMMY", product.productName));
    }

    @Test
    public void test_getProductsByProjectIdAndProductAreaId_succsess_withDUMMYProducts(){
        List<ProductEntity> productEntities = new ArrayList<>();
        preProductEntity.name = "DUMMY";
        productEntities.add(preProductEntity);

        // provide knowledge
        when(projectRepository.findById(preProjectEntity.id)).thenReturn(Optional.of(preProjectEntity));
        when(productAreaRepository.getById(preProductAreaEntity.id)).thenReturn(preProductAreaEntity);
        when(repository.findByProjectAndProductarea(preProjectEntity, preProductAreaEntity)).thenReturn(productEntities);

        // execute and assert test method
        List<ProductDto> out = service.getProductsByProjectIdAndProductAreaId(preProjectEntity.id, 1);

        assertNotEquals(productEntities.size(), out.size());
        out.forEach( product -> assertNotEquals("DUMMY", product.productName));
    }

    /**
     * tests for calculateProductRatingProgress()
     */
    @Test
    public void test_calculateProductRatingProgress_productRatingEntitiesIsNull(){

        float[] out = service.calculateProductRatingProgress(preProductEntity);

        assertEquals(0.0f, out[0]);
        assertEquals(0.0f, out[1]);
    }

    @Test
    public void test_calculateProductRatingProgress_productRatingEntitiesIsEmpty(){
        preProductEntity.productRatingEntities = new ArrayList<>();

        float[] out = service.calculateProductRatingProgress(preProductEntity);

        assertEquals(0.0f, out[0]);
        assertEquals(0.0f, out[1]);
    }

    @Test
    public void test_calculateProductRatingProgress_productRatingEntitiesExist(){

        float[] out = service.calculateProductRatingProgress(entity);

        assertEquals(100.0f, out[0]);
        assertEquals(100.0f, out[1]);
    }


    /**
     * tests for createProductRatings()
     *
     * testCreateProductRatings: productID exists -> return productDto with empty created product ratings
     * testCreateProductRatings: productID does not exist -> return null
     */
    @Test
    public void test_createProductRatings_success() {
        // init test object
        int productID = 1;

        // provide knowledge
        when(repository.existsById(productID)).thenReturn(true);
        when(repository.getById(productID)).thenReturn(entity);
        when(ratingRepository.findAll()).thenReturn(ratingEntities);

        // execute and assert test method
        ProductDto out = service.createProductRatings(productID);

        assertEquals(createDto.productName , out.productName);
        out.ratings.forEach(rating ->
                assertAll(
                        () -> assertThat(rating.ratingID).isGreaterThan(0),
                        () -> assertNull(rating.answer),
                        () -> assertNull(rating.comment),
                        () -> assertNull(rating.score)
                )
        );
    }

    @Test
    public void test_createProductRatings_resourceNotFound_ratingsNotInitilizedInDB() {
        // init test object
        int productID = 1;
        ratingEntities = new ArrayList<>();

        // provide knowledge
        when(repository.existsById(productID)).thenReturn(true);
        when(repository.getById(productID)).thenReturn(entity);
        when(ratingRepository.findAll()).thenReturn(ratingEntities);


        // execute and assert test method
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProductRatings(productID));

        String expectedMessage = "Ratings not initilized.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void test_createProductRatings_productIdDoesNotExist_returnNull() {
        assertNull(service.createProductRatings(1));
    }
}
