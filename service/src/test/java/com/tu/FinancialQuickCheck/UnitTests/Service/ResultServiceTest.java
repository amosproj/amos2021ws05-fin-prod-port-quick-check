package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.Service.ResultService;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import com.tu.FinancialQuickCheck.dto.ResultDto;
import com.tu.FinancialQuickCheck.dto.ScoreDto;
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

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

    static Logger log = Logger.getLogger(ResultServiceTest.class.getName());

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductRatingRepository productRatingRepository;

    private ResultService service;

    private int projectID;
    private int productAreaID;
    private Optional<String> productAreaIdStringOptional;

    private List<ResultDto> listDtos;
    private List<ProductRatingEntity> listProductRatingEntities;
    private List<ProductRatingDto> ratings;

    private Hashtable<Integer, ResultDto> ResultTable;
    private Hashtable<Integer, ResultDto> ResultTableCopy;

    private List<ProductRatingEntity> listProductRatingEntityForResult;
    private ProductRatingEntity productRatingEntityForResult;
    Hashtable<Integer, ResultDto> emptyTable;
    int productIdNotInTable = 0;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ResultServiceTest.class");
        // init ProductRatingService
        service = new ResultService(projectRepository, productRepository, productRatingRepository);

        projectID = 1;
        productAreaID = 6;
        productAreaIdStringOptional = Optional.of("6");

        listDtos = new ArrayList<>();
        String[] ratingNames = {"Kreditvolumen im Bestand", "Marge", "Kunde", "Gesamteinschätzung wirtschaftliche Bewertung"};
        Integer[] ratingIds = {4, 5, 10, 9};
        String[] answers = {"700 Mio EUR", "2,5%", "10.0, 20.0, 70.0", "answer 9"};

        ratings = new ArrayList<>();
        listProductRatingEntities = new ArrayList<>();
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < ratingNames.length; j++) {
                ProductRatingDto dto = new ProductRatingDto();
                RatingDto ratingDto = new RatingDto();
                ratingDto.id = ratingIds[j];
                ratingDto.criterion = ratingNames[j];
                dto.rating = ratingDto;
                dto.answer = answers[j];
                ratings.add(dto);

                ProductRatingEntity entity = new ProductRatingEntity();
                ProductEntity parentProductEntity = new ProductEntity();
                parentProductEntity.id = 99;
                parentProductEntity.name = "Produkt99";
                ProductEntity productEntity = new ProductEntity();
                productEntity.id = 100;
                productEntity.parentProduct = parentProductEntity;
                RatingEntity ratingEntity = new RatingEntity();
                ratingEntity.id = ratingIds[j];
                ratingEntity.criterion = ratingNames[j];
                entity.productRatingId = new ProductRatingId();
                entity.productRatingId.setRating(ratingEntity);
                entity.productRatingId.setProduct(productEntity);
                entity.answer = answers[j];
                listProductRatingEntities.add(entity);
            }

            ScoreDto[] scores = new ScoreDto[3];
            scores[2] = new ScoreDto(Score.HOCH, 5);
            scores[1] = new ScoreDto(Score.MITTEL, 7);
            scores[0] = new ScoreDto(Score.GERING, 0);

            listDtos.add(new ResultDto(100, "productName" + 100, ratings, scores));
        }


        ScoreDto[] scores = new ScoreDto[3];
        scores[2] = new ScoreDto(Score.HOCH, 5);
        scores[1] = new ScoreDto(Score.MITTEL, 7);
        scores[0] = new ScoreDto(Score.GERING, 0);

        ResultTable = new Hashtable<>();
        ResultTableCopy = new Hashtable<>();
        ResultTable.put(listDtos.get(0).productID, listDtos.get(0));
        ResultTableCopy.put(listDtos.get(0).productID, listDtos.get(0));

    }

    /**
     * tests for getResults()
     *
     * testGetResults: projectID does not exists and productAreaId is empty --> return null
     * testGetResults: projectID does not exists and productAreaId is not empty --> return null
     * testGetResults: projectID exists and productAreaId is empty --> return List<ResultDto>
     * testGetResults: projectID exists and productAreaId is not empty --> return List<ResultDto>
     * testGetResults: projectID exists and productAreaId is not an integer --> throw BadRequest
     */
    @Test
    public void testGetResults_resourceNotFound_productAreaIdEmpty() {
//        // provide knowledge
//        when(projectRepository.existsById(projectID)).thenReturn(Boolean.FALSE);
//
//        // execute and assert test method
//        assertNull(service.getResults(projectID, Optional.empty()));

        assertEquals(new ArrayList<>(), service.getResults(projectID, Optional.empty()));

    }

    @Test
    public void testGetResults_resourceNotFound_productAreaIdNotEmpty() {
        // provide knowledge
//        when(projectRepository.existsById(projectID)).thenReturn(Boolean.FALSE);

        // execute and assert test method
//        assertNull(service.getResults(projectID, productAreaIdStringOptional));

        assertEquals(new ArrayList<>(), service.getResults(projectID, productAreaIdStringOptional));
    }

    @Test
    public void testGetResults_resourceExists_productAreaIdEmpty() {
        // provide knowledge
        when(productRatingRepository.getResultsByProject(projectID)).thenReturn(listProductRatingEntities);

        // execute and assert test method
        List<ResultDto> out = service.getResults(projectID, Optional.empty());

        assertEquals(listDtos.size(), out.size());
    }

    @Test
    public void testGetResults_resourceExists_productAreaIdNotEmpty() {
        // provide knowledge
        when(productRatingRepository.getResultsByProjectAndProductArea(projectID, productAreaID)).thenReturn(listProductRatingEntities);

        // execute and assert test method
        List<ResultDto> out = service.getResults(projectID, productAreaIdStringOptional);

        assertEquals(listDtos.size(), out.size());
    }

    @Test
    public void testGetResults_badRequest() {
        // execute and assert test method
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> service.getResults(projectID, Optional.of("NAN")));

        String expectedMessage = "Input is missing/incorrect.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**tests for convertEntitiesToResultDtos()**/
    @Test
    public void testConvertEntitiesToResultDtos_nullPointerException_emptyRatingEntity(){

        Exception exception = assertThrows(NullPointerException.class,
                () -> service.convertEntitiesToResultDtos(listProductRatingEntityForResult));

        String expectedMessage = "List<ProductRatingEntity> not initilized.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**tests for updateResultRating()**/
    @Test
    public void testUpdateResultRating_emptyRatingEntity(){

        Exception exception = assertThrows(BadRequest.class,
                () -> service.updateResultRating(ResultTable, productRatingEntityForResult));

        String expectedMessage = "Table is Empty or ProductRatingEntity is missing";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateResultRating_emptyTable(){

        Exception exception = assertThrows(BadRequest.class,
                () -> service.updateResultRating(emptyTable, productRatingEntityForResult));

        String expectedMessage = "Table is Empty or ProductRatingEntity is missing";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateResultRating_nothingToUpdate(){

        int productId = listDtos.get(0).productID;
        String productName = listDtos.get(0).productName;
        ResultTable.put(productId, listDtos.get(0));
        productRatingEntityForResult = listProductRatingEntities.get(0);
        productRatingEntityForResult.productRatingId.getProduct().name = productName;
        productRatingEntityForResult.productRatingId.getProduct().id = productId;
        service.updateResultRating(ResultTable, productRatingEntityForResult);

        assertAll("update Result Rating",
                () -> assertEquals(productId, ResultTable.get(productId).productID),
                () -> assertEquals(productName, ResultTable.get(productId).productName)
        );
    }

    @Test
    public void testUpdateResultRating_succsess(){

        int productId = listDtos.get(0).productID;
        ResultTable.put(productId, listDtos.get(0));
        productRatingEntityForResult = listProductRatingEntities.get(0);
        productRatingEntityForResult.productRatingId.getProduct().name = "newName";
        service.updateResultRating(ResultTable, productRatingEntityForResult);

        assertAll("update Result Rating",
                () -> assertEquals(productId, ResultTable.get(productId).productID),
                () -> assertEquals("newName", ResultTable.get(productId).productName)
        );
    }

    /**tests for updateResultScore()**/
    @Test
    public void testUpdateResultScore_nullpointerException_tableIsNull(){
        Exception exception = assertThrows(NullPointerException.class,
                () -> service.updateResultScore(emptyTable, productRatingEntityForResult));
        String expectedMessage = "Table is not initilized, entity p does not have a parent or " +
                "parent entity does not have a name or id.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateResultScore_nullpointerException_entityIsNull(){
        Exception exception = assertThrows(NullPointerException.class,
                () -> service.updateResultScore(ResultTable, productRatingEntityForResult));
        String expectedMessage = "Table is not initilized, entity p does not have a parent or " +
                "parent entity does not have a name or id.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateResultScore_success_emptyTable_nothingToUpdate(){
        emptyTable = new Hashtable<>();

        for(ProductRatingEntity entity: listProductRatingEntities){
            service.updateResultScore(emptyTable, entity);
        }

        assertEquals(1, emptyTable.size());
    }

    @Test
    public void testUpdateResultScore_success_notEmptyTable_nothingToUpdate(){
        for(ProductRatingEntity entity: listProductRatingEntities){
            service.updateResultScore(ResultTable, entity);
        }

        assertEquals(2, ResultTable.size());
    }

    @Test
    public void testUpdateResultScore_success_emptyTable_scoreIsUpdated(){
        emptyTable = new Hashtable<>();
        listProductRatingEntities.get(3).score = Score.HOCH;

        for(ProductRatingEntity entity: listProductRatingEntities){
            service.updateResultScore(emptyTable, entity);
        }

        assertEquals(1, emptyTable.size());
        int parentId = listProductRatingEntities.get(0).productRatingId.getProduct().parentProduct.id;
        assertEquals(1, emptyTable.get(parentId).scores[2].count);
        assertEquals(0, emptyTable.get(parentId).scores[1].count);
        assertEquals(0, emptyTable.get(parentId).scores[0].count);
    }

    @Test
    public void testUpdateResultScore_success_notEmptyTable_scoreIsUpdated(){
        listProductRatingEntities.get(3).score = Score.HOCH;

        for(ProductRatingEntity entity: listProductRatingEntities){
            service.updateResultScore(ResultTable, entity);
        }

        assertEquals(2, ResultTable.size());
        int parentId = listProductRatingEntities.get(0).productRatingId.getProduct().parentProduct.id;
        assertEquals(1, ResultTable.get(parentId).scores[2].count);
        assertEquals(0, ResultTable.get(parentId).scores[1].count);
        assertEquals(0, ResultTable.get(parentId).scores[0].count);
    }


    /**tests for getResultDto()**/
    @Test
    public void testGetResultDto_nullpointerException_tableIsNull(){
        Exception exception = assertThrows(NullPointerException.class,
                () -> service.getResultDto(emptyTable, 100));
        String expectedMessage = "Table is not initilized.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetResultDto_emptyTable(){
        emptyTable = new Hashtable<>();

        ResultDto out = service.getResultDto(emptyTable, listDtos.get(0).productID);

        assertEquals(new ResultDto(), out);
    }

    @Test
    public void testGetResultDto_productIdNotInTable(){
        ResultDto out = service.getResultDto(ResultTable, productIdNotInTable);

        assertEquals(new ResultDto(), out);
    }

    @Test
    public void testGetResultDto_productIdInTable(){
        ResultDto out = service.getResultDto(ResultTable, listDtos.get(0).productID);

        assertEquals(listDtos.get(0), out);
    }


    /** tests for ResultDto **/
    @Test
    public void testResultDto_updateProductInfos(){

        ResultDto tmpResultDto = listDtos.get(0);

        tmpResultDto.updateProductInfos(42, "newName");

        assertAll("updateResultDto",
                () -> assertEquals(42, tmpResultDto.productID),
                () -> assertEquals("newName", tmpResultDto.productName)
        );
    }

    @Test
    public void testResultDto_equals(){

        ResultDto tmpResultDto          = listDtos.get(0);
        ResultDto tmpResultDtoSame      = listDtos.get(0);
        ResultDto tmpResultDtoDifferent = listDtos.get(0);
        tmpResultDtoDifferent.updateProductInfos(66, "differentName");

        //TODO: discuss equals
        assertTrue(tmpResultDto.equals(tmpResultDtoSame));
        assertFalse(tmpResultDto.equals(tmpResultDtoDifferent));
    }

    @Test
    public void testResultDto_hashCode(){

        ResultDto tmpResultDto = listDtos.get(0);
        assertEquals((-212165700), tmpResultDto.hashCode());


        tmpResultDto.updateProductInfos(66, "newName");
        assertEquals((1880436434), tmpResultDto.hashCode());
    }

    /** test for ScoreDto **/

    @Test
    public void testScoreDto_equals() {

        ScoreDto[] scores1 = new ScoreDto[3];
        scores1[2] = new ScoreDto(Score.HOCH, 5);
        scores1[1] = new ScoreDto(Score.MITTEL, 7);
        scores1[0] = new ScoreDto(Score.GERING, 0);

        ScoreDto[] scores1_same = scores1;

        ScoreDto[] scores2 = new ScoreDto[3];
        scores2[2] = new ScoreDto(Score.HOCH, 5);
        scores2[1] = new ScoreDto(Score.MITTEL, 8);
        scores2[0] = new ScoreDto(Score.GERING, 1);

        assertFalse(scores1.equals(scores2));
        assertTrue(scores1.equals(scores1_same));

    }

    @Test
    public void testScoreDto_hashCode(){

        ScoreDto[] scores = new ScoreDto[3];
        scores[2] = new ScoreDto(Score.HOCH, 5);
        scores[1] = new ScoreDto(Score.MITTEL, 7);
        scores[0] = new ScoreDto(Score.GERING, 0);

        assertEquals(1572127577, scores.hashCode());

    }


}
