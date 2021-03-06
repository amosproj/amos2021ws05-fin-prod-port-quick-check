package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The ProductRatingService class performs service tasks and defines the logic for the product ratings. This includes
 * creating, updating or giving back product ratings
 */
@Service
public class ProductRatingService {
    @Autowired
    private final ProductRatingRepository repository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final RatingRepository ratingRepository;

    /**
     * Class constructor initializes productRating repository
     * */
    public ProductRatingService(ProductRatingRepository repository, ProductRepository productRepository,
                                RatingRepository ratingRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     * This method is giving back the rating for a specific product by its ID and rating area.
     *
     * @param productID The ID of the product for which the rating has to be returned.
     * @param ratingArea The rating area of the product for which the rating has to be returned.
     * @return The rating for a specific product.
     */
    public ProductDto getProductRatings(int productID, RatingArea ratingArea) {

        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (productEntity.isEmpty()) {
            return null;
        } else {

            Iterable<ProductRatingEntity> entities = productEntity.get().productRatingEntities;

            if (ratingArea != null) {
                entities = StreamSupport.stream(entities.spliterator(), false)
                        .filter(product -> product.productRatingId.getRating().ratingarea == ratingArea)
                        .collect(Collectors.toList());
            }

            List<ProductRatingEntity> tmp = StreamSupport
                    .stream(entities.spliterator(), false)
                    .collect(Collectors.toList());

            return new ProductDto(productEntity.get(), tmp);
        }
    }

    /**
     * Updates an existing ProductRatingEntity in DB
     * Attributes/relations that can be updated: The rating for a product
     * Attributes/relations that can not be updated: The productID
     * @param productDto contains data that needs to be updated
     * @param productID unique identifier for ProductEntity
     * @throws ResourceNotFound When the product ID is not found.
     */
    @Transactional
    public ProductDto updateProductRatings(ProductDto productDto, int productID) {

        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (productEntity.isEmpty()) {
            return null;
        } else {
            List<ProductRatingEntity> updates = new ArrayList<>();

            if(productDto.ratings != null){
                for (ProductRatingDto tmp : productDto.ratings) {
                    ProductRatingId tmpId = new ProductRatingId(productEntity.get(),
                            ratingRepository.getById(tmp.ratingID));

                    Optional<ProductRatingEntity> updateEntity = repository.findById(tmpId);

                    if(updateEntity.isPresent()){
                        updateEntity.map(productRating -> {
                            assignAttributes(tmp, productRating);
                            return updates.add(productRating);
                        });
                    }else{
                        throw new ResourceNotFound("ratingID " + tmp.ratingID + " not found");
                    }
                }
                repository.saveAll(updates);
            }else{
                throw new BadRequest("Input missing/incorrect");
            }

            return new ProductDto(productEntity.get() , updates);
        }
    }

    /**
     * This method is assigning information (answer, comment & score) to specific ratings
     *
     * @param tmp The product rating transfer object for which information (answer, comment & score) should be assigned
     */
    public void assignAttributes(ProductRatingDto tmp, ProductRatingEntity newEntity) {
        if(tmp.answer != null){
            newEntity.answer = tmp.answer;
        }
        if(tmp.comment != null) {
            newEntity.comment = tmp.comment;
        }
        if(tmp.score != null){
            newEntity.score = tmp.score;
        }
    }

}
