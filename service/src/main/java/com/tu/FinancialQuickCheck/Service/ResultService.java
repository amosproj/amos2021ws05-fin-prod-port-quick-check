package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The ProductRatingService class performs service tasks and defines the logic for the product ratings. This includes
 * creating, updating or giving back product ratings
 */
@Service
public class ResultService {
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final RatingRepository ratingRepository;

    public ResultService(ProjectRepository repository, ProductRepository productRepository,
                         RatingRepository ratingRepository) {
        this.projectRepository = repository;
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     *
     *
     * @param projectID
     * @param productAreaID
     * @return List of ResultDto
     */
    public List<ResultDto> getResults(int projectID, Optional<String> productAreaID) {

        if(productAreaID.isEmpty()){
            return getResultsByProject(projectID);
        }else{
            try{
                int area = Integer.parseInt(productAreaID.get());
                return getResultsByProductArea(projectID, area);
            }catch (Exception e){
                throw new BadRequest("Input is missing/incorrect.");
            }
        }
    }

    public List<ResultDto> getResultsByProject(int projectID) {

        if(!projectRepository.existsById(projectID)){
            return null;
        }else{
            return returnDummyData();
        }
    }

    /**
     *
     *
     * @param projectID
     * @param productAreaID
     * @return List of ResultDto
     */
    public List<ResultDto> getResultsByProductArea(int projectID, int productAreaID) {

        if(!projectRepository.existsById(projectID)){
            return null;
        }else{
            return returnDummyData();
        }
    }

    // TODO: delete when data is correctly send (can potentially be used for testing)
    public List<ResultDto> returnDummyData() {

        List<ResultDto> dummyResult = new ArrayList<>();
        String[] ratingNames = {"Kreditvolumen im Bestand", "Marge", "Kunde"};
        String[] answers = {"700 Mio EUR", "2,5%", "10.0, 20.0, 70.0"};

        for(int i = 1; i < 2; i++){
            List<ProductRatingDto> ratings = new ArrayList<>();
            for(int j = 0; j < ratingNames.length; j++){
                ProductRatingDto p = new ProductRatingDto();
                RatingDto tmp = new RatingDto();
                tmp.id = j;
                tmp.criterion = ratingNames[j];
                p.rating = tmp;
                p.answer = answers[j];
                ratings.add(p);
            }

            List<ScoreDto> scores = new ArrayList<>();
            scores.add(new ScoreDto(Score.HOCH, 5));
            scores.add(new ScoreDto(Score.MITTEL, 7));
            scores.add(new ScoreDto(Score.GERING, 0));

            dummyResult.add(new ResultDto("productName" + i, ratings, scores));
        }

        return dummyResult;
    }

}
