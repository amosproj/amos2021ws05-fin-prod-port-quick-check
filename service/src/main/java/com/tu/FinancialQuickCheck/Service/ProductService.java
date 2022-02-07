package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
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

    /**
     * Class Constructor specifying product, project, productArea, rating and productRating repositories.
     */
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
     * @param productID The productID is the unique identifier of the product entity in db
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
     * Adds a product entity and its children or a product variant to db.
     *
     * Products belong to a project entity, and can therefore only be added to db
     * if an existing projectID is provided. It is further required to provide a
     * product name with a minimum length of one character.
     *
     * @param projectID The projectID is the unique identifier of a project entity in db
     * @param productDto The productDto should contain necessary product information, incl. children
     * @return List of created products if successful else null
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
     * Adds a product entity and its children or a product variant to db and adds emptu product ratings
     * for added product entities to db.
     *
     * @param projectID The projectID is the unique identifier of a project entity in db
     * @param productDto The productDto should contain necessary product information, incl. children
     * @throws ResourceNotFound if project entity with projectID or product area entity with productAreaID does not exist in db
     * @return List of created products
     */
    @Transactional
    public List<ProductDto> createProduct(int projectID, ProductDto productDto){
        Optional<ProjectEntity> project = projectRepository.findById(projectID);
        Optional<ProductAreaEntity> productArea = productAreaRepository.findById(productDto.productArea.id);

        if(project.isPresent() && productArea.isPresent()) {
            List<ProductEntity> entities = new ArrayList<>();

            ProductEntity newProduct = createProductEntity(project.get(), productArea.get(), productDto, productDto.isProductVariant(), getParentEntity(productDto));
            entities.add(newProduct);

            entities.addAll(createProductVariationEntities(project.get(), productArea.get(), productDto, newProduct));

            repository.saveAllAndFlush(entities);

            List<ProductDto> createdProducts = new ArrayList<>();
//            entities.forEach(entity -> createdProducts.add(new ProductDto(entity)));

            for (ProductEntity tmp : entities) {
                createProductRatings(tmp.id);
            }

            return createdProducts;
        }else{
            throw new ResourceNotFound("Resource not Found. ProjectID and/or ProjectAreaID does not exist.");
        }
    }

    /**
     * Retrieves parent entity of product from db
     *
     * @param productDto The productDto should contain parentID if exists
     * @throws BadRequest if provided parentID does not exist in db
     * @return A product entity only if exist else null
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
     * Creates product variants of a product entity .
     *
     * @param project The project to which the product entities are assigned to, should not be null.
     * @param productArea The productArea to which the product entities are assigned to, should not be null.
     * @param productDto The productDto containing the product variants
     * @param parent The product entity representing the parent of the product variants, should not be null
     * @return A list of product entities
     */
    public List<ProductEntity> createProductVariationEntities(ProjectEntity project, ProductAreaEntity productArea,
                                             ProductDto productDto, ProductEntity parent){
        List<ProductEntity> productEntities = new ArrayList<>();

        if (productDto.productVariations != null) {
            for (ProductDto product : productDto.productVariations) {
                ProductEntity entity = createProductEntity(project, productArea, product, true, parent);
                productEntities.add(entity);
            }
        }
        return productEntities;
    }

    /**
     * Creates a product entity.
     *
     * @param project The project to which the product entity is assigned to, should not be null.
     * @param productArea The productArea to which the product entity is assigned to, should not be null.
     * @param dto The dto contains the necessary product information.
     * @param isProductVariation True if dto has a parent product
     * @param parent The product entity representing the parent of the product, should not be null of isProductVariation is True
     * @throws BadRequest if productName is missing or an empty string
     * @return A product entity if attribute name is provided and is at least one character long
     */
    public ProductEntity createProductEntity(ProjectEntity project, ProductAreaEntity productArea,
                                             ProductDto dto, Boolean isProductVariation,ProductEntity parent){

        if(dto.productName != null && !dto.productName.isBlank()) {
            ProductEntity newProduct = new ProductEntity();
            newProduct.name = dto.productName;
            newProduct.project = project;
            newProduct.productarea = productArea;
            if (dto.comment != null) {newProduct.comment = dto.comment;}
            if(isProductVariation) {newProduct.parentProduct = parent;}
            return newProduct;
        }else{
            throw new BadRequest("Input is missing/incorrect");
        }
    }

    /**
     * Updates attributes name and comment of product entity in db.
     *
     * @param productDto The productDto contains attributes productName and comment for update
     * @param productID The productID is the unique identifier of the product entity in db
     * @return Updated product if exists else null
     */
    public ProductDto updateById(ProductDto productDto, int productID) {
        Optional<ProductEntity> productEntity = repository.findById(productID);
        if (productEntity.isEmpty()) {
            return null;
        }else{
            ProductEntity product = productEntity.get();
            updateProductName(product, productDto);
            updateProductComment(product, productDto);
            repository.save(product);

            return new ProductDto(product);
        }
    }

    /**
     * Updates attributes comment of product entity.
     *
     * @param currentEntity The currentEntity is the object that is updated
     * @param updateDto The updateDto contains product information for update
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
     * @param currentEntity The currentEntity is the object that is updated
     * @param updateDto The updateDto contains product information for update
     * @throws BadRequest if product name is empty or missing
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
     * Retrieve all product entities of a project entity from db.
     *
     * Filters out any product entity with name "DUMMY". These are placeholder entities for existing productAreas.
     *
     * @param projectID The projectID of the project entity for which products should be retrieved
     * @throws ResourceNotFound if the projectID does not exist in db.
     * @return List of products
     */
    public List<ProductDto> getProductsByProjectId(int projectID){
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);
        if(projectEntity.isPresent()){
            List<ProductDto> productsByProject = new ArrayList<>();
            Iterable<ProductEntity> productEntities = repository.findByProject(projectEntity.get());
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
     * Filters out any product entity with name "DUMMY". These are placeholder entities for existing productAreas.
     *
     * @param projectID The projectID of the project entity for which products should be retrieved
     * @param productAreaID The productAreaID of the productArea entity for which products should be retrieved
     * @throws ResourceNotFound if projectID or productAreaID does not exist.
     * @return List of products
     */
    public List<ProductDto> getProductsByProjectIdAndProductAreaId(int projectID, int productAreaID){
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);

        if(projectEntity.isPresent()){
            List<ProductDto> productsByProjectAndProductArea = new ArrayList<>();
            Iterable<ProductEntity> productEntities = repository.findByProjectAndProductarea(
                    projectEntity.get(),
                    productAreaRepository.getById(productAreaID));

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
     * @param product The product entity for whom the progress is calculated
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
     * Adds for an existing product entity all existing rating entities to db.
     *
     * @param productID The productID of the product entity for which ratings are created
     * @return A product with created product ratings if product exists, else null
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
     * @param product The product entity to whom ratings are assigned
     * @throws ResourceNotFound if rating entities table in db is empty
     * @return List of created product rating entities
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
