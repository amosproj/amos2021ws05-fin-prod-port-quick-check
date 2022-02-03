package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

/**
 * The ProductEntity-class represents the product's database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity
public class ProductEntity {
    /**
     * ID of the product (as primary key).
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    /**
     * Name of the product (e.g. mortage, loan credit, dispo credit..).
     */
    @Column(name = "name")
    public String name;

    @Column(name = "comment")
    public String comment;

    /**
     * Name of the project. Usually identical with the name of the financial institution using the software.
     */
    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project")
    public ProjectEntity project;

    /**
     * All products have one product area which is credit, payment or client.
     */
    @ManyToOne(targetEntity = ProductAreaEntity.class , fetch = FetchType.EAGER)
    @JoinColumn(name = "productarea")
    public ProductAreaEntity productarea;

    /**
     * All products can be rated. The rating consists of an answer, score and an optional comment.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product")
    public List<ProductRatingEntity> productRatingEntities;

    /**
     * Some products can have a parent product (e.g. dispo credit is the parent of dispo credit-flex and -normal).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    public ProductEntity parentProduct;

    /**
     * Constructor for class ProductEntity.
     */
    public ProductEntity(){}
}
