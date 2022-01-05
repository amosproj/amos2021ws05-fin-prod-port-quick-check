package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;


public class ProductRatingIdTest {

    static Logger log = Logger.getLogger(ProductRatingIdTest.class.getName());

    ProductRatingId id1;
    ProductRatingId id2;
    ProductEntity product1;
    ProductEntity product2;
    ProductAreaEntity productArea1;
    ProductAreaEntity productArea2;
    RatingEntity rating1;
    RatingEntity rating2;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProductRatingId.class");

        // Define ProductAreaEntities
        String name1 = "Kredit";
        String name2 = "Payment";
        String category1 = "Privat";
        String category2 = "Business";
        productArea1 = new ProductAreaEntity();
        productArea1.name = name1;
        productArea1.category = category1;
        productArea2 = new ProductAreaEntity();
        productArea2.name = name2;
        productArea2.category = category2;

        // Define ProductEntities
        product1 = new ProductEntity();
        product1.id = 1;
        product1.name = "Product 1";
        product1.productarea = productArea1;

        product2 = new ProductEntity();
        product2.id = 2;
        product2.name = "Product 2";
        product2.productarea = productArea2;

        // Define RatingEntities
        rating1 = new RatingEntity();
        rating1.id = 1;
        rating1.category = "category 1";
        rating1.criterion = "criterion 1";
        rating1.ratingarea = RatingArea.COMPLEXITY;

        rating2 = new RatingEntity();
        rating2.id = 2;
        rating2.category = "category 2";
        rating2.criterion = "criterion 2";
        rating2.ratingarea = RatingArea.ECONOMIC;

        // Define ProductRatingId
        id1 = new ProductRatingId();
        id1.setProduct(product1);
        id1.setRating(rating1);

        id2 = new ProductRatingId();
        id2.setProduct(product2);
        id2.setRating(rating2);
    }

    /**
     * tests for equals()
     *
     * testEqualsTrue: two objects are equal --> return True
     * testEqualsTrue: two objects are  NOT equal --> return False
     */
    @Test
    public void testEquals_true() {
        assertTrue(id1.equals(id1));
        assertTrue(id2.equals(id2));
    }

    @Test
    public void testEquals_false() {
        assertFalse(id1.equals(id2));
        assertFalse(id2.equals(id1));
    }

    /**
     * tests for hashCode()
     *
     * testHashCode: hashCodes of the same object are equal
     * testHashCode: hashCodes of the two different objects are NOT equal
     */
    @Test
    public void testHashCode_equal() {
        assertEquals(id1.hashCode(), id1.hashCode());
        assertEquals(id2.hashCode(), id2.hashCode());
    }

    @Test
    public void testHashCode_notEqual() {
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

}
