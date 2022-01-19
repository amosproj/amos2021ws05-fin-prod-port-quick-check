package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import java.util.Objects;

/**
 * This class represents the product area data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class ProductAreaDto {

    /**
     * ID of product area.
     */
    public int id;

    /**
     * Name of the product area which can be credit, client or payment.
     */
    public String name;

    /**
     * Category of the product area which can be privat or corporate.
     */
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

    /**
     * Gives back the hashcode value of ProductAreaDto.
     *
     * @return Hashcode value of ProductAreaDto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, category);
    }
}
