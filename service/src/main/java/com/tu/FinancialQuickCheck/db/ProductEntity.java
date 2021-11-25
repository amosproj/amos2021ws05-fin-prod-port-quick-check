package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int product_id;

    @Column(name = "name")
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid", updatable = false)
    public ProjectEntity projectid;

    @Column(name = "productareaid")
    public int productareaid;

    @ManyToOne(fetch = FetchType.LAZY)
    public ProductEntity parentProduct;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="parentProduct")
    public List<ProductEntity> subProducts = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "productid", insertable = false, updatable = false)
    public List<ProductRatingEntity> productRatingEntities;
}
