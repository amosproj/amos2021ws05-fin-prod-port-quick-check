package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;


@Entity
public class ProductEntity {
    // TODO: add attribute for overall economic rating

    @EmbeddedId
    public ProductId id;

    @Column(name = "name")
    public String name;



//    @ManyToOne(fetch = FetchType.LAZY)
//    public ProductEntity parentProduct;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy="parentProduct")
//    public List<ProductEntity> subProducts = new ArrayList<>();

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "productid", insertable = false, updatable = false)
//    public List<ProductRatingEntity> productRatingEntities;

    public ProductEntity(){}
}
