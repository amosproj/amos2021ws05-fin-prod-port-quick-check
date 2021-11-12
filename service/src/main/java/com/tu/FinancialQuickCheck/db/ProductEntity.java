package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "project_id")
    public int project_id;

    @Column(name = "productArea_id")
    public int productArea_id;


//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    public List<ProductEntity> productVariations;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public List<RatingEntity> ratingEntities;
}
