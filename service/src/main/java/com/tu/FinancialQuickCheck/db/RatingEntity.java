package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;

import javax.persistence.*;


@Entity
public class RatingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "criterion")
    public String criterion;

    @Column(name = "category")
    public String category;

    @Column(name = "ratingarea")
    public RatingArea ratingarea;

}
