package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public static final Integer[] SET_VALUES = new Integer[] {4, 5, 9, 10};
    public static final Set<Integer> VALUES_ECONOMIC = new HashSet<>(Arrays.asList(SET_VALUES));


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
     * Retrieves product entity from db based on productID.
     *
     * @param productID unique identifier of product entity
     * @return ProductDto if product enity exists else null
     */
    public ProductDto findById(int productID) {
        Optional<ProductEntity> productEntity = repository.findById(productID);

        if (productEntity.isEmpty()) {
            return null;
        }else{
            float[] progress = calculateProductRatingProgress(productEntity.get());
            return new ProductDto(productEntity.get(), progress);
        }
    }

    /**
     * Adds a product entity and its children to db and adds emptu product ratings for added product entities.
     *
     * @param projectID unique identifier of project entity
     * @param productDto contains product information
     * @return list of created products if successful else null
     */
    public List<ProductDto> wrapperCreateProduct(int projectID, ProductDto productDto){
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
     * Adds a product entity and its children to db or adds a product variatin to db.
     *
     * @param projectID unique identifier of project entity
     * @param productDto contains product information
     * @throws ResourceNotFound when project entity with projectID or product area entity with productAreaID does not exist
     * @return list of created products
     */
    @Transactional
    public List<ProductDto> createProduct(int projectID, ProductDto productDto){
        if(repository.existsByProjectAndProductarea(
                projectRepository.getById(projectID),
                productAreaRepository.getById(productDto.productArea.id)))
        {
            List<ProductEntity> entities = new ArrayList<>();

            ProductEntity parentEntity = getParentEntity(productDto);

            ProductEntity newProduct = createProductEntity(projectID, productDto.productArea.id, productDto, productDto.isProductVariant(), parentEntity);
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

            repository.saveAllAndFlush(entities);

            List<ProductDto> createdProducts = new ArrayList<>();
//            entities.forEach(entity -> createdProducts.add(new ProductDto(entity)));

            for(ProductEntity tmp: entities){
                createProductRatings(tmp.id);
            }

            return createdProducts;
        }else {
            throw new ResourceNotFound("Resource not Found. ProjectID and/or ProjectAreaID does not exist.");
        }
    }

    /**
     * Retrieves parent entity of product from db
     *
     * @param productDto contains product information
     * @return product entity only if productDto has a parent entity else null
     */
    public ProductEntity getParentEntity(ProductDto productDto){
        ProductEntity parentEntity = null;
        if(productDto.parentID != 0){
            Optional<ProductEntity> parentEntityOptional = repository.findById(productDto.parentID);
            if(parentEntityOptional.isPresent()){
                parentEntity = parentEntityOptional.get();
            }else{
                throw new BadRequest("Parent Id does not exist");
            }
        }
        return parentEntity;
    }

    /**
     * Creates a product entity.
     *
     * @param projectID unique identifier of project entity
     * @param productAreaID unique identifier of product area entity
     * @param dto contains product information
     * @param isProductVariation True if dto has a parent product
     * @param parent
     * @return product entity if attribute name is provided and is at least one character long else null
     */
    public ProductEntity createProductEntity(int projectID, int productAreaID, ProductDto dto, Boolean isProductVariation,ProductEntity parent){
        if(dto.productName != null && !dto.productName.isBlank()) {
            ProductEntity newProduct = new ProductEntity();
            newProduct.name = dto.productName;
            newProduct.project = projectRepository.findById(projectID).get();
            newProduct.productarea = productAreaRepository.getById(productAreaID);
            if (dto.comment != null) {newProduct.comment = dto.comment;}
            if(isProductVariation) {newProduct.parentProduct = parent;}
            return newProduct;
        }else{
            return null;
        }
    }

    /**
     * Updates attributes name and comment of product entity.
     *
     * @param productDto contain product information
     * @param productID unique identifier of product
     * @return updated product if exists else null
     */
    public ProductDto updateById(ProductDto productDto, int productID) {

        if (!repository.existsById(productID)) {
            return null;
        }else{
            ProductEntity product = repository.findById(productID).get();
            updateProductName(product, productDto);
            updateProductComment(product, productDto);
            repository.save(product);

            return new ProductDto(product);
        }
    }

    /**
     * Updates attributes comment of product entity.
     *
     * @param currentEntity object that is updated
     * @param updateDto contains product information for update
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
     * Updates attributes name of product entity.
     *
     * @param currentEntity object that is updated
     * @param updateDto contains product information for update
     * @throws BadRequest if product name is empty
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
     * Retrieve all product entities from db for project entity with projectID.
     *
     * @param projectID unique identifier of project entity
     * @throws ResourceNotFound When the project ID does not exist.
     * @return list of products
     */
    public List<ProductDto> getProductsByProjectId(int projectID){

        if(projectRepository.existsById(projectID)){
            List<ProductDto> productsByProject = new ArrayList<>();
            Iterable<ProductEntity> productEntities = repository.findByProject(projectRepository.findById(projectID).get());
            for(ProductEntity tmp : productEntities){
                if(!tmp.name.equals("DUMMY")){
                    float[] progress = calculateProductRatingProgress(tmp);
                    ProductDto addProduct = new ProductDto(tmp, progress);
                    productsByProject.add(addProduct);
                }
            }
            return productsByProject;
        }else{
            throw new ResourceNotFound("project " + projectID + " does not exist.");
        }
    }

    /**
     * Retrieve all product entities from db for project entity with projectID and
     * product area entity with product area ID.
     *
     * @param projectID unique identifier of project entity
     * @param projectAreaID unique identifier of product area entity
     * @throws ResourceNotFound When the project ID does not exist.
     * @return list of products
     */
    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int projectAreaID){
        if(projectRepository.existsById(projectID)){
            List<ProductDto> productsByProjectAndProductArea = new ArrayList<>();
            Iterable<ProductEntity> productEntities = repository.findByProjectAndProductarea(
                    projectRepository.findById(projectID).get(),
                    productAreaRepository.getById(projectAreaID));

            for(ProductEntity tmp : productEntities){
                if(!tmp.name.equals("DUMMY")) {
                    float[] progress = calculateProductRatingProgress(tmp);
                    productsByProjectAndProductArea.add(new ProductDto(tmp, progress));
                }
            }
            return productsByProjectAndProductArea;
        }else{
            throw new ResourceNotFound("project " + projectID + " does not exist.");
        }
    }

    /**
     * Calculates progress of economic and complexity evaluation.
     *
     * complexity progress = attributes answer and score are not null
     * economic progress = attributes answer and score of ratings with id in VALUES_ECONOMIC is not null
     *
     * @param product entity for whom the progress is calculated
     * @return array of length 2, array[0] = economic progress, array[1] = complexity progress,
     */
    public float[] calculateProductRatingProgress(ProductEntity product){

        float[] progressValues = new float[2];
        int[] counter = new int[2];

        if(product.productRatingEntities == null || product.productRatingEntities.isEmpty()){
            progressValues[0] = 0.0f;
            progressValues[1] = 0.0f;
            return progressValues;
        }else{
            for(ProductRatingEntity tmp: product.productRatingEntities){
                if(tmp.productRatingId.getRating().ratingarea == RatingArea.ECONOMIC){
                    if(VALUES_ECONOMIC.contains(tmp.productRatingId.getRating().id)) {
                        counter[0] += 1;
                        if (tmp.answer != null && tmp.score != null) {
                            progressValues[0] += 1;
                        }
                    }
                }else{
                    counter[1] += 1;
                    if(tmp.answer != null && tmp.score != null){
                        progressValues[1] += 1;
                    }
                }
            }

            progressValues[0] = Math.round((progressValues[0] / counter[0]) * 100);
            progressValues[1] = Math.round((progressValues[1] / counter[1]) * 100);

            return progressValues;
        }
    }

    /**
     * Adds for one product all existing ratings to db.
     *
     * @param productID unique identifier of product entity
     * @throws ResourceNotFound when db table rating has no entries
     * @return product with created product ratings
     */
    @Transactional
    public ProductDto createProductRatings(int productID){
        //Step 1: check if productRatings can be created
        if (!repository.existsById(productID)) {
            return null;
        } else {
            ProductEntity product = repository.getById(productID);

            //Step 2: ensure for each ratingEntity is a productRatingEntity created
            List<ProductRatingEntity> newProductRatings = initProductRatings(product);

            //Step 3: persist to db
            productRatingRepository.saveAll(newProductRatings);

            return new ProductDto(product, newProductRatings);
        }
    }

    /**
     * Creates product rating entities for product entity and all existing rating entities in db.
     *
     * @param product entity to whom ratings are assigned
     * @return list of created product rating entities
     */
    public List<ProductRatingEntity> initProductRatings(ProductEntity product){
        List<ProductRatingEntity> newProductRatings = new ArrayList<>();
        List<RatingEntity> ratings = ratingRepository.findAll();

        if(ratings.isEmpty()){
            throw new ResourceNotFound("Ratings not initilized.");
        }

        for(RatingEntity rating: ratings){
            ProductRatingEntity productRating = new ProductRatingEntity();
            productRating.productRatingId = new ProductRatingId(product, rating);
            newProductRatings.add(productRating);
        }

        return newProductRatings;
    }
}
