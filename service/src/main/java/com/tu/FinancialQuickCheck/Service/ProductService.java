package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository repository;
    private ProjectRepository projectRepository;
    private ProductAreaRepository productAreaRepository;

    public ProductService(ProductRepository productRepository, ProjectRepository projectRepository,
                          ProductAreaRepository productAreaRepository) {
        this.repository = productRepository;
        this.projectRepository = projectRepository;
        this.productAreaRepository = productAreaRepository;
    }


    public ProductDto createProduct(int projectID, int productAreaID, ProductDto productDto){
        if(projectRepository.existsById(projectID)
                && productAreaRepository.existsById(productAreaID)){
            if(productDto.productName != null){

                List<ProductEntity> entities = new ArrayList<>();
                ProductEntity newProduct = new ProductEntity();
                newProduct.name = productDto.productName;
                newProduct.projectid = projectRepository.findById(projectID).get();
                newProduct.productareaid = productAreaID;
                entities.add(newProduct);

                if(productDto.productVariations != null){
                    for (ProductDto productVariation: productDto.productVariations) {
                        ProductEntity entity = new ProductEntity();
                        entity.name = productVariation.productName;
                        entity.projectid = projectRepository.findById(projectID).get();
                        entity.productareaid = productAreaID;
                        entity.parentProduct = repository.getById(newProduct.product_id);
                        entities.add(entity);
                    }
                }

                repository.saveAll(entities);
                return new ProductDto(newProduct.product_id, newProduct.name, newProduct.projectid.id,
                        newProduct.productareaid);
            }else{
                return null;
            }

        }else {
            throw new ResourceNotFound("Resource not Found. ProjectID and/or ProjectAreaID does not exist.");
        }
    }


    public ProductDto findById(int productID) {

        Optional<ProductEntity> productEntity = repository.findById(productID);

        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            return new ProductDto(productEntity.get().product_id, productEntity.get().name,
                    productEntity.get().projectid.id, productEntity.get().productareaid);
        }
    }


    public ProductDto updateById(ProductDto productDto, int productID) {

        if (!repository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            if(productDto.productName != null){
                repository.findById(productID).map(
                        product -> {
                            product.name = productDto.productName;
                            return repository.save(product);
                        });
//                new ProductDto(product.product_id, product.name, product.projectid.id,
//                        product.productareaid);
                return new ProductDto();
            }else{
                return null;
            }
        }
    }


    public void deleteProduct(int productID) {
        Optional<ProductEntity> productEntity = repository.findById(productID);
        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            repository.deleteById(productID);
        }
    }


    public List<ProductDto> getProductsByProjectId(int projectID){
        List<ProductDto> productsByProject = new ArrayList<>();
        Iterable<ProductEntity> productEntities = repository.findByProjectid(projectRepository.findById(projectID).get());


        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")){
                ProductDto addProduct = new ProductDto(tmp.product_id, tmp.name, tmp.projectid.id, tmp.productareaid);
                productsByProject.add(addProduct);
            }
        }

        return productsByProject;
    }


    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int projectAreaID){

        List<ProductDto> productsByProjectAndProductArea = new ArrayList<>() {
        };
        Iterable<ProductEntity> productEntities = repository.findByProjectidAndProductareaid(
                projectRepository.findById(projectID).get(),
                projectAreaID);

        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")) {
                productsByProjectAndProductArea.add(
                        new ProductDto(
                            tmp.product_id,
                            tmp.name,
                            tmp.projectid.id,
                            tmp.productareaid
                            ));
            }
        }

        return productsByProjectAndProductArea;
    }


}
