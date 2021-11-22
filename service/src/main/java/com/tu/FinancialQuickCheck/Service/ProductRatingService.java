package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductRatingService {

    private final ProductRatingRepository repository;
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public ProductRatingService(ProductRatingRepository repository, ProductRepository productRepository,
                                RatingRepository ratingRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }


    public ProductDto getProductRatings(int productID, RatingArea ratingArea){

        Optional<ProductEntity> productEntity = productRepository.findById(productID);

        if (productEntity.isEmpty()) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{

            Iterable<ProductRatingEntity> entities = productEntity.get().productRatingEntities;
            List<ProductRatingDto> productRatingDtos = new ArrayList<>() {};

            if(ratingArea != null) {
                entities = StreamSupport.stream(entities.spliterator(), false)
                        .filter(product -> product.projectRatingId.getRatingid().ratingarea == ratingArea)
                        .collect(Collectors.toList());
            }

            for (ProductRatingEntity tmp : entities) {
                productRatingDtos.add(new ProductRatingDto(tmp.answer, tmp.comment, tmp.score,
                        tmp.projectRatingId.getRatingid().id));
            }

            return new ProductDto(productEntity.get().name, productRatingDtos);
        }
    }

    // TODO: request mit gleichen IDs überschreibt vorhandene Daten, wollen wir das zulassen für ein HTTP POST Request?
    public void createProductRatings(ProductDto productDto, int productID){
        List<ProductRatingEntity> newProductRatings = new ArrayList<>();

        for(ProductRatingDto tmp:  productDto.ratings){
            ProductRatingEntity newEntity = new ProductRatingEntity();
            newEntity.answer = tmp.answer;
            newEntity.score = tmp.score;
            newEntity.comment = tmp.comment;
            System.out.println("Current Rating ID: " + tmp.ratingID);
            newEntity.projectRatingId = new ProjectRatingId(
                    productRepository.getById(productID),
                    ratingRepository.getById(tmp.ratingID));
            newProductRatings.add(newEntity);
        }

        repository.saveAll(newProductRatings);
    }


//    public void updateProductRatings(ProductDto productDto, Integer productID) {
//
//        if (!productRepository.existsById(productID)) {
//            throw new ResourceNotFound("productID " + productID + " not found");
//        }else{
//            productRepository.findById(productID).map(
//                    product -> {
//                        product.name = productDto.name;
//                        return productRepository.save(product);
//                    });
//        }
//    }
}
