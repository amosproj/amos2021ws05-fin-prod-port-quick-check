package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Score;

import javax.persistence.*;

@Entity
public class ProductRatingEntity {
    // TODO: primary key aus productid & questionid erstellen
    // TODO: überlegen wie Dateien gespeicht werden, und ob hier ein Verweis notwendig ist
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "answer")
    public String answer;

    // TODO: wie gehen wir mit anderen Scalenwerten um?
    @Column(name = "score")
    public Score score;

    @Column(name = "comment")
    public String comment;

    @ManyToOne (targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    public ProductEntity product;

    @ManyToOne(targetEntity = RatingEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ratingid", insertable = false, updatable = false)
    public RatingEntity rating;

    public ProductRatingEntity(){};
}
