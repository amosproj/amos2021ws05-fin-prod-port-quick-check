package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductRatingService {
    @Autowired
    private final ProductRatingRepository repository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final RatingRepository ratingRepository;

    public ProductRatingService(ProductRatingRepository repository, ProductRepository productRepository,
                                RatingRepository ratingRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }

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


    //TODO: (done - needs review)
    @Transactional
    public ProductDto createProductRatings(ProductDto productDto, int productID){
        //Step 1: check if productRatings can be created
        if (!productRepository.existsById(productID)) {
            return null;
        } else {
            ProductEntity product = productRepository.getById(productID);

            //Step 2: ensure for each ratingEntity is a productRatingEntity created
            HashMap<Integer, ProductRatingEntity> newProductRatings = initProductRatings(product, productDto);

            //Step 3: map input to created productRatingEntities
            assignInputToAttributes(productDto.ratings, newProductRatings);

            //Step 4: persist to db
            List<ProductRatingEntity> tmp = new ArrayList<>(newProductRatings.values());
            repository.saveAll(tmp);

            return new ProductDto(product, tmp, false);
        }
    }


    //TODO: (test)
    @Transactional
    public ProductDto updateProductRatings(ProductDto productDto, int productID) {

        if (!productRepository.existsById(productID)) {
            return null;
        } else {
            ProductEntity productEntity = productRepository.findById(productID).get();

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


    public void assignInputToAttributes(List<ProductRatingDto> productRatingsIn,
                                        HashMap<Integer, ProductRatingEntity> existingProductRatings){

        for (ProductRatingDto tmp : productRatingsIn) {
            if(ratingRepository.existsById(tmp.ratingID)){
                assignAttributes(tmp, existingProductRatings.get(tmp.ratingID));
            }else{
                throw new ResourceNotFound("ratingID " + tmp.ratingID + " not found");
            }
        }
    }

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


    public HashMap<Integer, ProductRatingEntity> initProductRatings(ProductEntity product, ProductDto productIn){
        HashMap<Integer, ProductRatingEntity> newProductRatings = new HashMap<>();
        RatingArea ratingArea;
        List<RatingEntity> ratings;

        if(ratingRepository.existsById(productIn.ratings.get(0).ratingID)){
            ratingArea = ratingRepository.findById(productIn.ratings.get(0).ratingID).get().ratingarea;
            ratings = ratingRepository.findByRatingarea(ratingArea);
        }else{
            throw new ResourceNotFound("ratingID " + productIn.ratings.get(0).ratingID + " not found");
        }

        for(RatingEntity rating: ratings){
            ProductRatingEntity productRating = new ProductRatingEntity();
            productRating.productRatingId = new ProductRatingId(product, rating);
            newProductRatings.put(rating.id, productRating);
        }

        return newProductRatings;
    }


}
