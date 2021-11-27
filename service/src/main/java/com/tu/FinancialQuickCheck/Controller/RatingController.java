package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.RatingService;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ratings")
public class RatingController {

    private RatingService service;

    public RatingController(RatingService ratingService){
        this.service = ratingService;
    }

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
