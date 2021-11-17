package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductAreaDtoTest {

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