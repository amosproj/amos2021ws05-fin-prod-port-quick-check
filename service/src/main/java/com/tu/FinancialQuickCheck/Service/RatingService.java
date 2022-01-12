package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.RatingEntity;
import com.tu.FinancialQuickCheck.db.RatingRepository;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * This method is giving back a list of all ratings.
     *
     * @return A list of rating data transfer objects.
     */
    public List<RatingDto> getAllRatings() {

        List<RatingDto> ratingDtos = new ArrayList<>() {};

        Iterable<RatingEntity> ratingEntities = this.repository.findAll();

        for(RatingEntity tmp : ratingEntities){
            ratingDtos.add(new RatingDto(tmp.id, tmp.criterion, tmp.category, tmp.ratingarea));
        }

        return ratingDtos;
    }


    /**
     * This method is giving back the ratings for a specific rating area (complexity or economical).
     *
     * @param ratingArea The rating area for which ratings should be returned.
     * @return A list of ratings data transfer object for the specific rating area.
     */
    public List<RatingDto> getRatingsByRatingArea(RatingArea ratingArea) {

        List<RatingDto> ratingDtos = new ArrayList<>() {};

        Iterable<RatingEntity> ratingEntities = repository.findByRatingarea(ratingArea);

        for(RatingEntity tmp : ratingEntities){
            ratingDtos.add(new RatingDto(tmp.id, tmp.criterion, tmp.category, tmp.ratingarea));
        }

        return ratingDtos;
    }

}
