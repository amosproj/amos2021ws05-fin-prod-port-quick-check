package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "project_id")
    public String project_id;

    @Column(name = "name")
    public String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public List<ProductEntity> productVariations;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    public List<RatingEntity> ratingEntities;
}
