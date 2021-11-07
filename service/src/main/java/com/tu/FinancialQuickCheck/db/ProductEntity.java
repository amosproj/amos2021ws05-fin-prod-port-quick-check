package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int product_id;

    @Column(name = "name")
    public String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    public List<ProductEntity> productVariations;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rating_id")
    public List<RatingEntity> ratingEntities;
}
