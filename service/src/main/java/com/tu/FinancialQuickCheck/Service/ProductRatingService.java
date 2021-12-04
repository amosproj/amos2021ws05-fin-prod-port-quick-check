//package com.tu.FinancialQuickCheck.Service;
//
//import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
//import com.tu.FinancialQuickCheck.RatingArea;
//import com.tu.FinancialQuickCheck.db.*;
//import com.tu.FinancialQuickCheck.dto.ProductDto;
//import com.tu.FinancialQuickCheck.dto.ProductRatingDto;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@Service
//public class ProductRatingService {
//
//    private final ProductRatingRepository repository;
//    private final ProductRepository productRepository;
//    private final RatingRepository ratingRepository;
//
//    public ProductRatingService(ProductRatingRepository repository, ProductRepository productRepository,
//                                RatingRepository ratingRepository) {
//        this.repository = repository;
//        this.productRepository = productRepository;
//        this.ratingRepository = ratingRepository;
//    }
//
//
//    public ProductDto getProductRatings(int productID, RatingArea ratingArea) {
//
//        Optional<ProductEntity> productEntity = productRepository.findById(productID);
//
//        if (productEntity.isEmpty()) {
//            throw new ResourceNotFound("productID " + productID + " not found");
//        } else {
//
//            Iterable<ProductRatingEntity> entities = productEntity.get().productRatingEntities;
//
//            if (ratingArea != null) {
//                entities = StreamSupport.stream(entities.spliterator(), false)
//                        .filter(product -> product.productRatingId.getRatingid().ratingarea == ratingArea)
//                        .collect(Collectors.toList());
//            }
//
//            List<ProductRatingEntity> tmp = StreamSupport
//                    .stream(entities.spliterator(), false)
//                    .collect(Collectors.toList());
//
//            return new ProductDto(productEntity.get().name , tmp);
//        }
//    }
//
//    // TODO: merge createProductRatings & updateProductRatings
//    public ProductDto createProductRatings(ProductDto productDto, int productID) {
//
//        if (!productRepository.existsById(productID)) {
//            throw new ResourceNotFound("productID " + productID + " not found");
//        } else {
//            List<ProductRatingEntity> newProductRatings = new ArrayList<>();
//
//            for (ProductRatingDto tmp : productDto.ratings) {
//                if(ratingRepository.existsById(tmp.ratingID)){
//                    ProductRatingEntity newEntity = new ProductRatingEntity();
//                    assignAttributes(tmp, newEntity);
//                    newEntity.productRatingId = new ProductRatingId(
//                            productRepository.getById(productID),
//                            ratingRepository.getById(tmp.ratingID));
//                    newProductRatings.add(newEntity);
//                }else{
//                    throw new ResourceNotFound("ratingID " + tmp.ratingID + " not found");
//                }
//            }
//
//            repository.saveAll(newProductRatings);
//            return new ProductDto(productDto.productName, newProductRatings);
//        }
//    }
//
//
//    private void assignAttributes(ProductRatingDto tmp, ProductRatingEntity newEntity) {
//        if(tmp.answer != null){
//            newEntity.answer = tmp.answer;
//        }
//        if(tmp.comment != null) {
//            newEntity.comment = tmp.comment;
//        }
//        if(tmp.score != null){
//            newEntity.score = tmp.score;
//        }
//    }
//
//    // TODO: implementierung mit Alex oder Max bequatschen
//    public void updateProductRatings(ProductDto productDto, int productID) {
//        if (!productRepository.existsById(productID)) {
//            throw new ResourceNotFound("productID " + productID + " not found");
//        } else {
//            List<ProductRatingEntity> updates = new ArrayList<>();
//
//            for (ProductRatingDto tmp : productDto.ratings) {
//                ProductRatingId tmpId = new ProductRatingId(productRepository.getById(productID),
//                        ratingRepository.getById(tmp.ratingID));
//                Optional<ProductRatingEntity> updateEntity = repository.findById(tmpId);
//
//                if(updateEntity.isPresent()){
//                    updateEntity.map(
//                                productRating -> {
//                                    assignAttributes(tmp, productRating);
//                                    return updates.add(productRating);
//                                });
//                }else{
//                    throw new ResourceNotFound("ratingID " + tmp.ratingID + " not found");
//                }
//            }
//
//            repository.saveAll(updates);
//        }
//    }
//
//}
