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

    //TODO: return empty list or return Error if no ratings exist?
    @GetMapping()
    public ProductDto getProductRatings(
            @PathVariable int productID,
            @RequestParam(required = false) RatingArea ratingArea) {

        ProductDto p = service.getProductRatings(productID, ratingArea);

        if(p.ratings.isEmpty()){
            throw new ResourceNotFound("No ratings for productID" + productID + " found.");
        }else{
            return p;
        }
    }


    //TODO: Secure that every entry inside the RatingEntity is existing
    //TODO: add consumes and produces
    @PostMapping()
    public void createProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        service.createProductRatings(productDto, productID);
    }

    //TODO: add consumes
    @PutMapping()
    public void updateProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        service.updateProductRatings(productDto, productID);
    }

}
