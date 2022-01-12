package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
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

    private final ProductRatingRepository repository;
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;

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
    //TODO: (done - needs review) test output against api definition
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

            return new ProductDto(productEntity.get(), tmp, true);
        }
    }


    /**
     * This method is creating ratings for a product by its ID. Before the method is checking if there is a product in
     * database for the ID.
     *
     * @param productDto The product data transfer object.
     * @param productID The ID of the product for which a rating should be conducted.
     * @throws ResourceNotFound When the product ID is not found.
     * @return A Product data transfer object with new product rating.
     */
    // TODO: (prio: high) sicherstellen dass für jede hinterlegte Frage in der Rating_Entity ein Eintrag angelegt
    // wird in der Product_Rating_Entity
    @Transactional
    public ProductDto createProductRatings(ProductDto productDto, int productID) {

        if (!productRepository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        } else {
            ProductEntity productEntity = productRepository.findById(productID).get();

            if(productDto.overallEconomicRating != null){
                productEntity.overallEconomicRating = productDto.overallEconomicRating;
                productRepository.saveAndFlush(productEntity);
            }

            List<ProductRatingEntity> newProductRatings = new ArrayList<>();

            for (ProductRatingDto tmp : productDto.ratings) {
                if(ratingRepository.existsById(tmp.ratingID)){
                    ProductRatingEntity newEntity = new ProductRatingEntity();
                    assignAttributes(tmp, newEntity);
                    RatingEntity ratingEntity = ratingRepository.findById(tmp.ratingID).get();
                    newEntity.productRatingId = new ProductRatingId(
                            productRepository.getById(productID), ratingEntity);
                    newProductRatings.add(newEntity);
                }else{
                    throw new ResourceNotFound("ratingID " + tmp.ratingID + " not found");
                }
            }

//            productEntity.productRatingEntities = newProductRatings;

            repository.saveAll(newProductRatings);

            return new ProductDto(productEntity , newProductRatings, false);
        }
    }

    /**
     * This method is updating ratings for specific products.
     *
     * @param productDto The product data transfer object.
     * @param productID The ID pf the product for which the rating has to be updated.
     * @throws ResourceNotFound When the product ID or rating ID is not found.
     * @return A product data transfer object with the updated rating for product.
     */
    //TODO: (test)
    @Transactional
    public ProductDto updateProductRatings(ProductDto productDto, int productID) {

        if (!productRepository.existsById(productID)) {
            throw new ResourceNotFound("productID " + productID + " not found");
        } else {
            ProductEntity productEntity = productRepository.findById(productID).get();


            if(productDto.overallEconomicRating != null){
                productEntity.overallEconomicRating = productDto.overallEconomicRating;
                productRepository.save(productEntity);
            }

            List<ProductRatingEntity> updates = new ArrayList<>();

            for (ProductRatingDto tmp : productDto.ratings) {
                ProductRatingId tmpId = new ProductRatingId(productRepository.getById(productID),
                        ratingRepository.getById(tmp.ratingID));

                Optional<ProductRatingEntity> updateEntity = repository.findById(tmpId);

                if(updateEntity.isPresent()){
                    updateEntity.map(
                                productRating -> {
                                    assignAttributes(tmp, productRating);
                                    return updates.add(productRating);
                                });
                }else{
                    throw new ResourceNotFound("ratingID " + tmp.ratingID + " not found");
                }
            }

            repository.saveAll(updates);

            return new ProductDto(productEntity , updates, false);
        }
    }

    /**
     * This method is defining answer, comment and score for a product rating.
     *
     * @param tmp The product rating DTO.
     * @param newEntity The product rating entity in database.
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
