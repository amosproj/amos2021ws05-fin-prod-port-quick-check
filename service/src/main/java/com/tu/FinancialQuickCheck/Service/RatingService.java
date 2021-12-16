package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.RatingRepository;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import com.tu.FinancialQuickCheck.dto.ListOfRatingDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingService {

    private RatingRepository repository;


    public RatingService(RatingRepository ratingRepository) {
        this.repository = ratingRepository;
    }


    public List<RatingDto>  getAllRatings() {

        return new ListOfRatingDto(this.repository.findAll()).ratings;

    }


    public List<RatingDto> getRatingsByRatingArea(RatingArea ratingArea) {

        return new ListOfRatingDto(this.repository.findByRatingarea(ratingArea)).ratings;
    }

}
