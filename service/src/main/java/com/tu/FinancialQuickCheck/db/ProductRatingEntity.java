package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Score;

import javax.persistence.*;

/**
 * The ProductRatingEntity-class represents the product rating's database table, in which each class‘ attribute corresponds
 * to a row
 *
 * ID of the product rating (as composite primary key).
 * Answer for questions out of complexity evaluation entered by the user.
 * Score out of complexity evaluation entered by user (high, mid and low).
 * Comment about the rating entered by user (e.g. an explanation for a specific product rating choice).
 */
@Entity
public class ProductRatingEntity {
    // TODO: (tbd) überlegen wie Dateien gespeicht werden, und ob hier ein Verweis notwendig ist

    @EmbeddedId
    public ProductRatingId productRatingId;

    @Column(name = "answer")
    public String answer;

    // TODO: (prio: low) wie gehen wir mit anderen Scalenwerten um?
    @Column(name = "score")
    public Score score;

    @Column(name = "comment")
    public String comment;

    public ProductRatingEntity(){}
}
