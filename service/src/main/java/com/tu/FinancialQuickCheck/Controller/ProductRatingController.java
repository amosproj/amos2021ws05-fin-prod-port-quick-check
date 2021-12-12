package com.tu.FinancialQuickCheck.Controller;


import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.ProductRatingService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("products/{productID}/ratings")
public class ProductRatingController {

    private ProductRatingService service;

    public ProductRatingController(ProductRatingService productRatingService){
        this.service = productRatingService;
    }

    //TODO: (done - needs review) return empty list or return Error if no ratings exist?
    @GetMapping()
    public ProductDto getProductRatings(
            @PathVariable int productID,
            @RequestParam(required = false) RatingArea ratingArea) {

        return service.getProductRatings(productID, ratingArea);

    }


    //TODO: (done - needs review) add consumes and produces
    //TODO: (done - needs review) change output according to API
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ProductDto createProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        return service.createProductRatings(productDto, productID);
    }

    //TODO: (done - needs review) add consumes
    //TODO: (done - needs review) change output according to API
    @PutMapping(consumes = "application/json")
    public ProductDto updateProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        return service.updateProductRatings(productDto, productID);
    }

}
