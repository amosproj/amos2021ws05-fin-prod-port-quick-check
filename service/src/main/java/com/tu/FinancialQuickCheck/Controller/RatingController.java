package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.RatingService;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@CrossOrigin
@RequestMapping("ratings")
public class RatingController {

    private RatingService service;

    /**
     * Constructor for class RatingController.
     *
     * @param ratingService The different services for the rating.
     */
    public RatingController(RatingService ratingService){
        this.service = ratingService;
    }

    /**
     * This method can return ratings for the economical and/or complexity rating.
     *
     * @param ratingArea The rating area which can be economical or cemplexity.
     * @throws ResourceNotFound When there is no ratings for an area found.
     * @return A list of ratings for their related area.
     */
    @GetMapping(produces = "application/json")
    public List<RatingDto> getRatings(@RequestParam(required = false) RatingArea ratingArea) {

        List<RatingDto> r = (ratingArea == null) ? service.getAllRatings() : service.getRatingsByRatingArea(ratingArea);

        if(r.isEmpty()){
            throw new ResourceNotFound("No Ratings found.");
        }else{
            return r;
        }
    }
}
