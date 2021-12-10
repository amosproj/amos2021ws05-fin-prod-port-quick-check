package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProductDto;
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

    public ProductDto findById(int productID) {
        Optional<ProductEntity> productEntity = repository.findById(productID);

        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            return new ProductDto(productEntity.get().id, productEntity.get().name,
                    productEntity.get().project.id, productEntity.get().productarea,
                    productEntity.get().parentProduct);
        }
    }


    //TODO: change return to List
    public ProductDto createProduct(int projectID, int productAreaID, ProductDto productDto){

        if(projectRepository.existsById(projectID)
                && productAreaRepository.existsById(productAreaID)){
            if(productDto.productName != null){

                List<ProductEntity> entities = new ArrayList<>();
                ProductEntity newProduct = new ProductEntity();
                newProduct.name = productDto.productName;
                newProduct.project = projectRepository.findById(projectID).get();
                newProduct.productarea = productAreaRepository.getById(productAreaID);
                entities.add(newProduct);

                if(productDto.productVariations != null){
                    for (ProductDto productVariation: productDto.productVariations) {
                        ProductEntity entity = new ProductEntity();
                        entity.name = productVariation.productName;
                        entity.project = projectRepository.findById(projectID).get();
                        entity.productarea = productAreaRepository.getById(productAreaID);
                        entity.parentProduct = newProduct;
                        entities.add(entity);
                    }
                }

                repository.saveAll(entities);
                return new ProductDto(newProduct.id, newProduct.name, newProduct.project.id,
                        newProduct.productarea, newProduct.parentProduct);
            }else{
                return null;
            }

        }else {
            throw new ResourceNotFound("Resource not Found. ProjectID and/or ProjectAreaID does not exist.");
        }
    }


    public ProductDto updateById(ProductDto productDto, int productID) {
        // TODO: soll update als Batch implementiert werden (d.h. auch für productvariations?)
        if (!repository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            if(productDto.productName != null && productDto.productName.length() > 0){
                repository.findById(productID).map(
                        product -> {
                            product.name = productDto.productName;
                            return repository.save(product);
                        });
                return new ProductDto();
            }else{
                return null;
            }
        }
    }


    public List<ProductDto> getProductsByProjectId(int projectID){
        List<ProductDto> productsByProject = new ArrayList<>();
        Iterable<ProductEntity> productEntities = repository.findByProject(projectRepository.findById(projectID).get());


        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")){
                ProductDto addProduct = new ProductDto(tmp.id, tmp.name, tmp.project.id, tmp.productarea,
                        tmp.parentProduct);
                productsByProject.add(addProduct);
            }
        }

        return productsByProject;
    }


    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int projectAreaID){

        List<ProductDto> productsByProjectAndProductArea = new ArrayList<>();
        Iterable<ProductEntity> productEntities = repository.findByProjectAndProductarea(
                projectRepository.findById(projectID).get(),
                productAreaRepository.getById(projectAreaID));

        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")) {
                productsByProjectAndProductArea.add(
                        new ProductDto(
                            tmp.id,
                            tmp.name,
                            tmp.project.id,
                            tmp.parentProduct
                            ));
            }
        }

        return productsByProjectAndProductArea;
    }


//    public void deleteProduct(int productID) {
//        // TODO: was soll mit ProductVarianten beim löschen passieren? sollen die auch mit gelöscht werden?
//        Optional<ProductEntity> productEntity = repository.findById(productID);
//
//        if (productEntity.isEmpty()) {
//            throw new ResourceNotFound("productID " + productID + " not found");
//        }else{
//            repository.deleteById(productID);
//        }
//    }


}
