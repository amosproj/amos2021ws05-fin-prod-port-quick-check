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

    /**
     * Retrieves all existing rating entities from db.
     *
     * @return A list of all ratings if exist else empty list.
     */
    public List<RatingDto>  getAllRatings() {
        return new ListOfRatingDto(this.repository.findAll()).ratings;
    }

    /**
     * Retrieves all rating entities, that belong to specified ratingArea, from db.
     *
     * @param ratingArea The rating area is used to filter the output.
     * @return A list of ratings if exist else empty list.
     */
    public List<RatingDto> getRatingsByRatingArea(RatingArea ratingArea) {
        return new ListOfRatingDto(this.repository.findByRatingarea(ratingArea)).ratings;
    }

}
