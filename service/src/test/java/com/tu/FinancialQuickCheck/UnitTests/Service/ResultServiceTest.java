package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

        List<ProductRatingDto> ratings = new ArrayList<>();
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
                ProductEntity productEntity = new ProductEntity();
                productEntity.id = 100;
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

            listDtos.add(new ResultDto(i, "productName" + i, ratings, scores));
        }
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


}
