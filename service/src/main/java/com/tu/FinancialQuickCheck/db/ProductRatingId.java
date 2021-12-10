package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductRatingId implements Serializable {


    //TODO: remove ...id in Name declarations
    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", updatable = false)
    private ProductEntity productid;

    //TODO: remove ...id in Name declarations
    @ManyToOne(targetEntity = RatingEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ratingid", updatable = false)
    private RatingEntity ratingid;

    public ProductRatingId(){}

    public ProductRatingId(ProductEntity productID, RatingEntity ratingID){
        this.productid = productID;
        this.ratingid = ratingID;
    }

    public ProductEntity getProductid() {
        return productid;
    }

    public void setProductid(ProductEntity productid) {
        this.productid = productid;
    }

    public RatingEntity getRatingid() {
        return ratingid;
    }

    public void setRatingid(RatingEntity ratingid) {
        this.ratingid = ratingid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRatingId that = (ProductRatingId) o;
        return productid.equals(that.productid) && ratingid.equals(that.ratingid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productid, ratingid);
    }
}
