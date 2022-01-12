package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Score;

import javax.persistence.*;

/**
 * The ProductRatingEntity-class represents the product rating's database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity
public class ProductRatingEntity {
    // TODO: (tbd) überlegen wie Dateien gespeicht werden, und ob hier ein Verweis notwendig ist

    /**
     * ID of the product rating (as composite primary key).
     */
    @EmbeddedId
    public ProductRatingId productRatingId;

    /**
     * Answer for questions out of complexity evaluation entered by the user.
     */
    @Column(name = "answer")
    public String answer;

    /**
     * Score out of complexity evaluation entered by user (high, mid and low).
     */
    // TODO: (prio: low) wie gehen wir mit anderen Scalenwerten um?
    @Column(name = "score")
    public Score score;

    /**
     * Comment about the rating entered by user (e.g. an explanation for a specific product rating choice).
     */
    @Column(name = "comment")
    public String comment;

    /**
     * Constructor for class ProductRatingEntity.
     */
    public ProductRatingEntity(){}
}
