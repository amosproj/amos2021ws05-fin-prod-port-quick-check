package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Score;

import javax.persistence.*;

@Entity
public class ProductRatingEntity {
    // TODO: überlegen wie Dateien gespeicht werden, und ob hier ein Verweis notwendig ist

    @EmbeddedId
    public ProductRatingId productRatingId;

    @Column(name = "answer")
    public String answer;

    // TODO: wie gehen wir mit anderen Scalenwerten um?
    @Column(name = "score")
    public Score score;

    @Column(name = "comment")
    public String comment;

    public ProductRatingEntity(){}
}
