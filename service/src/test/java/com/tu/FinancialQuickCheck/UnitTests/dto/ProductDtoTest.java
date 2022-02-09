package com.tu.FinancialQuickCheck.UnitTests.dto;

import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


public class ProductDtoTest {

    static Logger log = Logger.getLogger(ProductDtoTest.class.getName());

    ProductDto dto1;
    ProductDto dto2;
    ProductDto dto3;
    ProductEntity parent;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductDto.class");

        // Define Dto
        dto1 = new ProductDto();
        dto1.parentID = 0;

        dto2 = new ProductDto();
        dto2.parentID = 1;

        dto3 = new ProductDto();

        // Define entity;
        parent = new ProductEntity();
        parent.id = 1;
    }

    /**
     * tests for convertParentEntiity()
     *
     * testConvertParentEntiity: parent is null -> parentID is 0
     * testConvertParentEntiity: parent is not null -> parentID is parent entity id
     */
    @Test
    public void testConvertParentEntiity_parentIsNull(){
        ProductDto dto = new ProductDto();
        int out = dto.convertParentEntity(new ProductEntity());
        assertEquals(0, out);
    }

    @Test
    public void testConvertParentEntity_parentIsNotNull(){
        ProductDto dto = new ProductDto();
        int out = dto.convertParentEntity(parent);
        assertEquals(parent.id, out);
    }

    /**
     * tests for isProductVariant()
     *
     * testIsProductVariant: isProductVariant --> return True
     * testIsProductVariant: isProductVariant --> return False
     */
    @Test
    public void testIsProductVariant_true(){
        assertTrue(dto2.isProductVariant());
    }

    @Test
    public void testIsProductVariant_false(){
        assertFalse(dto1.isProductVariant());
        assertFalse(dto3.isProductVariant());
    }

}
