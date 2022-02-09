package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the product data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class ProductDto {

    public int  productID;
    public String productName;
    public ProductAreaDto productArea;
    public int projectID;
    public int parentID;
    public float progressComplexity;
    public float progressEconomic;
    public List<ProductRatingDto> ratings;
    public List<ProductDto> productVariations;
    public String comment;
    public List<String> resources;


    public ProductDto(){}

    /**
     * Class Constructor specifying attributes through product entity and product rating entities
     * used in ProductRatingService.class
     */
    public ProductDto(ProductEntity product, List<ProductRatingEntity> productRatingEntities)
    {
        this.comment = product.comment;
        this.projectID = product.project.id;
        this.productID = product.id;
        this.productName = product.name;
        this.parentID = convertParentEntity(product.parentProduct);
        this.ratings = convertProductRatingEntities(productRatingEntities);
    }

    /**
     * Class Constructor specifying attributes through product entity and result of calculateProgress()
     * used in ProductService.class to get products
     */
    public ProductDto(ProductEntity product, float[] progress){
        this.productID = product.id;
        this.productName = product.name;
        this.productArea = new ProductAreaDto(product.productarea);
        this.projectID = product.project.id;
        this.parentID = convertParentEntity(product.parentProduct);
        this.progressComplexity = progress[1];
        this.progressEconomic = progress[0];
        this.comment = product.comment;
        this.resources = new ArrayList<>();
    }

    /**
     * Class Constructor specifying attributes through product entity, used in ProductService.class to create products
     */
    public ProductDto(ProductEntity product){
        this.productID = product.id;
        this.productName = product.name;
        this.productArea = new ProductAreaDto(product.productarea);
        this.projectID = product.project.id;
        this.parentID = convertParentEntity(product.parentProduct);
        this.progressComplexity = 0;
        this.progressEconomic = 0;
        this.comment = product.comment;
        this.resources = new ArrayList<>();
    }

    /**
     * Converts list of product rating entities into product rating dtos
     */
    private List<ProductRatingDto> convertProductRatingEntities(List<ProductRatingEntity> productRatingEntities) {
        List<ProductRatingDto> tmp = new ArrayList<>();

        for(ProductRatingEntity entity : productRatingEntities){
                tmp.add(new ProductRatingDto(entity));
        }
        return tmp;
    }

    /**
     * Retrieves parent entity id if exist, else 0
     */
    public int convertParentEntity(ProductEntity parentEntity) {
        if(parentEntity != null){
            return parentEntity.id;
        }else{
            return 0;
        }
    }

    /**
     * Returns True if parentID != 0, else False
     */
    public boolean isProductVariant(){
        if(this.parentID != 0){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
