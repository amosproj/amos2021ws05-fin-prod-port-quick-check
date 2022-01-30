package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

/**
 * The ProductAreaEntity-class represents the product area‘s database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity // This tells Hibernate to make a table out of this class
public class ProductAreaEntity {

    /**
     * ID of the product areas (as primary key).
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    /**
     * Name of the product area which is credit, payment or client.
     */
    @Column(name = "name")
    public String name;

    /**
     * Category of the product area which is either privat or business.
     */
    @Column(name = "category")
    public String category;

    /**
     * An entity of the product. One product area can include n products.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productarea")
    public List<ProductEntity> productEntities;


}