package com.tu.FinancialQuickCheck.db;

import org.aopalliance.intercept.Interceptor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The ID of a product. Products are included in projects and have one product area.
 */
@Embeddable
public class ProductId implements Interceptor, Serializable {

    /**
     * Project in which products (and therefore productIDs) are included.
     */
    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    public ProjectEntity project;

    /**
     * Products (and therefore productIDs) have one product area.
     */
    @ManyToOne(targetEntity = ProductAreaEntity.class , fetch = FetchType.EAGER)
    @JoinColumn(name = "productarea")
    public ProductAreaEntity productarea;

    /**
     * No-argument Constructor for class ProductID.
     */
    public ProductId(){}

    /**
     * Constructor for class ProductID.
     *
     * @param productArea Product area of product (and therefore productID).
     * @param project Project in which product (and therefore productID) is included.
     */
    public ProductId(ProductAreaEntity productArea, ProjectEntity project){
        this.productarea = productArea;
        this.project = project;
    }

    public ProductAreaEntity getProductarea() {
        return productarea;
    }

    public void setProductarea(ProductAreaEntity productarea) {
        this.productarea = productarea;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    /**
     * Compares two productIDs based on the product area and project.
     *
     * @param o productID which has to be compared.
     * @return True when the two productIDs are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(productarea, productId.productarea) && Objects.equals(project, productId.project);
    }

    /**
     * Gives back the hashcode value of productID.
     *
     * @return Hashcode value of productID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(productarea, project);
    }
}