package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;


@Entity
public class ProductEntity {
    // TODO: (ask frontend) confirm attribute for overallEconomicRating?

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "overallRating")
    public Boolean overallEconomicRating;

    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    public ProjectEntity project;

    @ManyToOne(targetEntity = ProductAreaEntity.class , fetch = FetchType.EAGER)
    @JoinColumn(name = "productarea")
    public ProductAreaEntity productarea;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product", insertable = false, updatable = false)
    public List<ProductRatingEntity> productRatingEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    public ProductEntity parentProduct;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy="parentProduct")
//    public List<ProductEntity> subProducts = new ArrayList<>();

    public ProductEntity(){}
}
