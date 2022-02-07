package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

/**
 * The ProductEntity-class represents the product's database table, in which each class‘ attribute corresponds
 * to a column in the db and an object of this class corresponds to an entry in the db table.
 *
 * id: The unique identifier of the product entity, created by db
 * name: A description of the product entity, can be used for display purposes, currently no restrictions on length, etc.
 * comment: A comment about the product entity, currently no restrictions on length, etc.
 * project: The project entity to which the product entity is assigned
 * productArea: The product area entity to which the product entity is assigned
 * productRatingEntities: List of product rating entities created when a new product entity is created
 * parentProduct: The parent product entity points to the parent entity of a product entity if it is a product variant
 */
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "comment")
    public String comment;

    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project")
    public ProjectEntity project;

    @ManyToOne(targetEntity = ProductAreaEntity.class , fetch = FetchType.EAGER)
    @JoinColumn(name = "productarea")
    public ProductAreaEntity productarea;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product")
    public List<ProductRatingEntity> productRatingEntities;

    @ManyToOne(fetch = FetchType.EAGER)
    public ProductEntity parentProduct;

    public ProductEntity(){}
}
