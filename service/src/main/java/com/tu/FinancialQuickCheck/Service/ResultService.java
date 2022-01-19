package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ResultService {
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductRatingRepository productRatingRepository;

    public static final Integer[] SET_VALUES = new Integer[] {4, 5, 10};
    public static final Set<Integer> RATINGS = new HashSet<>(Arrays.asList(SET_VALUES));
    public static final Integer SCORES = 9;

    public ResultService(ProjectRepository repository, ProductRepository productRepository,
                         ProductRatingRepository productRatingRepository) {
        this.projectRepository = repository;
        this.productRepository = productRepository;
        this.productRatingRepository = productRatingRepository;
    }

    public List<ResultDto> getResults(int projectID, Optional<String> productAreaID) {
        List<ProductRatingEntity> tmp;

        if(productAreaID.isEmpty()){
            tmp = productRatingRepository.getResultsByProject(projectID);
        }else{
            try{
                int area = Integer.parseInt(productAreaID.get());
                tmp = productRatingRepository.getResultsByProjectAndProductArea(projectID, area);
            }catch (Exception e){
                throw new BadRequest("Input is missing/incorrect.");
            }
        }

        return convertEntitiesToResultDtos(tmp);
    }

    public List<ResultDto> convertEntitiesToResultDtos(List<ProductRatingEntity> productEntities){
        try {
            Hashtable<Integer, ResultDto> table = new Hashtable<>();

            for (ProductRatingEntity p : productEntities) {
                if (p.productRatingId.getProduct().parentProduct == null) {
                    updateResultRating(table, p);
                } else {
                    updateResultScore(table, p);
                }

            }

            return new ArrayList<>(table.values());
        } catch (Exception e){
            throw new NullPointerException("List<ProductRatingEntity> not initilized.");
        }
    }

    public void updateResultRating(Hashtable<Integer, ResultDto> table, ProductRatingEntity p){

        try {
            ResultDto tmp = getResultDto(table, p.productRatingId.getProduct().id);
            tmp.updateProductInfos(p.productRatingId.getProduct().id, p.productRatingId.getProduct().name);
            if (RATINGS.contains(p.productRatingId.getRating().id)) {
                tmp.ratings.add(new ProductRatingDto(p.answer, p.score, p.productRatingId.getRating()));
            }
            table.put(p.productRatingId.getProduct().id, tmp);
        }catch (Exception e){
            throw new BadRequest("Table is Empty or ProductRatingEntity is missing");
        }

    }

    public void updateResultScore(Hashtable<Integer, ResultDto> table, ProductRatingEntity p){
        try {
            ResultDto tmp = getResultDto(table, p.productRatingId.getProduct().parentProduct.id);
            tmp.updateProductInfos(p.productRatingId.getProduct().parentProduct.id,
                    p.productRatingId.getProduct().parentProduct.name);

            if (p.productRatingId.getRating().id == SCORES && p.score != null) {
                int index = p.score.getValue() - 1;
                int current_count = tmp.scores[index].count;
                tmp.scores[index].count = current_count + 1;
            }
            table.put(p.productRatingId.getProduct().parentProduct.id, tmp);
        }catch (Exception e){
            throw new NullPointerException("Table is not initilized, entity p does not have a parent or parent entity does not have a name or id.");
        }
    }

    public ResultDto getResultDto(Hashtable<Integer, ResultDto> table, Integer productId){
        try {
            if (table.containsKey(productId)) {
                return table.get(productId);
            } else {
                return new ResultDto();
            }
        } catch (Exception e){
            throw new NullPointerException("Table is not initilized.");
        }

    }

    // TODO: use if we do not finish the implementation
//    public List<ResultDto> returnDummyData() {
//
//        List<ResultDto> dummyResult = new ArrayList<>();
//        String[] ratingNames = {"Kreditvolumen im Bestand", "Marge", "Kunde"};
//        String[] answers = {"700 Mio EUR", "2,5%", "10.0, 20.0, 70.0"};
//
//        for(int i = 1; i < 2; i++){
//            List<ProductRatingDto> ratings = new ArrayList<>();
//            for(int j = 0; j < ratingNames.length; j++){
//                ProductRatingDto p = new ProductRatingDto();
//                RatingDto tmp = new RatingDto();
//                tmp.id = j;
//                tmp.criterion = ratingNames[j];
//                p.rating = tmp;
//                p.answer = answers[j];
//                ratings.add(p);
//            }
//
//            ScoreDto[] scores = new ScoreDto[3];
//            scores[2] = new ScoreDto(Score.HOCH, 5);
//            scores[1] = new ScoreDto(Score.MITTEL, 7);
//            scores[0] = new ScoreDto(Score.GERING, 0);
//
//            dummyResult.add(new ResultDto(i, "productName" + i, ratings, scores));
//        }
//
//        return dummyResult;
//    }

}
