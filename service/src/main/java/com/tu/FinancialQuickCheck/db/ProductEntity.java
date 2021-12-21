package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;


@Entity
public class ProductEntity {
    //TODO: (done: needs review) add missing attribute: comment
    //TODO: (prio: medium) (Topic: Dateien abspeichern) add missing attribute: list of resources or something else (?)

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

//    @OneToMany(fetch = FetchType.LAZY, mappedBy="parentProduct")
//    public List<ProductEntity> subProducts = new ArrayList<>();

    public ProductEntity(){}
}
