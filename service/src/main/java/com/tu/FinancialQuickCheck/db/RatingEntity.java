package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import javax.persistence.*;

/**
 * The RatingEntity-class represents the rating's database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity
public class RatingEntity {

    /**
     * ID of the rating (as primary key).
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    /**
     * Evaluation criteria which is answered by user (e.g. margin, credit volume,...).
     */
    @Column(name = "criterion")
    public String criterion;

    /**
     * Complexity drivers which summarizing questions for same topics (e.g. regulatory, payment terms, documentation,..).
     */
    @Column(name = "category")
    public String category;

    /**
     * Area in which the rating is exectued (economical or complexity).
     */
    @Column(name = "ratingarea")
    public RatingArea ratingarea;

}
