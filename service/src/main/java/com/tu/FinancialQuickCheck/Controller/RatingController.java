package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.RatingService;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @GetMapping(produces = "application/json")
    public List<RatingDto> getRatings(@RequestParam(required = false) RatingArea ratingArea) {
        if(ratingArea == null){
            return ratingService.getAllRatings();
        }else {
            return ratingService.getRatingsByRatingArea(ratingArea);
        }
    }
}
