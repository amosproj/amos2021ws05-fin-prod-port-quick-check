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

        return service.getProductRatings(productID, ratingArea);
    }

    @PostMapping()
    public void createProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        service.createProductRatings(productDto, productID);
    }

    @PutMapping()
    public void updateProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        service.updateProductRatings(productDto, productID);
    }

}
