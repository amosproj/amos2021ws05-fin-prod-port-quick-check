package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.RatingService;
import com.tu.FinancialQuickCheck.db.RatingEntity;
import com.tu.FinancialQuickCheck.db.RatingRepository;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    static Logger log = Logger.getLogger(RatingServiceTest.class.getName());

    @Mock
    RatingRepository repository;

    private RatingService service;

    private List<RatingEntity> entities;
    private List<RatingEntity> entitiesComplexity;
    private List<RatingEntity> entitiesEconomic;


    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in RatingServiceTest.class");

        service = new RatingService(repository);

        String criterion1 = "Frequenz";
        String criterion2 = "Produktabschlüsse";

        RatingDto dto1;
        dto1 = new RatingDto();
        dto1.criterion = criterion1;
        dto1.ratingArea = RatingArea.ECONOMIC;

        RatingDto dto2;
        dto2 = new RatingDto();
        dto2.criterion = criterion2;
        dto2.ratingArea = RatingArea.ECONOMIC;

        String criterion3 = "Frage für Kreditrating";
        String criterion4 = "Frage für Zahlungsbedingungen";
        String category3 = "Kreditrating";
        String category4 = "Zahlungsbedingungen";

        RatingDto dto3;
        dto3 = new RatingDto();
        dto3.criterion = criterion3;
        dto3.category = category3;
        dto3.ratingArea = RatingArea.COMPLEXITY;

        RatingDto dto4;
        dto4 = new RatingDto();
        dto4.criterion = criterion4;
        dto4.category = category4;
        dto4.ratingArea = RatingArea.COMPLEXITY;

        RatingEntity entity1 = new RatingEntity();
        entity1.criterion = criterion1;
        entity1.ratingarea = RatingArea.ECONOMIC;
        RatingEntity entity2 = new RatingEntity();
        entity2.criterion = criterion2;
        entity2.ratingarea = RatingArea.ECONOMIC;
        entitiesEconomic = new ArrayList<>();
        entitiesEconomic.add(entity1);
        entitiesEconomic.add(entity2);

        RatingEntity entity3 = new RatingEntity();
        entity3.criterion = criterion3;
        entity3.category = category3;
        entity3.ratingarea = RatingArea.COMPLEXITY;
        RatingEntity entity4 = new RatingEntity();
        entity4.criterion = criterion4;
        entity4.category = category4;
        entity4.ratingarea = RatingArea.COMPLEXITY;
        entitiesComplexity = new ArrayList<>();
        entitiesComplexity.add(entity3);
        entitiesComplexity.add(entity4);

        entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
    }
    
    
    /**
     * tests for getAllRatings()
     *
     * testGetAllRatings1: no ratings exist --> return empty List<RatingDto>
     * testGetAllRatings2: ratings exist --> return List<RatingDto>
     */
    @Test
    public void test_getAllRatings_noRatingsExist_returnEmptyList() {
        // Step 1: init test object         
        List<RatingEntity> ratingEntities = new ArrayList<>();
        
        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(ratingEntities);

        // Step 3: execute getAllRatings()
        List<RatingDto> out = service.getAllRatings();
        List<RatingDto> expected = new ArrayList<>();

        assertEquals(expected, out);
    }

    @Test
    public void test_getAllRatings_ratingsExist() {
        // Step 1: provide knowledge
        when(repository.findAll()).thenReturn(entities);

        // Step 2: execute test method and assert result
        List<RatingDto> out = service.getAllRatings();

        out.forEach(
                rating -> assertAll("get ratings",
                        () -> assertNotNull(rating.criterion),
                        () -> assertNotNull(rating.ratingArea),
                        () -> assertEquals(0, rating.id)
                )
        );

        assertThat(out.size()).isGreaterThanOrEqualTo(4);
    }


    /**
     * tests for getRatingsByRatingArea()
     *
     * testGetRatingsByRatingArea1: ratingArea = COMPLEXITY, rating exists --> return List<RatingDto>
     * testGetRatingsByRatingArea2: ratingArea = ECONOMIC, rating exists --> return List<RatingDto>
     * testGetRatingsByRatingArea3: ratingArea = COMPLEXITY, no rating exists --> return empty List<RatingDto>
     * testGetRatingsByRatingArea4: ratingArea = ECONOMIC, no rating exists --> return empty List<RatingDto>
     */
    @Test
    public void test_getRatingsByRatingArea_ratingsExist_ratingAreaCOMPLEXITY() {
        // Step 1: provide knowledge
        when(repository.findByRatingarea(RatingArea.COMPLEXITY)).thenReturn(entitiesComplexity);

        // Step 2: execute test method and assert result
        List<RatingDto> out = service.getRatingsByRatingArea(RatingArea.COMPLEXITY);

        out.forEach(
                rating -> assertAll("get ratings",
                        () -> assertNotNull(rating.criterion),
                        () -> assertNotNull(rating.ratingArea),
                        () -> assertEquals(0, rating.id),
                        () -> assertEquals(RatingArea.COMPLEXITY, rating.ratingArea)
                )
        );

        assertThat(out.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void test_getRatingsByRatingArea_ratingsExist_ratingAreaECONOMIC() {
        // Step 1: provide knowledge
        when(repository.findByRatingarea(RatingArea.ECONOMIC)).thenReturn(entitiesEconomic);

        // Step 2: execute test method and assert result
        List<RatingDto> out = service.getRatingsByRatingArea(RatingArea.ECONOMIC);

        out.forEach(
                rating -> assertAll("get ratings",
                        () -> assertNotNull(rating.criterion),
                        () -> assertNotNull(rating.ratingArea),
                        () -> assertEquals(0, rating.id),
                        () -> assertEquals(RatingArea.ECONOMIC, rating.ratingArea)
                )
        );

        assertThat(out.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void test_getRatingsByRatingArea_noRatingsExists_ratingAreaCOMPLEXITY() {
        // Step 1: init test object
        List<RatingEntity> ratingEntities = new ArrayList<>();

        // Step 2: provide knowledge
        when(repository.findByRatingarea(RatingArea.COMPLEXITY)).thenReturn(ratingEntities);

        // Step 3: execute test method and assert result
        List<RatingDto> out = service.getRatingsByRatingArea(RatingArea.COMPLEXITY);
        List<RatingDto> expected = new ArrayList<>();

        assertEquals(expected, out);
    }

    @Test
    public void test_getRatingsByRatingArea_noRatingsExists_ratingAreaECONOMIC() {
        // Step 1: init test object
        List<RatingEntity> ratingEntities = new ArrayList<>();

        // Step 2: provide knowledge
        when(repository.findByRatingarea(RatingArea.ECONOMIC)).thenReturn(ratingEntities);

        // Step 3: execute test method and assert result
        List<RatingDto> out = service.getRatingsByRatingArea(RatingArea.ECONOMIC);
        List<RatingDto> expected = new ArrayList<>();

        assertEquals(expected, out);
    }
}
