package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProjectRepository projectRepository) {

        this.productRepository = productRepository;
        this.projectRepository = projectRepository;
    }


    public ProductDto createProduct(int projectID, int productAreaID, ProductDto productDto){
    // TODO: erstellte IDs müssen ans Frontend kommuniziert werden
        ProductEntity newProduct = new ProductEntity();
        newProduct.name = productDto.productName;
        newProduct.projectid = projectRepository.findById(projectID).get();
        newProduct.productareaid = productAreaID;
        productRepository.save(newProduct);

        if(productDto.productVariations != null){
            for (ProductDto productVariation: productDto.productVariations) {
                ProductEntity entity = new ProductEntity();
                entity.name = productVariation.productName;
                entity.projectid = projectRepository.findById(projectID).get();
                entity.productareaid = productAreaID;
                entity.parentProduct = productRepository.getById(newProduct.product_id);
                productRepository.save(entity);
            }
        }


        productDto.projectID = newProduct.product_id;
        productDto.productAreaID = newProduct.productareaid;
        productDto.projectID = newProduct.projectid.id;
        return productDto;
    }


    public ProductDto findById(int productID) {

        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            return new ProductDto(productEntity.get().product_id,productEntity.get().name,
                    productEntity.get().projectid.id, productEntity.get().productareaid);
        }
    }


    public void updateById(ProductDto productDto, int productID) {

        if (!productRepository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            productRepository.findById(productID).map(
                    product -> {
                        product.name = productDto.productName;
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
        List<ProductDto> productsByProject = new ArrayList<>();
        Iterable<ProductEntity> productEntities = productRepository.findByProjectid(
                projectRepository.findById(projectID).get());

        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")){
                productsByProject.add(new ProductDto(tmp.product_id,tmp.name,
                        projectRepository.findById(projectID).get().id, tmp.productareaid));
            }
        }

        return productsByProject;
    }


    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int projectAreaID){

        List<ProductDto> productsByProjectAndProductArea = new ArrayList<>() {
        };
        Iterable<ProductEntity> productEntities = productRepository.findByProjectidAndProductareaid(
                projectRepository.findById(projectID).get(),
                projectAreaID);

        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")) {
                productsByProjectAndProductArea.add(
                        new ProductDto(
                            tmp.product_id,
                            tmp.name,
                            tmp.projectid.id,
                            tmp.productareaid,
                            tmp.parentProduct.product_id));
            }
        }

        return productsByProjectAndProductArea;
    }


}
