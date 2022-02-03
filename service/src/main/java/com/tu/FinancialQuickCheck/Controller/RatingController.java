package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.RatingService;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ratings")
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
     * Retrieves either all existing rating entities from db or the ones for the specified ratingArea.
     *
     * @param ratingArea The rating area is used to filter the output.
     * @throws ResourceNotFound if no rating entities exist in db.
     * @return A list of ratings.
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
