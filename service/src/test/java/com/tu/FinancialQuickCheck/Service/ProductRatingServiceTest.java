package com.tu.FinancialQuickCheck.Service;


import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.*;
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
public class ProductRatingServiceTest {

    static Logger log = Logger.getLogger(ProductRatingServiceTest.class.getName());

    @Mock
    ProductRatingRepository repository;
    @Mock
    ProductRepository productRepository;
    @Mock
    RatingRepository ratingRepository;

    private ProductRatingService service;

    private ProjectEntity projectEntity;
    private ProductEntity entity;
    private ProductRatingEntity productRatingEntity;
    private List<RatingEntity> ratingEntities;
    private List<RatingEntity> econimicRatingEntities;
    private List<RatingEntity> complexityRatingEntities;

    private ProductDto createDto;
    private ProductDto createEmptyDto;
    private ProductDto updateDto;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductRatingServiceTest.class");
        // init ProductRatingService
        service = new ProductRatingService(repository, productRepository, ratingRepository);

        projectEntity = new ProjectEntity();
        projectEntity.name = "DKB";

        String name = "Produkt";

        productRatingEntity = new ProductRatingEntity();
        entity = new ProductEntity();
        entity.name = name;
        //TODO: anpassen
//        entity.productarea = 1;
        entity.project = projectEntity;
        entity.productRatingEntities = new ArrayList<>();
        ratingEntities = new ArrayList<>();
        econimicRatingEntities = new ArrayList<>();
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

        updateDto = new ProductDto();
        updateDto.productName = name;
        updateDto.ratings = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            ProductRatingDto tmp = new ProductRatingDto();
            tmp.ratingID = i;
            tmp.answer = "update answer" + i;
            tmp.score = Score.GERING;
            tmp.comment = "update comment" + i;
            updateDto.ratings.add(tmp);
        }
    }

    /**
     * tests for getProductRatings()
     *
     * testGetProductRatings: productID does not exist --> return  null
     * testGetProductRatings: productID exist, no rating exists --> return empty ProductDto.ratings
     * testGetProductRatings: no ratings exist for ratingArea.COMPLEXITY --> return empty ProductDto.ratings
     * testGetProductRatings: no ratings exist for ratingArea.ECONOMIC --> return empty ProductDto.ratings
     * testGetProductRatings: ratings exist for ratingArea.COMPLEXITY --> return ProductDto with exisitng ratings
     * testGetProductRatings: ratings exist for ratingArea.ECONOMIC --> return ProductDto with exisitng ratings
     * testGetProductRatings: productID and ratings exist --> return ProductDto with exisitng ratings
     */
    @Test
    public void testGetProductRatings_resourceNotFound_productID() {

        assertNull(service.getProductRatings(entity.id, null));

    }

    @Test
    public void testGetProductRatings_noRatingsExist() {
        // Step 0: init test object
        entity.productRatingEntities = new ArrayList<>(){};

        // Step 1: provide knowledge
        when(productRepository.findById(entity.id)).thenReturn(Optional.of(entity));

        // Step 2: Execute getProductRatings()
        assertEquals(new ArrayList<>(), service.getProductRatings(entity.id, null).ratings);
    }

    @Test
    public void testGetProductRatings_noComplexityRatingsExist() {
        // Step 0: init test object
        entity.productRatingEntities.removeIf(productRatingEntity ->
                productRatingEntity.productRatingId.getRating().ratingarea == RatingArea.COMPLEXITY);

        // Step 1: provide knowledge
        when(productRepository.findById(entity.id)).thenReturn(Optional.of(entity));

        // Step 2: Execute getProductRatings() and assert
        assertEquals(new ArrayList<>(), service.getProductRatings(entity.id, RatingArea.COMPLEXITY).ratings);
    }

    @Test
    public void testGetProductRatings_noEconomicRatingsExist() {
        // Step 0: init test object
        entity.productRatingEntities.removeIf(productRatingEntity ->
                productRatingEntity.productRatingId.getRating().ratingarea == RatingArea.ECONOMIC);

        // Step 1: provide knowledge
        when(productRepository.findById(entity.id)).thenReturn(Optional.of(entity));

        // Step 2: Execute getProductRatings() and assert
        assertEquals(new ArrayList<>(), service.getProductRatings(entity.id, RatingArea.ECONOMIC).ratings);
    }

    @Test
    public void testGetProductRatings_ComplexityRatingsExist() {
        // Step 1: provide knowledge
        when(productRepository.findById(entity.id)).thenReturn(Optional.of(entity));

        // Step 2: Execute getProductRatings()
        ProductDto out = service.getProductRatings(entity.id, RatingArea.COMPLEXITY);

        out.ratings.forEach(
                rating -> assertThat(rating.ratingID > 8)
        );
    }

    @Test
    public void testGetProductRatings_EconomicRatingsExist() {
        // Step 1: provide knowledge
        when(productRepository.findById(entity.id)).thenReturn(Optional.of(entity));

        // Step 2: Execute getProductRatings()
        ProductDto out = service.getProductRatings(entity.id, RatingArea.ECONOMIC);

        out.ratings.forEach(
                rating -> assertThat(rating.ratingID < 9)
        );
    }

    @Test
    public void testGetProductRatings_returnAllExistingRatings() {
        // Step 1: provide knowledge
        when(productRepository.findById(entity.id)).thenReturn(Optional.of(entity));

        // Step 2: Execute getProductRatings()
        ProductDto out = service.getProductRatings(entity.id, null);

        assertNotNull(out.ratings);
        out.ratings.forEach(rating ->
                assertAll("get product ratings",
                        () -> assertThat(rating.rating.id).isGreaterThan(0),
                        () -> assertNotNull(rating.answer),
                        () -> assertNotNull(rating.score),
                        () -> assertNotNull(rating.comment)
        ));
    }



    /**
     * tests for createProductRatings()
     *
     * testCreateProductRatings: input contains required information (projectId, ratingID and NO data)
     *                            --> return ProductDto with created empty ratings
     * testCreateProductRatings: input contains required information (projectId, ratingID and data)
     *                            --> return ProductDto with created ratings
     * testCreateProductRatings: input projectID does not exist
     *                            --> throw ResourceNotFound Exception
     * testCreateProductRatings: input ratingID does not exist
     *                            --> throw ResourceNotFound Exception
     */
    @Test
    public void testCreateProductRatings_createRatingsWithoutData() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(productRepository.getById(productID)).thenReturn(entity);
        when(ratingRepository.existsById(1)).thenReturn(true);
        when(ratingRepository.existsById(2)).thenReturn(true);
        when(ratingRepository.existsById(3)).thenReturn(true);
        when(ratingRepository.existsById(4)).thenReturn(true);
        when(ratingRepository.existsById(5)).thenReturn(true);
        when(ratingRepository.existsById(6)).thenReturn(true);
        when(ratingRepository.existsById(7)).thenReturn(true);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntities.get(0)));

        when(ratingRepository.findByRatingarea(RatingArea.ECONOMIC)).thenReturn(econimicRatingEntities);

        // Step 2: Execute updateProject()
        ProductDto out = service.createProductRatings(createEmptyDto, productID);

        // Step 3: assert exception
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
    public void testCreateProductRatings_createRatingsWithData() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(productRepository.getById(productID)).thenReturn(entity);
        when(ratingRepository.existsById(1)).thenReturn(true);
        when(ratingRepository.existsById(2)).thenReturn(true);
        when(ratingRepository.existsById(3)).thenReturn(true);
        when(ratingRepository.existsById(4)).thenReturn(true);
        when(ratingRepository.existsById(5)).thenReturn(true);
        when(ratingRepository.existsById(6)).thenReturn(true);
        when(ratingRepository.existsById(7)).thenReturn(true);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntities.get(0)));

        when(ratingRepository.findByRatingarea(RatingArea.ECONOMIC)).thenReturn(econimicRatingEntities);


        // Step 2: Execute updateProject()
        ProductDto out = service.createProductRatings(createDto, productID);

        // Step 3: assert exception
        assertEquals(createDto.productName , out.productName);
        assertThat(out.ratings.size()).isEqualTo(econimicRatingEntities.size());

        for (ProductRatingDto rating : out.ratings) {
            assertThat(rating.ratingID).isBetween(1,8);
        }
    }


    @Test
    public void testCreateProductRatings_resourceNotFound_projectID() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(false);

        // Step 2: Execute updateProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProductRatings(createDto, productID));

        // Step 3: assert exception
        String expectedMessage = "productID " + productID + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testCreateProductRatings_resourceNotFound_ratingID_single() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(ratingRepository.existsById(1)).thenReturn(false);

        // Step 2: Execute updateProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProductRatings(createDto, productID));

        // Step 3: assert exception
        String expectedMessage = "ratingID " + 1 + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testCreateProductRatings_resourceNotFound_ratingID_multiple() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(productRepository.getById(productID)).thenReturn(entity);
        when(ratingRepository.existsById(1)).thenReturn(true);
        when(ratingRepository.existsById(2)).thenReturn(true);
        when(ratingRepository.existsById(3)).thenReturn(false);
        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntities.get(0)));

        when(ratingRepository.findByRatingarea(RatingArea.ECONOMIC)).thenReturn(econimicRatingEntities);

        // Step 2: Execute updateProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.createProductRatings(createDto, productID));

        // Step 3: assert exception
        String expectedMessage = "ratingID " + 3 + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }




    /**
     * tests for updateProductRatings()
     *
     * testUpdateProductRatings: input contains required information (projectId, ratingID and NO data)
     *                            --> nothing changes
     * testUpdateProductRatings2: input contains required information (projectId, ratingID and data)
     *                            --> update existing data with provided data
     * testUpdateProductRatings: input productID does not exist
     *                            --> throw ResourceNotFound Exception
     * testUpdateProductRatings4: input ratingID does not exist
     *                            --> throw ResourceNotFound Exception
     * testUpdateProductRatings5: input contains more than required information
     *                            --> return ProductDto with exisitng ratings and ignore addtional information
     */
    @Test
    public void testUpdateProductRatings_withoutData_nothingChanges() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(productRepository.findById(productID)).thenReturn(Optional.of(entity));
        when(productRepository.getById(productID)).thenReturn(entity);
        when(ratingRepository.getById(1)).thenReturn(ratingEntities.get(0));
        when(ratingRepository.getById(2)).thenReturn(ratingEntities.get(1));
        when(ratingRepository.getById(3)).thenReturn(ratingEntities.get(2));
        when(ratingRepository.getById(4)).thenReturn(ratingEntities.get(3));
        when(ratingRepository.getById(5)).thenReturn(ratingEntities.get(4));
        when(ratingRepository.getById(6)).thenReturn(ratingEntities.get(5));
        when(ratingRepository.getById(7)).thenReturn(ratingEntities.get(6));

        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(0))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(0)));
        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(1))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(1)));
        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(2))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(2)));
        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(3))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(3)));
        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(4))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(4)));
        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(5))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(5)));
        when(repository.findById(new ProductRatingId(entity, ratingEntities.get(6))))
                .thenReturn(Optional.of(entity.productRatingEntities.get(6)));

        // Step 2: Execute updateProject()
        ProductDto out = service.updateProductRatings(createEmptyDto, productID);

        // Step 3: assert exception
        assertEquals(createDto.productName , out.productName);
        out.ratings.forEach(rating ->
                        assertAll(
                                () -> assertThat(rating.ratingID).isGreaterThan(0),
                                () -> assertThat(rating.answer.length()).isBetween(7,8),
                                () -> assertThat(rating.comment.length()).isBetween(8,9),
                                () -> assertEquals(Score.HOCH, rating.score)
                        )
        );
    }


    @Test
    public void testUpdateProductRatings2() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(productRepository.findById(productID)).thenReturn(Optional.of(entity));
        when(productRepository.getById(productID)).thenReturn(entity);
        when(ratingRepository.getById(entity.productRatingEntities.get(0).productRatingId.getRating().id)).thenReturn(ratingEntities.get(0));
        when(ratingRepository.getById(entity.productRatingEntities.get(1).productRatingId.getRating().id)).thenReturn(ratingEntities.get(1));
        when(ratingRepository.getById(entity.productRatingEntities.get(2).productRatingId.getRating().id)).thenReturn(ratingEntities.get(2));
        when(repository.findById(entity.productRatingEntities.get(0).productRatingId))
                .thenReturn(Optional.of(entity.productRatingEntities.get(0)));
        when(repository.findById(entity.productRatingEntities.get(1).productRatingId))
                .thenReturn(Optional.of(entity.productRatingEntities.get(1)));
        when(repository.findById(entity.productRatingEntities.get(2).productRatingId))
                .thenReturn(Optional.of(entity.productRatingEntities.get(2)));

        // Step 2: Execute updateProject()
        service.updateProductRatings(updateDto, productID);

        //Step 3:
        //TODO: add assert

    }


    @Test
    public void testUpdateProductRatings_productIdNotFound() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(false);

        // Step 2: Execute updateProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.updateProductRatings(createDto, productID));

        // Step 3: assert exception
        String expectedMessage = "productID " + productID + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testUpdateProductRatings4_resourcesNotFound_ratingIdNotFound() {
        // Step 0: init test object
        int productID = 1;

        // Step 1: provide knowledge
        when(productRepository.existsById(productID)).thenReturn(true);
        when(productRepository.findById(productID)).thenReturn(Optional.of(entity));
        when(productRepository.getById(productID)).thenReturn(entity);
        when(ratingRepository.getById(entity.productRatingEntities.get(0).productRatingId.getRating().id)).thenReturn(ratingEntities.get(0));
        when(ratingRepository.getById(entity.productRatingEntities.get(1).productRatingId.getRating().id)).thenReturn(ratingEntities.get(1));
        when(ratingRepository.getById(entity.productRatingEntities.get(2).productRatingId.getRating().id)).thenReturn(ratingEntities.get(2));
        when(repository.findById(entity.productRatingEntities.get(0).productRatingId))
                .thenReturn(Optional.of(entity.productRatingEntities.get(0)));
        when(repository.findById(entity.productRatingEntities.get(1).productRatingId))
                .thenReturn(Optional.of(entity.productRatingEntities.get(1)));
        when(repository.findById(entity.productRatingEntities.get(2).productRatingId))
                .thenReturn(Optional.empty());

        // Step 2: Execute updateProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.updateProductRatings(createDto, productID));

        // Step 3: assert exception
        String expectedMessage = "ratingID " + 3 + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }



    /**
     * tests for assignAttributes():
     * attributes: answer, comment, score
     * //TODO: (discuss) what should happen if input is null or can this even happen?
     * testAssignAttributes: input is empty
     *                       --> ???
     * testAssignAttributes: assign any combination of attributes
     *                       --> entity is updated according to input and
     */
    @Test
    public void testAssignAttributes_emptyDto(){
        //Step 0: init test object
        ProductRatingDto dtoIn = new ProductRatingDto();
        String answer = "answer 1";
        String comment = "comment 1";
        Score score = Score.HOCH;
        productRatingEntity.answer = answer;
        productRatingEntity.comment = comment;
        productRatingEntity.score = score;

        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn, productRatingEntity);

        //Step 2: Assert result
        assertEquals(answer, productRatingEntity.answer);
        assertEquals(comment, productRatingEntity.comment);
        assertEquals(score, productRatingEntity.score);
    }


    @Test
    public void testAssignAttributes_emptyDtoAndEntity(){
        //Step 0: init test object
        ProductRatingDto dtoIn = new ProductRatingDto();
        ProductRatingEntity emptyEntity = new ProductRatingEntity();

        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn, emptyEntity);

        //Step 2: Assert result
        assertNull(emptyEntity.answer);
        assertNull(emptyEntity.comment);
        assertNull(emptyEntity.score);
    }


    @Test
    public void testAssignAttributes_assignAllAttributeToEmptyEntity(){
        //Step 0: init test object
        String answer = "answer 1";
        String comment = "comment 1";
        Score score = Score.HOCH;
        ProductRatingDto dtoIn = new ProductRatingDto();
        dtoIn.answer = answer;
        dtoIn.comment = comment;
        dtoIn.score = score;

        ProductRatingEntity emptyEntity = new ProductRatingEntity();


        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn, emptyEntity);

        //Step 2: Assert result
        assertEquals(answer, emptyEntity.answer);
        assertEquals(comment, emptyEntity.comment);
        assertEquals(score, emptyEntity.score);
    }


    @Test
    public void testAssignAttributes_assignSingleAttributeToEmptyEntity(){
        //Step 0: init test object
        String answer = "answer 1";
        String comment = "comment 1";
        Score score = Score.HOCH;

        ProductRatingDto dtoIn1 = new ProductRatingDto();
        ProductRatingDto dtoIn2 = new ProductRatingDto();
        ProductRatingDto dtoIn3 = new ProductRatingDto();

        dtoIn1.answer = answer;
        dtoIn2.comment = comment;
        dtoIn3.score = score;

        ProductRatingEntity entity1 = new ProductRatingEntity();
        ProductRatingEntity entity2 = new ProductRatingEntity();
        ProductRatingEntity entity3 = new ProductRatingEntity();


        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn1, entity1);
        service.assignAttributes(dtoIn2, entity2);
        service.assignAttributes(dtoIn3, entity3);

        //Step 2: Assert result
        assertAll("assign answer",
                () -> assertNull(entity1.score),
                () -> assertNull(entity1.comment),
                () -> assertEquals(answer, entity1.answer));

        assertAll("assign comment",
                () -> assertNull(entity2.score),
                () -> assertNull(entity2.answer),
                () -> assertEquals(comment, entity2.comment));

        assertAll("assign score",
                () -> assertNull(entity3.answer),
                () -> assertNull(entity3.answer),
                () -> assertEquals(score, entity3.score));

    }


    @Test
    public void testAssignAttributes_assignTwoAttributeToEmptyEntity(){
        //Step 0: init test object
        String answer = "answer 1";
        String comment = "comment 1";
        Score score = Score.HOCH;

        ProductRatingDto dtoIn1 = new ProductRatingDto();
        ProductRatingDto dtoIn2 = new ProductRatingDto();
        ProductRatingDto dtoIn3 = new ProductRatingDto();

        dtoIn1.answer = answer;
        dtoIn1.comment = comment;

        dtoIn2.comment = comment;
        dtoIn2.score = score;

        dtoIn3.answer = answer;
        dtoIn3.score = score;

        ProductRatingEntity entity1 = new ProductRatingEntity();
        ProductRatingEntity entity2 = new ProductRatingEntity();
        ProductRatingEntity entity3 = new ProductRatingEntity();

        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn1, entity1);
        service.assignAttributes(dtoIn2, entity2);
        service.assignAttributes(dtoIn3, entity3);

        //Step 2: Assert result
        assertAll("assign answer and comment",
                () -> assertNull(entity1.score),
                () -> assertEquals(comment, entity1.comment),
                () -> assertEquals(answer, entity1.answer));

        assertAll("assign comment and score",
                () -> assertEquals(score, entity2.score),
                () -> assertNull(entity2.answer),
                () -> assertEquals(comment, entity2.comment));

        assertAll("assign answer and score",
                () -> assertEquals(answer, entity3.answer),
                () -> assertNull(entity3.comment),
                () -> assertEquals(score, entity3.score));

    }


    @Test
    public void testAssignAttributes_assignAllAttributeToNonEmptyEntity(){
        //Step 0: init test object
        String answer = "answer 1";
        String comment = "comment 1";
        Score score = Score.HOCH;
        ProductRatingDto dtoIn = new ProductRatingDto();
        dtoIn.answer = answer;
        dtoIn.comment = comment;
        dtoIn.score = score;

        ProductRatingEntity entity = new ProductRatingEntity();
        String previousAnswer = "previous answer";
        String previousComment = "previous comment";
        Score previousScore = Score.GERING;
        entity.answer = previousAnswer;
        entity.comment = previousComment;
        entity.score = previousScore;

        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn, entity);

        //Step 2: Assert result
        assertEquals(answer, entity.answer);
        assertEquals(comment, entity.comment);
        assertEquals(score, entity.score);
    }


    @Test
    public void testAssignAttributes_multipleUpdatesOnSameEntity(){
        //Step 0: init test object
        String answer = "answer 1";
        String comment = "comment 1";
        Score score = Score.HOCH;

        ProductRatingDto dtoIn1 = new ProductRatingDto();
        ProductRatingDto dtoIn2 = new ProductRatingDto();
        ProductRatingDto dtoIn3 = new ProductRatingDto();

        dtoIn1.answer = answer;
        dtoIn2.comment = comment;
        dtoIn3.score = score;

        ProductRatingEntity entity = new ProductRatingEntity();
        String previousAnswer = "previous answer";
        String previousComment = "previous comment";
        Score previousScore = Score.GERING;
        entity.answer = previousAnswer;
        entity.comment = previousComment;
        entity.score = previousScore;


        //Step 1: Execute assignAttributes()
        service.assignAttributes(dtoIn1, entity);
        assertAll("assign answer",
                () -> assertEquals(previousScore, entity.score),
                () -> assertEquals(previousComment, entity.comment),
                () -> assertEquals(answer, entity.answer));

        service.assignAttributes(dtoIn2, entity);
        assertAll("assign comment",
                () -> assertEquals(previousScore, entity.score),
                () -> assertEquals(answer, entity.answer),
                () -> assertEquals(comment, entity.comment));

        service.assignAttributes(dtoIn3, entity);
        assertAll("assign score",
                () -> assertEquals(score, entity.score),
                () -> assertEquals(answer, entity.answer),
                () -> assertEquals(comment, entity.comment));
    }

}
