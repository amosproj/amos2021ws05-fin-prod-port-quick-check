package com.tu.FinancialQuickCheck.UnitTests.dto;

import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.dto.ResultDto;
import com.tu.FinancialQuickCheck.dto.ScoreDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


public class ResultDtoTest {

    static Logger log = Logger.getLogger(ResultDtoTest.class.getName());

    ResultDto dto1;
    ResultDto dto1Copy;
    ResultDto dto2;
    ResultDto dto2Copy;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ResultDto.class");

        // Define ResultDto
        dto1 = new ResultDto();
        dto1.productID = 100;
        dto1.productName = "Product 1";

        dto1Copy = new ResultDto();
        dto1Copy.productID = 100;
        dto1Copy.productName = "Product 1";

        dto2 = new ResultDto();
        dto2.productID = 200;
        dto2.productName = "Product 2";

        dto2Copy = new ResultDto();
        dto2Copy.productID = 200;
        dto2Copy.productName = "Product 2";
    }

    /**
     * tests for equals()
     *
     * testEqualsTrue: two objects are equal --> return True
     * testEqualsTrue: two objects are  NOT equal --> return False
     */
    @Test
    public void testEquals_true(){
        //TODO: discuss equals
        assertTrue(dto1.equals(dto1Copy));
        assertTrue(dto2.equals(dto2Copy));
    }

    @Test
    public void testEquals_false(){
        assertFalse(dto1.equals(dto2));
        assertFalse(dto2.equals(dto1));
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
    }

    @Test
    public void testHashCode_notEqual(){
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1Copy.hashCode(), dto2Copy.hashCode());
    }
}
