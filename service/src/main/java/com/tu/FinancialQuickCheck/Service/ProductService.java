package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }


    public ProductDto createProduct(int projectID,  int productAreaID, ProductDto productDto){
        ProductEntity newProduct = new ProductEntity();
        newProduct.projectid = projectID;
        newProduct.productareaid = productAreaID;
        productRepository.save(newProduct);
        productDto.id = newProduct.id;
        return productDto;
    }


    public ProductDto findById(int productID) {

        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            return new ProductDto(productEntity.get().id, productEntity.get().name, productEntity.get().projectid,
                productEntity.get().productareaid);
        }
    }


    public void updateById(ProductDto productDto, Integer productID) {

        if (!productRepository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            productRepository.findById(productID).map(
                    product -> {
                        product.name = productDto.name;
                        return productRepository.save(product);
                    });
        }
    }


    public void deleteProduct(int productID) {
        Optional<ProductEntity> productEntity = productRepository.findById(productID);
        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            productRepository.deleteById(productID);
        }
    }


    public List<ProductDto> getProductsByProjectId(int projectID){

        List<ProductDto> productsByProject = new ArrayList<>() {
        };
        Iterable<ProductEntity> productEntities = productRepository.findByProjectid(projectID);

        for(ProductEntity tmp : productEntities){
            productsByProject.add(new ProductDto(tmp.id, tmp.name, tmp.projectid, tmp.productareaid));
        }

        return productsByProject;
    }


    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int projectAreaID){

        List<ProductDto> productsByProjectAndProductArea = new ArrayList<>() {
        };
        Iterable<ProductEntity> productEntities = productRepository.findByProjectidAndProductareaid(projectID,
                projectAreaID);

        for(ProductEntity tmp : productEntities){
            productsByProjectAndProductArea.add(new ProductDto(tmp.id, tmp.name, tmp.projectid, tmp.productareaid));
        }

        return productsByProjectAndProductArea;
    }


}
