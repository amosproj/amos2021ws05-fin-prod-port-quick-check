package com.tu.FinancialQuickCheck.Controller;


import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.ProductRatingService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("products/{productID}/ratings")
public class ProductRatingController {

    @Autowired
    private ProductRatingService service;

    @GetMapping()
    public ProductDto getProductRatings(
            @PathVariable int productID,
            @RequestParam(required = false) RatingArea ratingArea) {
        if(ratingArea == null){
            return service.getAllProductRatings(productID);
        }
        else {
            return new ProductDto();
//            return service.getProductRatingsByRatingarea(productID, ratingArea);
        }
    }

//    @PutMapping()
//    public void updateProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {
//
//        productService.updateById(productDto, productID);
//    }
//
//    @PostMapping()
//    public void createProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {
//
//        productService.updateById(productDto, productID);
//    }




}
