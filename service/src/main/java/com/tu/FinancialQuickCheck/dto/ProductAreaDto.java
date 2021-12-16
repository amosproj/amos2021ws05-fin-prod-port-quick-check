package com.tu.FinancialQuickCheck.dto;


import com.tu.FinancialQuickCheck.db.ProductAreaEntity;

import java.util.Objects;

public class ProductAreaDto {

    public int id;
    public String name;
    public String category;

    public ProductAreaDto(){}

    public ProductAreaDto(int id){
        this.id = id;
    }

    public ProductAreaDto(int id, String name, String category){
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public ProductAreaDto(ProductAreaEntity productAreaEntity){
        this.id = productAreaEntity.id;
        this.name = productAreaEntity.name;
        this.category = productAreaEntity.category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAreaDto that = (ProductAreaDto) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category);
    }
}
