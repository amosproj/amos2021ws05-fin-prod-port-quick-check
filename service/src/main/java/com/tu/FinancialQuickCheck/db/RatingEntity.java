package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Score;

import javax.persistence.*;
import java.util.List;

@Entity
public class RatingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "productid")
    public int productid;

    @Column(name = "criterion")
    public String criterion;

    @Column(name = "score")
    public Score score;

    @Column(name = "criterionValue")
    public String criterionValue;

    @Column(name = "comment")
    public String comment;

    @Column(name = "area")
    public RatingArea area;

    //public List<String> sources;
}
