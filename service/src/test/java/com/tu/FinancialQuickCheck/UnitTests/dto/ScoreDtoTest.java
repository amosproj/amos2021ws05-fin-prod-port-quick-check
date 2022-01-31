package com.tu.FinancialQuickCheck.UnitTests.dto;

import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.dto.ResultDto;
import com.tu.FinancialQuickCheck.dto.ScoreDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreDtoTest {

    static Logger log = Logger.getLogger(ScoreDtoTest.class.getName());

    ScoreDto dto1;
    ScoreDto dto1Copy;
    ScoreDto dto2;
    ScoreDto dto2Copy;
    ScoreDto dto3;
    ScoreDto dto3Copy;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ScoreDto.class");

        dto1 = new ScoreDto();
        dto1.score = Score.HOCH;
        dto1.count = 6;

        dto1Copy = new ScoreDto();
        dto1Copy.score = Score.HOCH;
        dto1Copy.count = 6;

        dto2 = new ScoreDto();
        dto2.score = Score.GERING;
        dto2.count = 4;

        dto2Copy = new ScoreDto();
        dto2Copy.score = Score.GERING;
        dto2Copy.count = 4;

        dto3 = new ScoreDto();
        dto3.score = Score.MITTEL;
        dto3.count = 7;

        dto3Copy = new ScoreDto();
        dto3Copy.score = Score.MITTEL;
        dto3Copy.count = 7;
    }

    /**
     * tests for equals()
     *
     * testEqualsTrue: two objects are equal --> return True
     * testEqualsTrue: two objects are  NOT equal --> return False
     */
    @Test
    public void testEquals_true(){
        assertTrue(dto1.equals(dto1Copy));
        assertTrue(dto2.equals(dto2Copy));
        assertTrue(dto3.equals(dto3Copy));
    }

    @Test
    public void testEquals_false(){
        assertFalse(dto1.equals(dto2));
        assertFalse(dto1.equals(dto3));
        assertFalse(dto2.equals(dto1));
        assertFalse(dto2.equals(dto3));
        assertFalse(dto3.equals(dto1));
        assertFalse(dto3.equals(dto2));
    }

    /**
     * tests for hashCode()
     *
     * testHashCode: hashCodes of the same object are equal
     * testHashCode: hashCodes of the two different objects are NOT equal
     */

    @Test
    public void testHashCode_equal(){
        assertEquals(dto1Copy.hashCode(), dto1.hashCode());
        assertEquals(dto2Copy.hashCode(), dto2.hashCode());
        assertEquals(dto3Copy.hashCode(), dto3.hashCode());
    }

    @Test
    public void testHashCode_notEqual(){
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
        assertNotEquals(dto3.hashCode(), dto2.hashCode());
    }

}
