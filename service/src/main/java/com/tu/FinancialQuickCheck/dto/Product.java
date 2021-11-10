package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.RatingEntity;

import java.util.ArrayList;
import java.util.List;

public class Product {

    public int id;

    public String name;

    public Score economicRating;

    public Score complexityRating;

    public List<Product> productVariations;

    public List<Rating> economicRatingCriterions;

    public List<Rating> complexityRatingCriterions;

    public Product(int id, String name, List<ProductEntity> productEntities, List<RatingEntity> ratingEntities)
    {
        this.id = id;
        this.name = name;
        this.productVariations = new ArrayList<>();
        for (ProductEntity p: productEntities) {
            productVariations.add(new Product(p.id, p.name, p.ratingEntities));
        }
        this.economicRatingCriterions = new ArrayList<>();
        this.complexityRatingCriterions = new ArrayList<>();
        for (RatingEntity r: ratingEntities) {
            if (r.area == RatingArea.ECONOMIC)
                economicRatingCriterions.add(new Rating(r.id, r.criterion, r.score,r.criterionValue,r.comment));
            else
                complexityRatingCriterions.add(new Rating(r.id, r.criterion, r.score,r.criterionValue,r.comment));
        }
        economicRating = computeRating(economicRatingCriterions);
        complexityRating = computeRating(complexityRatingCriterions);
    }


    public Product(int id, String name, List<RatingEntity> ratingEntities)
    {
        this.id = id;
        this.name = name;
        this.productVariations = new ArrayList<>();
        this.economicRatingCriterions = new ArrayList<>();
        this.complexityRatingCriterions = new ArrayList<>();

        for (RatingEntity r: ratingEntities) {
            if (r.area == RatingArea.ECONOMIC)
                economicRatingCriterions.add(new Rating(r.id, r.criterion, r.score,r.criterionValue,r.comment));
            else
                complexityRatingCriterions.add(new Rating(r.id, r.criterion, r.score,r.criterionValue,r.comment));
        }

        economicRating = computeRating(economicRatingCriterions);
        complexityRating = computeRating(complexityRatingCriterions);
    }

    private Score computeRating(List<Rating> values)
    {
        int sum = 0;
        for (Rating r: values) {
            sum += r.score.getValue();
        }
        return Score.valueOf(sum/values.size());
    }
}
