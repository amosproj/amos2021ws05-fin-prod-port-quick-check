package com.tu.FinancialQuickCheck.db;

import org.aopalliance.intercept.Interceptor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Interceptor, Serializable {

    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    public ProjectEntity project;

    @ManyToOne(targetEntity = ProductAreaEntity.class , fetch = FetchType.EAGER)
    @JoinColumn(name = "productarea")
    public ProductAreaEntity productarea;


    public ProductId(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(productarea, productId.productarea) && Objects.equals(project, productId.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productarea, project);
    }
}
