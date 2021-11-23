package com.tu.FinancialQuickCheck.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ProductEntity {

    @Id
    public int product_id;

    @Column(name = "name")
    public String name;

    @Column(name = "projectid")
    public int projectid;

    @Column(name = "productareaid")
    public int productareaid;

    @ManyToOne(fetch = FetchType.LAZY)
    public ProductEntity parentProduct;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="parentProduct")
    public List<ProductEntity> subProducts = new ArrayList<ProductEntity>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "productid", insertable = false, updatable = false)
    public List<ProductRatingEntity> productRatingEntities;
}
