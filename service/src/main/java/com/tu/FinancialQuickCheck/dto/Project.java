package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class Project {

    public String name;

    public int id;

    public List<Product> products;

    public Project(String name, int id, List<ProductEntity> productEntity)
    {
        this.name = name;
        this.id = id;
//        this.products = convertEntities(productEntity);
    }

//    private List<Product> convertEntities(List<ProductEntity> productEntity) {
//        List<Product> products = new ArrayList<>();
//        for (ProductEntity p:productEntity)
//        {
//            if(p.productVariations != null)
//                products.add(new Product(p.id, p.name, p.productVariations, p.ratingEntities));
//            else
//                products.add(new Product(p.id, p.name, p.ratingEntities));
//        }
//        return products;
//    }
}
