package com.tu.FinancialQuickCheck.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ProductAreaDtoTest {

    static Logger log = Logger.getLogger(ProductAreaDtoTest.class.getName());

    ProductAreaDto productArea1;
    ProductAreaDto productArea1_copy;
    ProductAreaDto productArea2;
    ProductAreaDto productArea2_copy;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductAreaDto.class");

        // Define ProductAreaEntities
        String name1 = "Kredit";
        String name2 = "Payment";
        String category1 = "Privat";
        String category2 = "Business";
        productArea1 = new ProductAreaDto();
        productArea1.name = name1;
        productArea1.category = category1;
        productArea2 = new ProductAreaDto();
        productArea2.name = name2;
        productArea2.category = category2;

        productArea1_copy = new ProductAreaDto();
        productArea1_copy.name = name1;
        productArea1_copy.category = category1;
        productArea2_copy = new ProductAreaDto();
        productArea2_copy.name = name2;
        productArea2_copy.category = category2;
    }

    /**
     * tests for equals()
     *
     * testEqualsTrue: two objects are equal --> return True
     * testEqualsTrue: two objects are  NOT equal --> return False
     */
    @Test
    public void testEquals_true() {
        assertTrue(productArea1.equals(productArea1_copy));
        assertTrue(productArea2.equals(productArea2_copy));
    }

    @Test
    public void testEquals_false() {
        assertFalse(productArea2.equals(productArea1));
        assertFalse(productArea1.equals(productArea2));
    }

    /**
     * tests for hashCode()
     *
     * testHashCode: hashCodes of the same object are equal
     * testHashCode: hashCodes of the two different objects are NOT equal
     */
    @Test
    public void testHashCode_equal() {
        assertEquals(productArea1.hashCode(), productArea1_copy.hashCode());
        assertEquals(productArea2.hashCode(), productArea2_copy.hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testHashCode_notEqual() {
        assertNotEquals(productArea1.hashCode(), productArea2.hashCode());
    }


    @Test
    public void createProductAreaTest()
    {
        int id = 1;
        String name = "testCriterion";
        String category = "category";
        ProductAreaDto productAreaDto = new ProductAreaDto(id, name, category);
        assertEquals(name, productAreaDto.name);
        assertEquals(category, productAreaDto.category);
        assertEquals(id, productAreaDto.id);
    }

    @Test
    public void createProductAreaTest_emptyString()
    {
        int id = 1;
        String name = "";
        String category = "";
        ProductAreaDto productAreaDto = new ProductAreaDto(id, name, category);
        assertEquals(name, productAreaDto.name);
        assertEquals(category, productAreaDto.category);
        assertEquals(id, productAreaDto.id);
    }

    @Test
    public void createProductAreaTest_nullString()
    {
        int id = 1;
        ProductAreaDto productAreaDto = new ProductAreaDto(id, null, null);
        assertEquals(id, productAreaDto.id);
        assertNull(productAreaDto.name);
        assertNull(productAreaDto.category);
    }

}