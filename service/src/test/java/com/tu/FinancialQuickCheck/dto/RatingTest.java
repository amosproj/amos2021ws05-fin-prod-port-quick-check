//package com.tu.FinancialQuickCheck.dto;
//
//import com.tu.FinancialQuickCheck.Score;
//import org.junit.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RatingTest {
//
//    @Test
//    public void createRatingTest()
//    {
//        int id = 1;
//        String criterion = "testCriterion";
//        Score score = Score.GERING;
//        String criterionValue = "testValue";
//        String comment = "testCommet";
//        RatingDto rating = new RatingDto(id, criterion, score, criterionValue, comment);
//        assertEquals(criterion, rating.criterion);
//        assertEquals(score, rating.score);
//        assertEquals(criterionValue, rating.criterionValue);
//        assertEquals(comment, rating.comment);
//    }
//
//    @Test
//    public void createRatingTest_emptyString()
//    {
//        int id = 1;
//        String criterion = "";
//        Score score = Score.GERING;
//        String criterionValue = "";
//        String comment = "";
//        RatingDto rating = new RatingDto(id, criterion, score, criterionValue, comment);
//        assertEquals(criterion, rating.criterion);
//        assertEquals(score, rating.score);
//        assertEquals(criterionValue, rating.criterionValue);
//        assertEquals(comment, rating.comment);
//    }
//
//    @Test
//    public void createRatingTest_nullString()
//    {
//        int id = 1;
//        Score score = Score.GERING;
//        RatingDto rating = new RatingDto(id, null, score, null, null);
//        assertNull(rating.criterion);
//        assertEquals(score, rating.score);
//        assertNull(rating.criterionValue);
//        assertNull(rating.comment);
//    }
//
//}