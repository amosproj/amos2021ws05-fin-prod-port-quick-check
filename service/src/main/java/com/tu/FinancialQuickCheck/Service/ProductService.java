package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return new ProductDto(productEntity.get());
        }
    }

    public List<ProductDto> wrapper_createProduct(int projectID, ProductDto productDto){
        List<ProductDto> out = new ArrayList<>();
        List<ProductDto> tmp = createProduct(projectID, productDto);
        if(tmp != null){
            for(ProductDto dto: tmp){
                ProductDto dtoOut = findById(dto.productID);
                out.add(dtoOut);
            }

            return out;
        }else{
            return null;
        }

    }

    //TODO: (done - needs review) change return to List
    @Transactional
    public List<ProductDto>  createProduct(int projectID, ProductDto productDto){

        if(repository.existsByProjectAndProductarea(
                projectRepository.getById(projectID),
                productAreaRepository.getById(productDto.productArea.id)))
        {
            List<ProductEntity> entities = new ArrayList<>();

            ProductEntity newProduct = createProductEntity(projectID, productDto.productArea.id, productDto, false, null);
            if(newProduct != null){
                entities.add(newProduct);
            }else{
                return null;
            }

            if(productDto.productVariations != null){
                for (ProductDto productVariation: productDto.productVariations) {
                    ProductEntity entity = createProductEntity(projectID, productDto.productArea.id, productVariation, true, newProduct);
                    if(entity != null){
                        entities.add(entity);
                    }else{
                        return null;
                    }
                }
            }

            entities = repository.saveAllAndFlush(entities);

            List<ProductDto> createdProducts = new ArrayList<>();
            entities.forEach(entity -> createdProducts.add(new ProductDto(entity)));
            return createdProducts;

        }else {
            throw new ResourceNotFound("Resource not Found. ProjectID and/or ProjectAreaID does not exist.");
        }
    }

    public ProductEntity createProductEntity(int projectID, int productAreaID, ProductDto dto, Boolean isProductVariant, ProductEntity parentEntity){

        if(dto.productName != null && !dto.productName.isBlank()) {
            ProductEntity newProduct = new ProductEntity();
            newProduct.name = dto.productName;
            newProduct.project = projectRepository.findById(projectID).get();
            newProduct.productarea = productAreaRepository.getById(productAreaID);
            if (dto.comment != null) {newProduct.comment = dto.comment;}
            if(isProductVariant) {newProduct.parentProduct = parentEntity;}
            return newProduct;
        }else{
            return null;
        }
    }


    public ProductDto updateById(ProductDto productDto, int productID) {

        if (!repository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            repository.findById(productID).map(
                    product -> {
                        updateProductName(product, productDto);
                        updateProductComment(product, productDto);
                        return repository.save(product);
                    });
            return new ProductDto();
        }
    }

    private void updateProductComment(ProductEntity currentEntity, ProductDto updateDto) {
        if (updateDto.comment != null) {
            if (updateDto.comment.isBlank()) {
                currentEntity.comment = null;
            } else {
                currentEntity.comment = updateDto.comment;
            }
        }
    }


    private void updateProductName(ProductEntity currentEntity, ProductDto updateDto) {
        if (updateDto.productName != null){
            if(updateDto.productName.isBlank()){
                throw new BadRequest("Input is missing/incorrect");
            }else{
                currentEntity.name = updateDto.productName;
            }
        }
    }


    public List<ProductDto> getProductsByProjectId(int projectID){

        if(projectRepository.existsById(projectID)){
            List<ProductDto> productsByProject = new ArrayList<>();
            Iterable<ProductEntity> productEntities = repository.findByProject(projectRepository.findById(projectID).get());
            for(ProductEntity tmp : productEntities){
                if(!tmp.name.equals("DUMMY")){
                    ProductDto addProduct = new ProductDto(tmp);
                    productsByProject.add(addProduct);
                }
            }
            return productsByProject;
        }else{
            throw new ResourceNotFound("project " + projectID + "does not exist.");
        }
    }


    //TODO: (discuss with Alex) return Resource not found if projectAreaID does not exist?
    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int projectAreaID){

        List<ProductDto> productsByProjectAndProductArea = new ArrayList<>();
        Iterable<ProductEntity> productEntities = repository.findByProjectAndProductarea(
                projectRepository.findById(projectID).get(),
                productAreaRepository.getById(projectAreaID));

        for(ProductEntity tmp : productEntities){
            if(!tmp.name.equals("DUMMY")) {
                productsByProjectAndProductArea.add(new ProductDto(tmp));
            }
        }

        return productsByProjectAndProductArea;
    }



//    public void deleteProduct(int productID) {
//        // TODO: (ask PO) was soll mit ProductVarianten beim löschen passieren? sollen die auch mit gelöscht werden?
//        Optional<ProductEntity> productEntity = repository.findById(productID);
//
//        if (productEntity.isEmpty()) {
//            throw new ResourceNotFound("productID " + productID + " not found");
//        }else{
//            repository.deleteById(productID);
//        }
//    }


}
