package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.ProductRatingService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("products/{productID}/ratings")
public class ProductRatingController {

    private ProductRatingService service;

    /**
     * Constructor for class ProductRatingController.
     *
     * @param productRatingService The different services for the product rating.
     */
    public ProductRatingController(ProductRatingService productRatingService){
        this.service = productRatingService;
    }

    /**
     * This method returns economical and complexity ratings for a product by their ID.
     *
     * @param productID The ID of the product.
     * @param ratingArea There is a economic and a complexity rating area.
     * @throws ResourceNotFound When the productID does not exist.
     * @return The economic and the complexity rating for a product by their ID.
     */
    //TODO: (done - needs review) return empty list or return Error if no ratings exist? --> empty list
    @GetMapping()
    public ProductDto getProductRatings(
            @PathVariable int productID,
            @RequestParam(required = false) RatingArea ratingArea) {

        ProductDto tmp = service.getProductRatings(productID, ratingArea);

        if(tmp == null){
            throw new ResourceNotFound("productID " + productID + " does not exist" );
        }else{
            return tmp;
        }

    }

    /**
     * This method can create economical or complexity ratings for a product by their ID.
     *
     * @param productDto The product data transfer object.
     * @param productID The ID of the product.
     * @return The created rating for a product by their ID.
     */
    //TODO: (done - needs review) add consumes and produces
    //TODO: (done - needs review) change output according to API
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ProductDto createProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {
        ProductDto tmp = service.createProductRatings(productDto, productID);

        if(tmp == null){
            throw new ResourceNotFound("productID " + productID + " does not exist" );
        }else{
            return tmp;
        }
    }

    /**
     * This method can update economical or complexity ratings for a product by their ID.
     *
     * @param productDto The product data transfer object.
     * @param productID The ID of the product for which the rating can be updated.
     * @return The updated rating for a product by their ID.
     */
    //TODO: (done - needs review) add consumes
    //TODO: (done - needs review) change output according to API
    @PutMapping(consumes = "application/json")
    public ProductDto updateProductRatings(@RequestBody ProductDto productDto, @PathVariable Integer productID) {
        ProductDto tmp = service.updateProductRatings(productDto, productID);

        if(tmp == null){
            throw new ResourceNotFound("productID " + productID + " does not exist" );
        }else{
            return tmp;
        }
    }

}
