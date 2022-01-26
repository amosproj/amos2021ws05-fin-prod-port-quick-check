package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The ProductService class performs service tasks and defines the logic for the product. This includes creating,
 * updating, finding or giving back products
 */
@Service
public class ProductService {

    private ProductRepository repository;
    private ProjectRepository projectRepository;
    private ProductAreaRepository productAreaRepository;
    private RatingRepository ratingRepository;
    private ProductRatingRepository productRatingRepository;

    /*public ProductService(ProductRepository productRepository, ProjectRepository projectRepository,
                          ProductAreaRepository productAreaRepository) {
        this.repository = productRepository;
        this.projectRepository = projectRepository;
        this.productAreaRepository = productAreaRepository;
    }*/


    public ProductService(ProductRepository productRepository, ProjectRepository projectRepository,
                          ProductAreaRepository productAreaRepository, RatingRepository ratingRepository,
                          ProductRatingRepository productRatingRepositor) {
        this.repository = productRepository;
        this.projectRepository = projectRepository;
        this.productAreaRepository = productAreaRepository;
        this.ratingRepository= ratingRepository;
        this.productRatingRepository = productRatingRepositor;
    }
    /**
     * This method is finding product data transfer objects by its product ID.
     *
     * @param productID The ID of the product for which the data transfer object has to be found.
     * @throws ResourceNotFound When there is no product ID found.
     * @return The product data transfer object belonging to the product ID.
     */
    public ProductDto findById(int productID) {
        Optional<ProductEntity> productEntity = repository.findById(productID);

        if (productEntity.isEmpty()) {
            return null;
        }else{
            return new ProductDto(productEntity.get());
        }
    }

    /**
     * This method is creating a list products for a project.
     *
     * @param projectID The ID of the project for which a product should be created.
     * @param productDto The product data transfer object.
     * @return A list of created products for a project.
     */
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

    /**
     * This method is creating a list products for a project.
     *
     * @param projectID The ID of the project for which a product should be created.
     * @param productDto The product data transfer object.
     * @throws ResourceNotFound When the projectID or projectAreaID does not exist.
     * @return A list of created products for a project.
     */
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

            //TODO: Product Area is missing in entity produces a nullpoint exception
            List<ProductDto> createdProducts = new ArrayList<>();
            //entities.forEach(entity -> createdProducts.add(new ProductDto(entity)));

            entities = repository.saveAllAndFlush(entities);

            List<ProductDto> ps = getProductsByProjectIdAndProductAreaId(projectID, productDto.productArea.id);
            for (ProductEntity entity : entities)
            {
                for (ProductDto p : ps) {
                    if(p.productName.equals(entity.name)){
                        createProductRatings(p, entity.id);
                    }
                }
            }


            return createdProducts;

        }else {
            throw new ResourceNotFound("Resource not Found. ProjectID and/or ProjectAreaID does not exist.");
        }
    }

    /**
     * This method is creating product entities with attributes like name, project or comment in database.
     *
     * @param projectID The project ID for which a product entity should be created.
     * @param productAreaID The ID of the product area which has to be created for a product.
     * @param dto The product data transfer object.
     * @param isProductVariant When the product is a variant of another product (e.g. dispo credit and dispo credit flex).
     * @param parentEntity When the product is a variant, it needs to have a parent entity.
     * @return A new product entity for database.
     */
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


    /**
     * This method is updating product information by the products ID.
     *
     * @param productDto The product data transfer object.
     * @param productID The ID of the product which information have to be updated.
     * @throws ResourceNotFound When the product ID is not found.
     * @return The product data transfer object with updated information.
     */
    public ProductDto updateById(ProductDto productDto, int productID) {

        if (!repository.existsById(productID)) {
            return null;
        }else{
            repository.findById(productID).map(
                    product -> {
                        updateProductName(product, productDto);
                        updateProductComment(product, productDto);
                        return repository.save(product);
                    });

            return productDto;
        }
    }

    /**
     * This method is updating the comment for a specific product.
     *
     * @param currentEntity The entity of the product before the comment is updated.
     * @param updateDto The new product data transfer object with updated comment.
     */
    private void updateProductComment(ProductEntity currentEntity, ProductDto updateDto) {
        if (updateDto.comment != null) {
            if (updateDto.comment.isBlank()) {
                currentEntity.comment = null;
            } else {
                currentEntity.comment = updateDto.comment;
            }
        }
    }


    /**
     * This method is updating the name for a specific product.
     *
     * @param currentEntity The entity of the product before the name is updated.
     * @param updateDto The new product data transfer object with updated name.
     * @throws BadRequest When the input is missing or incorrect.
     */
    private void updateProductName(ProductEntity currentEntity, ProductDto updateDto) {
        if (updateDto.productName != null){
            if(updateDto.productName.isBlank()){
                throw new BadRequest("Input is missing/incorrect");
            }else{
                currentEntity.name = updateDto.productName;
            }
        }
    }


    /**
     * This method is giving back a list of products for a specific project by its ID.
     *
     * @param projectID The ID of the project for which products should be returned.
     * @throws BadRequest When the project ID does not exist.
     * @return A list of product data transfer objects for a specific project by its ID.
     */
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
            throw new ResourceNotFound("project " + projectID + " does not exist.");
        }
    }


    /**
     * This method gives back a list of products based on the projectID and product area ID.
     *
     * @param projectID The project ID for which products should be returned.
     * @param projectAreaID The project area ID for which products should be returned.
     * @return A list of products for a specific project ID and product area ID.
     */
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


    @Transactional
    public ProductDto createProductRatings(ProductDto productDto, int productID){
        //Step 1: check if productRatings can be created
        if (!repository.existsById(productID)) {
            return null;
        } else {
            ProductEntity product = repository.getById(productID);
            System.out.println(product.id);

            //Step 2: ensure for each ratingEntity is a productRatingEntity created
            HashMap<Integer, ProductRatingEntity> newProductRatings = initProductRatings(product, productDto);

            //Step 3: persist to db
            List<ProductRatingEntity> tmp = new ArrayList<>(newProductRatings.values());
            productRatingRepository.saveAll(tmp);

            return new ProductDto(product, tmp, false);
        }
    }

    public HashMap<Integer, ProductRatingEntity> initProductRatings(ProductEntity product, ProductDto productIn){
        HashMap<Integer, ProductRatingEntity> newProductRatings = new HashMap<>();
        RatingArea ratingArea;
        List<RatingEntity> ratings;

        if(productIn.ratings != null) {
            if (ratingRepository.existsById(productIn.ratings.get(0).ratingID)) {
                ratingArea = ratingRepository.findById(productIn.ratings.get(0).ratingID).get().ratingarea;
                ratings = ratingRepository.findByRatingarea(ratingArea);
            } else {
                throw new ResourceNotFound("ratingID " + productIn.ratings.get(0).ratingID + " not found");
            }
        }
        else
        {
            ratings = ratingRepository.findByRatingarea(RatingArea.ECONOMIC);
            ratings.addAll(ratingRepository.findByRatingarea(RatingArea.COMPLEXITY));
        }

        for(RatingEntity rating: ratings){
            ProductRatingEntity productRating = new ProductRatingEntity();
            productRating.productRatingId = new ProductRatingId(product, rating);
            newProductRatings.put(rating.id, productRating);
        }

        return newProductRatings;
    }


}
