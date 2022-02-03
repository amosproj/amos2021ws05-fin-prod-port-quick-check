package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Every product rating has an ID for identification.
 * Rating for a product, for which a productRatingID was given.
 */
@Embeddable
public class ProductRatingId implements Serializable {

    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product")
    private ProductEntity product;

    //comment: do not change fetchType
    @ManyToOne(targetEntity = RatingEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "rating", updatable = false)
    private RatingEntity rating;


    public ProductRatingId(){}

    /**
     * Constructor for class ProductRatingID.
     * @param product The product which was rated and therefore has a ProductRatingID
     * @param rating The conducted rating which has a ProductRatingID.
     */
    public ProductRatingId(ProductEntity product, RatingEntity rating){
        this.product = product;
        this.rating = rating;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    /**
     * Compares two productRatingIDs based on the product and rating.
     *
     * @param o productRatingID which has to be compared.
     * @return True when the two productRatingIDs are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRatingId that = (ProductRatingId) o;
        return product.equals(that.product) && rating.equals(that.rating);
    }



    /**
     * Gives back the hashcode value of product rating ID.
     *
     * @return Hashcode value of product rating ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(product, rating);
    }
}
