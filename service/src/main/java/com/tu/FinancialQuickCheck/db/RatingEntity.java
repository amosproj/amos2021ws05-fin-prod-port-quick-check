package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import javax.persistence.*;

/**
 * The RatingEntity-class represents the rating's database table, in which each class‘ attribute corresponds
 * to a column
 *
 * id: unique identifier of rating entity
 * criterion: The criterion represents either a question of the complexity evaluation
 *    or a criteria of the economic evaliation (e.g. margin, credit volume,...).
 * category: The category groups different criterions and a category represents for example
 *    a complexity driver (e.g. regulatory, payment terms, documentation,..).
 * ratingarea: The ratingarea groups different ratings entities and
 *    a RatingArea represents either the economic or complexity evaluation of a product
*/
@Entity
public class RatingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    @Column(name = "criterion")
    public String criterion;

    @Column(name = "category")
    public String category;

    @Column(name = "ratingarea")
    public RatingArea ratingarea;

}
