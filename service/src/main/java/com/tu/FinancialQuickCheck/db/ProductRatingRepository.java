package com.tu.FinancialQuickCheck.db;


import com.tu.FinancialQuickCheck.RatingArea;
import org.springframework.data.repository.CrudRepository;


public interface ProductRatingRepository extends CrudRepository<ProductRatingEntity, Integer> {

//    Iterable<ProductRatingEntity> findByProductid(ProductEntity productEntity.);

}