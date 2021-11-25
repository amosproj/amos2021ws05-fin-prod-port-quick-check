package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.RatingEntity;
import com.tu.FinancialQuickCheck.db.RatingRepository;
import com.tu.FinancialQuickCheck.dto.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository repository) {
        this.ratingRepository = repository;
    }


    public List<RatingDto> getAllRatings() {

        List<RatingDto> ratingDtos = new ArrayList<>() {};

        Iterable<RatingEntity> ratingEntities = this.ratingRepository.findAll();


        for(RatingEntity tmp : ratingEntities){
            ratingDtos.add(new RatingDto(tmp.id, tmp.criterion, tmp.category, tmp.ratingarea));
        }

        return ratingDtos;
    }


    public List<RatingDto> getRatingsByRatingArea(RatingArea ratingArea) {

        List<RatingDto> ratingDtos = new ArrayList<>() {};

        Iterable<RatingEntity> ratingEntities = ratingRepository.findByRatingarea(ratingArea);

        for(RatingEntity tmp : ratingEntities){
            ratingDtos.add(new RatingDto(tmp.id, tmp.criterion, tmp.category, tmp.ratingarea));
        }

        return ratingDtos;
    }

}
