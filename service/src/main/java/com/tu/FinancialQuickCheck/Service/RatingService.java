package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.RatingRepository;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import com.tu.FinancialQuickCheck.dto.ListOfRatingDto;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * The RatingService class performs service tasks and defines the logic for the ratings. This includes giving back
 * all ratings or by a specific rating area (complexity or economical).
 */
@Service
public class RatingService {

    private RatingRepository repository;


    public RatingService(RatingRepository ratingRepository) {
        this.repository = ratingRepository;
    }


    public List<RatingDto>  getAllRatings() {

        return new ListOfRatingDto(this.repository.findAll()).ratings;

    }


    /**
     * This method is giving back the ratings for a specific rating area (complexity or economical).
     *
     * @param ratingArea The rating area for which ratings should be returned.
     * @return A list of ratings data transfer object for the specific rating area.
     */
    public List<RatingDto> getRatingsByRatingArea(RatingArea ratingArea) {

        return new ListOfRatingDto(this.repository.findByRatingarea(ratingArea)).ratings;
    }

}
