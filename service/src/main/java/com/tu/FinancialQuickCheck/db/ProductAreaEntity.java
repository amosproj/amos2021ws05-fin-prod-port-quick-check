package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;

/**
 * The ProductAreaEntity-class represents the product area‘s database table, in which each class‘ attribute corresponds
 * to a column
 *
 * id: The unique identifier of product area
 * name: The name of a product area, no restrictions currently
 * category: The category groups different product areas, no restrictions currently
 */
@Entity // This tells Hibernate to make a table out of this class
public class ProductAreaEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "category")
    public String category;

}