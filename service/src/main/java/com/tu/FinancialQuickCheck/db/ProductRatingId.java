package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductRatingId implements Serializable {

    //TODO: (done - needs review) remove ...id in Name declarations
    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private ProductEntity product;

    //TODO: (done - needs review) remove ...id in Name declarations
    //comment: do not change fetchType
    @ManyToOne(targetEntity = RatingEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "rating", updatable = false)
    private RatingEntity rating;

    public ProductRatingId(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRatingId that = (ProductRatingId) o;
        return product.equals(that.product) && rating.equals(that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, rating);
    }
}
