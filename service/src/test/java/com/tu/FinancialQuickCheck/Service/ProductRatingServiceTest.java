//package com.tu.FinancialQuickCheck.Service;
//
//
//import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
//import com.tu.FinancialQuickCheck.RatingArea;
//import com.tu.FinancialQuickCheck.Score;
//import com.tu.FinancialQuickCheck.db.*;
//import com.tu.FinancialQuickCheck.dto.ProductDto;
//import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//import java.util.logging.Logger;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductRatingServiceTest {
//
//    static Logger log = Logger.getLogger(ProductRatingServiceTest.class.getName());
//
//    @Mock
//    ProductRatingRepository repository;
//    @Mock
//    ProductRepository productRepository;
//    @Mock
//    RatingRepository ratingRepository;
//
//    private ProductRatingService service;
//
//    private ProjectEntity projectEntity;
//    private ProductEntity entity;
//    private ProductRatingEntity productRatingEntity;
//    private List<RatingEntity> ratingEntities;
//
//    private ProductDto createDto;
//    private ProductDto createEmptyDto;
//    private ProductDto updateDto;
//
//    @BeforeEach
//    public void init() {
//        log.info("@BeforeEach - setup for Tests in ProductRatingServiceTest.class");
//        // init ProjectService
//        service = new ProductRatingService(repository, productRepository, ratingRepository);
//
//        projectEntity = new ProjectEntity();
//        projectEntity.name = "DKB";
//
//        String name = "Produkt";
//
//        entity = new ProductEntity();
//        entity.name = name;
//        //TODO: anpassen
////        entity.productareaid = 1;
//        entity.projectid = projectEntity;
//        entity.productRatingEntities = new ArrayList<>();
//        ratingEntities = new ArrayList<>();
//        for(int i = 1; i < 20; i++){
//            ProductRatingEntity tmp = new ProductRatingEntity();
//            tmp.answer = "answer" + i;
//            tmp.score = Score.HOCH;
//            tmp.comment = "comment" + i;
//            RatingEntity ratingEntity = new RatingEntity();
//            ratingEntity.id = i;
//            ratingEntity.criterion = "criterion" + i;
//            ratingEntity.category = (i % 2 == 0) ? null : "category" + i;
//            ratingEntity.ratingarea = (i < 9) ? RatingArea.ECONOMIC : RatingArea.COMPLEXITY;
//            ratingEntities.add(ratingEntity);
//            tmp.productRatingId = new ProductRatingId(entity, ratingEntity);
//            entity.productRatingEntities.add(tmp);
//        }
//
//        createDto = new ProductDto();
//        createDto.productName = name;
//        createDto.ratings = new ArrayList<>();
//        for(int i = 1; i < 11; i++){
//            ProductRatingDto tmp = new ProductRatingDto();
//            tmp.ratingID = i;
//            tmp.answer = "answer" + i;
//            tmp.score = Score.HOCH;
//            tmp.comment = "comment" + i;
//            createDto.ratings.add(tmp);
//        }
//
//        createEmptyDto = new ProductDto();
//        createEmptyDto.productName = name;
//        createEmptyDto.ratings = new ArrayList<>();
//        for(int i = 1; i < 11; i++){
//            ProductRatingDto tmp = new ProductRatingDto();
//            tmp.ratingID = i;
//            createEmptyDto.ratings.add(tmp);
//        }
//
//        updateDto = new ProductDto();
//        updateDto.productName = name;
//        updateDto.ratings = new ArrayList<>();
//        for(int i = 1; i < 4; i++){
//            ProductRatingDto tmp = new ProductRatingDto();
//            tmp.ratingID = i;
//            tmp.answer = "answer" + i;
//            tmp.score = Score.HOCH;
//            tmp.comment = "comment" + i;
//            updateDto.ratings.add(tmp);
//        }
//    }
//
//    /**
//     * tests for getProductRatings()
//     *
//     * testGetProductRatings1: productID does not exist --> throw ResourceNotFound Exception
//     * testGetProductRatings2: productID exist, no rating exists --> return empty ProductDto.ratings
//     * testGetProductRatings3: no ratings exist for ratingArea.COMPLEXITY --> return empty ProductDto.ratings
//     * testGetProductRatings4: no ratings exist for ratingArea.ECONOMIC --> return empty ProductDto.ratings
//     * testGetProductRatings5: ratings exist for ratingArea.COMPLEXITY --> return ProductDto with exisitng ratings
//     * testGetProductRatings6: ratings exist for ratingArea.ECONOMIC --> return ProductDto with exisitng ratings
//     * testGetProductRatings7: productID and ratings exist --> return ProductDto with exisitng ratings
//     */
//    @Test
//    public void testGetProductRatings1() {
//        // Step 0: init test object
//        Optional<ProductEntity>  entities = Optional.empty();
//
//        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(entities);
//
//        // Step 2: Execute updateProject()
////        Exception exception = assertThrows(ResourceNotFound.class, ()
////                -> service.getProductRatings(entity.product_id, null));
//
//        // Step 3: assert exception
////        String expectedMessage = "productID " + entity.product_id + " not found";
////        String actualMessage = exception.getMessage();
//
////        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
////    @Test
////    public void testGetProductRatings2() {
////        // Step 0: init test object
////        entity.productRatingEntities = new ArrayList<>(){};
////
////        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(Optional.of(entity));
////
////        // Step 2: Execute getProductRatings()
////        assertEquals(new ArrayList<>(), service.getProductRatings(entity.product_id, null).ratings);
////    }
////
////    @Test
////    public void testGetProductRatings3() {
////        // Step 0: init test object
////        entity.productRatingEntities.removeIf(productRatingEntity ->
////                productRatingEntity.productRatingId.getRatingid().ratingarea == RatingArea.COMPLEXITY);
////
////        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(Optional.of(entity));
////
////        // Step 2: Execute getProductRatings() and assert
////        assertEquals(new ArrayList<>(), service.getProductRatings(entity.product_id, RatingArea.COMPLEXITY).ratings);
////    }
////
////    @Test
////    public void testGetProductRatings4() {
////        // Step 0: init test object
////        entity.productRatingEntities.removeIf(productRatingEntity ->
////                productRatingEntity.productRatingId.getRatingid().ratingarea == RatingArea.ECONOMIC);
////
////        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(Optional.of(entity));
////
////        // Step 2: Execute getProductRatings() and assert
////        assertEquals(new ArrayList<>(), service.getProductRatings(entity.product_id, RatingArea.ECONOMIC).ratings);
////    }
////
////    @Test
////    public void testGetProductRatings5() {
////        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(Optional.of(entity));
////
////        // Step 2: Execute getProductRatings()
////        ProductDto out = service.getProductRatings(entity.product_id, RatingArea.COMPLEXITY);
////
////        out.ratings.forEach(
////                rating -> assertThat(rating.ratingID > 8)
////        );
////    }
////
////    @Test
////    public void testGetProductRatings6() {
////        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(Optional.of(entity));
////
////        // Step 2: Execute getProductRatings()
////        ProductDto out = service.getProductRatings(entity.product_id, RatingArea.ECONOMIC);
////
////        out.ratings.forEach(
////                rating -> assertThat(rating.ratingID < 9)
////        );
////    }
////
////    @Test
////    public void testGetProductRatings7() {
////        // Step 1: provide knowledge
////        when(productRepository.findById(entity.product_id)).thenReturn(Optional.of(entity));
////
////        // Step 2: Execute getProductRatings()
////        ProductDto out = service.getProductRatings(entity.product_id, null);
////
////        assertNotNull(out.ratings);
////        out.ratings.forEach(rating ->
////                assertAll("get product ratings",
//////                        () -> assertNotNull(rating.ratingID),
////                        () -> assertNotNull(rating.answer),
////                        () -> assertNotNull(rating.score),
////                        () -> assertNotNull(rating.comment)
////        ));
////    }
//
//
//
//    /**
//     * tests for createProductRatings()
//     *
//     * testCreateProductRatings1: input contains required information (projectId, ratingID and NO data)
//     *                            --> return ProductDto with created empty ratings
//     * testCreateProductRatings2: input contains required information (projectId, ratingID and data)
//     *                            --> return ProductDto with created ratings
//     * testCreateProductRatings3: input projectID does not exist
//     *                            --> throw ResourceNotFound Exception
//     * testCreateProductRatings4: input ratingID does not exist
//     *                            --> throw ResourceNotFound Exception
//     * testCreateProductRatings5: input contains more than required information
//     *                            --> return ProductDto with exisitng ratings and ignore addtional information
//     */
//    @Test
//    public void testCreateProductRatings1() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(true);
//        when(ratingRepository.existsById(1)).thenReturn(true);
//        when(ratingRepository.existsById(2)).thenReturn(true);
//        when(ratingRepository.existsById(3)).thenReturn(true);
//        when(ratingRepository.existsById(4)).thenReturn(true);
//        when(ratingRepository.existsById(5)).thenReturn(true);
//        when(ratingRepository.existsById(6)).thenReturn(true);
//        when(ratingRepository.existsById(7)).thenReturn(true);
//        when(ratingRepository.existsById(8)).thenReturn(true);
//        when(ratingRepository.existsById(9)).thenReturn(true);
//        when(ratingRepository.existsById(10)).thenReturn(true);
//        when(ratingRepository.getById(1)).thenReturn(ratingEntities.get(0));
//        when(ratingRepository.getById(2)).thenReturn(ratingEntities.get(1));
//        when(ratingRepository.getById(3)).thenReturn(ratingEntities.get(2));
//        when(ratingRepository.getById(4)).thenReturn(ratingEntities.get(3));
//        when(ratingRepository.getById(5)).thenReturn(ratingEntities.get(4));
//        when(ratingRepository.getById(6)).thenReturn(ratingEntities.get(5));
//        when(ratingRepository.getById(7)).thenReturn(ratingEntities.get(6));
//        when(ratingRepository.getById(8)).thenReturn(ratingEntities.get(7));
//        when(ratingRepository.getById(9)).thenReturn(ratingEntities.get(8));
//        when(ratingRepository.getById(10)).thenReturn(ratingEntities.get(9));
//
//        // Step 2: Execute updateProject()
//        ProductDto out = service.createProductRatings(createEmptyDto, productID);
//
//        // Step 3: assert exception
//        assertEquals(createDto.productName , out.productName);
//        out.ratings.forEach(rating ->
//                assertAll(
////                        () -> assertNotNull(rating.ratingID),
//                        () -> assertNull(rating.answer),
//                        () -> assertNull(rating.comment),
//                        () -> assertNull(rating.score)
//                )
//        );
//    }
//
//    @Test
//    public void testCreateProductRatings2() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(true);
//        when(productRepository.getById(productID)).thenReturn(entity);
//        when(ratingRepository.existsById(1)).thenReturn(true);
//        when(ratingRepository.existsById(2)).thenReturn(true);
//        when(ratingRepository.existsById(3)).thenReturn(true);
//        when(ratingRepository.existsById(4)).thenReturn(true);
//        when(ratingRepository.existsById(5)).thenReturn(true);
//        when(ratingRepository.existsById(6)).thenReturn(true);
//        when(ratingRepository.existsById(7)).thenReturn(true);
//        when(ratingRepository.existsById(8)).thenReturn(true);
//        when(ratingRepository.existsById(9)).thenReturn(true);
//        when(ratingRepository.existsById(10)).thenReturn(true);
//        when(ratingRepository.getById(1)).thenReturn(ratingEntities.get(0));
//        when(ratingRepository.getById(2)).thenReturn(ratingEntities.get(1));
//        when(ratingRepository.getById(3)).thenReturn(ratingEntities.get(2));
//        when(ratingRepository.getById(4)).thenReturn(ratingEntities.get(3));
//        when(ratingRepository.getById(5)).thenReturn(ratingEntities.get(4));
//        when(ratingRepository.getById(6)).thenReturn(ratingEntities.get(5));
//        when(ratingRepository.getById(7)).thenReturn(ratingEntities.get(6));
//        when(ratingRepository.getById(8)).thenReturn(ratingEntities.get(7));
//        when(ratingRepository.getById(9)).thenReturn(ratingEntities.get(8));
//        when(ratingRepository.getById(10)).thenReturn(ratingEntities.get(9));
//
//
//        // Step 2: Execute updateProject()
//        ProductDto out = service.createProductRatings(createDto, productID);
//
//        // Step 3: assert exception
//        assertEquals(createDto.productName , out.productName);
//
////        assertEquals(createDto.ratings, out.ratings);
//        for (ProductRatingDto rating : out.ratings) {
////            assertNotNull(rating.ratingID);
//            assertNotNull(rating.answer);
//            assertNotNull(rating.comment);
//            assertNotNull(rating.score);
//        }
//    }
//
//    @Test
//    public void testCreateProductRatings3() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(false);
//
//        // Step 2: Execute updateProject()
//        Exception exception = assertThrows(ResourceNotFound.class,
//                () -> service.createProductRatings(createDto, productID));
//
//        // Step 3: assert exception
//        String expectedMessage = "productID " + productID + " not found";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testCreateProductRatings4() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(true);
//        when(ratingRepository.existsById(1)).thenReturn(true);
//        when(ratingRepository.existsById(2)).thenReturn(true);
//        when(ratingRepository.existsById(3)).thenReturn(false);
//
//        // Step 2: Execute updateProject()
//        Exception exception = assertThrows(ResourceNotFound.class,
//                () -> service.createProductRatings(createDto, productID));
//
//        // Step 3: assert exception
//        String expectedMessage = "ratingID " + 3 + " not found";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//
//    /**
//     * tests for updateProductRatings()
//     *
//     * testUpdateProductRatings1: input contains required information (projectId, ratingID and NO data)
//     *                            --> ???? TODO: sollen die vorhanden Daten mit nichts überschrieben werden
//     * testUpdateProductRatings2: input contains required information (projectId, ratingID and data)
//     *                            --> update existing data with provided data
//     * testUpdateProductRatings3: input projectID does not exist
//     *                            --> throw ResourceNotFound Exception
//     * testUpdateProductRatings4: input ratingID does not exist
//     *                            --> throw ResourceNotFound Exception
//     * testUpdateProductRatings5: input contains more than required information
//     *                            --> return ProductDto with exisitng ratings and ignore addtional information
//     */
//    @Test
//    @Disabled
//    public void testUpdateProductRatings1() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(true);
//        when(ratingRepository.existsById(1)).thenReturn(true);
//        when(ratingRepository.existsById(2)).thenReturn(true);
//        when(ratingRepository.existsById(3)).thenReturn(true);
//        when(ratingRepository.existsById(4)).thenReturn(true);
//        when(ratingRepository.existsById(5)).thenReturn(true);
//        when(ratingRepository.existsById(6)).thenReturn(true);
//        when(ratingRepository.existsById(7)).thenReturn(true);
//        when(ratingRepository.existsById(8)).thenReturn(true);
//        when(ratingRepository.existsById(9)).thenReturn(true);
//        when(ratingRepository.existsById(10)).thenReturn(true);
//        when(ratingRepository.getById(1)).thenReturn(ratingEntities.get(0));
//        when(ratingRepository.getById(2)).thenReturn(ratingEntities.get(1));
//        when(ratingRepository.getById(3)).thenReturn(ratingEntities.get(2));
//        when(ratingRepository.getById(4)).thenReturn(ratingEntities.get(3));
//        when(ratingRepository.getById(5)).thenReturn(ratingEntities.get(4));
//        when(ratingRepository.getById(6)).thenReturn(ratingEntities.get(5));
//        when(ratingRepository.getById(7)).thenReturn(ratingEntities.get(6));
//        when(ratingRepository.getById(8)).thenReturn(ratingEntities.get(7));
//        when(ratingRepository.getById(9)).thenReturn(ratingEntities.get(8));
//        when(ratingRepository.getById(10)).thenReturn(ratingEntities.get(9));
//
//        // Step 2: Execute updateProject()
//        ProductDto out = service.createProductRatings(createEmptyDto, productID);
//
//        // Step 3: assert exception
//        assertEquals(createDto.productName , out.productName);
//        out.ratings.forEach(rating ->
//                        assertAll(
////                        () -> assertNotNull(rating.ratingID),
//                                () -> assertNull(rating.answer),
//                                () -> assertNull(rating.comment),
//                                () -> assertNull(rating.score)
//                        )
//        );
//    }
//
//    @Test
//    public void testUpdateProductRatings2() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(true);
//        when(productRepository.getById(productID)).thenReturn(entity);
//        when(ratingRepository.getById(entity.productRatingEntities.get(0).productRatingId.getRatingid().id)).thenReturn(ratingEntities.get(0));
//        when(ratingRepository.getById(entity.productRatingEntities.get(1).productRatingId.getRatingid().id)).thenReturn(ratingEntities.get(1));
//        when(ratingRepository.getById(entity.productRatingEntities.get(2).productRatingId.getRatingid().id)).thenReturn(ratingEntities.get(2));
//        when(repository.findById(entity.productRatingEntities.get(0).productRatingId))
//                .thenReturn(Optional.of(entity.productRatingEntities.get(0)));
//        when(repository.findById(entity.productRatingEntities.get(1).productRatingId))
//                .thenReturn(Optional.of(entity.productRatingEntities.get(1)));
//        when(repository.findById(entity.productRatingEntities.get(2).productRatingId))
//                .thenReturn(Optional.of(entity.productRatingEntities.get(2)));
//
//        // Step 2: Execute updateProject()
//        service.updateProductRatings(updateDto, productID);
//
//
//    }
//
//    @Test
//    public void testUpdateProductRatings3_projectIdNotFound() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(false);
//
//        // Step 2: Execute updateProject()
//        Exception exception = assertThrows(ResourceNotFound.class,
//                () -> service.updateProductRatings(createDto, productID));
//
//        // Step 3: assert exception
//        String expectedMessage = "productID " + productID + " not found";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testUpdateProductRatings4_ratingIdNotFound() {
//        // Step 0: init test object
//        int productID = 1;
//
//        // Step 1: provide knowledge
//        when(productRepository.existsById(productID)).thenReturn(true);
//        when(productRepository.getById(productID)).thenReturn(entity);
//        when(ratingRepository.getById(entity.productRatingEntities.get(0).productRatingId.getRatingid().id)).thenReturn(ratingEntities.get(0));
//        when(ratingRepository.getById(entity.productRatingEntities.get(1).productRatingId.getRatingid().id)).thenReturn(ratingEntities.get(1));
//        when(ratingRepository.getById(entity.productRatingEntities.get(2).productRatingId.getRatingid().id)).thenReturn(ratingEntities.get(2));
//        when(repository.findById(entity.productRatingEntities.get(0).productRatingId))
//                .thenReturn(Optional.of(entity.productRatingEntities.get(0)));
//        when(repository.findById(entity.productRatingEntities.get(1).productRatingId))
//                .thenReturn(Optional.of(entity.productRatingEntities.get(1)));
//        when(repository.findById(entity.productRatingEntities.get(2).productRatingId))
//                .thenReturn(Optional.empty());
//
//        // Step 2: Execute updateProject()
//        Exception exception = assertThrows(ResourceNotFound.class,
//                () -> service.updateProductRatings(createDto, productID));
//
//        // Step 3: assert exception
//        String expectedMessage = "ratingID " + 3 + " not found";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//}
