package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.RatingController;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.RatingService;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    static Logger log = Logger.getLogger(RatingControllerTest.class.getName());

    @Mock
    private RatingService service;

    private RatingController controller;

    private RatingDto dto1;
    private RatingDto dto2;
    private RatingDto dto3;
    private RatingDto dto4;
    private List<RatingDto> listDtos;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in RatingControllerTest.class");

        controller = new RatingController(service);

        String criterion1 = "Frequenz";
        String criterion2 = "Produktabschlüsse";
        String criterion3 = "Frage für Kreditrating";
        String criterion4 = "Frage für Zahlungsbedingungen";
        String category3 = "Kreditrating";
        String category4 = "Zahlungsbedingungen";

        dto1 = new RatingDto();
        dto1.criterion = criterion1;
        dto1.ratingArea = RatingArea.ECONOMIC;

        dto2 = new RatingDto();
        dto2.criterion = criterion2;
        dto2.ratingArea = RatingArea.ECONOMIC;

        dto3 = new RatingDto();
        dto3.criterion = criterion3;
        dto3.category = category3;
        dto3.ratingArea = RatingArea.COMPLEXITY;

        dto4 = new RatingDto();
        dto4.criterion = criterion4;
        dto4.category = category4;
        dto4.ratingArea = RatingArea.COMPLEXITY;

        listDtos = new ArrayList<>();
    }

    /**
     * tests for getRatings()
     *
     * testGetRatings: no ratings exist with/without ratingArea--> throw ResourceNotFound
     * testGetRatings: ratings exist with/without ratingArea --> return List<RatingDto>
     */
    @Test
    public void test_getRatings_noRatingArea_resourceNotFound() {
        // Step 1: provide knowledge
        when(service.getAllRatings()).thenReturn(new ArrayList<>());

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.getRatings(null));

        String expectedMessage = "No Ratings found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_getRatings_economicRatingArea_resourceNotFound() {
        // Step 1: provide knowledge
        when(service.getRatingsByRatingArea(RatingArea.ECONOMIC)).thenReturn(new ArrayList<>());

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.getRatings(RatingArea.ECONOMIC));

        String expectedMessage = "No Ratings found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_getRatings_complexityRatingArea_resourceNotFound() {
        // Step 1: provide knowledge
        when(service.getRatingsByRatingArea(RatingArea.COMPLEXITY)).thenReturn(new ArrayList<>());

        // Step 3: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.getRatings(RatingArea.COMPLEXITY));

        String expectedMessage = "No Ratings found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_getRatings_noRatingArea_resourceExists() {
        listDtos.add(dto1);
        listDtos.add(dto2);
        listDtos.add(dto3);
        listDtos.add(dto4);

        // Step 1: provide knowledge
        when(service.getAllRatings()).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<RatingDto> out = controller.getRatings(null);

        assertEquals(out.size(), listDtos.size());
    }

    @Test
    public void test_getRatings_economicRatingArea_resourceExists() {
        listDtos.add(dto1);
        listDtos.add(dto2);

        // Step 1: provide knowledge
        when(service.getRatingsByRatingArea(RatingArea.ECONOMIC)).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<RatingDto> out = controller.getRatings(RatingArea.ECONOMIC);

        assertEquals(out.size(), listDtos.size());
        out.forEach(o -> assertEquals(o.ratingArea, RatingArea.ECONOMIC));
    }

    @Test
    public void test_getRatings_complexityRatingArea_resourceExists() {
        listDtos.add(dto3);
        listDtos.add(dto4);

        // Step 1: provide knowledge
        when(service.getRatingsByRatingArea(RatingArea.COMPLEXITY)).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<RatingDto> out = controller.getRatings(RatingArea.COMPLEXITY);

        assertEquals(out.size(), listDtos.size());
        out.forEach(o -> assertEquals(o.ratingArea, RatingArea.COMPLEXITY));
    }

}
