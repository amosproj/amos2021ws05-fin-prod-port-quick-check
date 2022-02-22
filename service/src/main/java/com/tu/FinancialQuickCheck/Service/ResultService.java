package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The ResultService class performs service tasks and defines the logic for the results. This includes
 * updating result scores or ratings, updating those and returning the result ratings or scores.
 */
@Service
public class ResultService {

    @Autowired
    private final ProductRatingRepository productRatingRepository;

    public static final Integer[] SET_VALUES = new Integer[] {4, 5, 10};
    public static final Set<Integer> RATINGS = new HashSet<>(Arrays.asList(SET_VALUES));
    public static final Integer SCORES = 9;

    /**
     * Class constructor initializes result repository
     * */
    public ResultService(ProductRatingRepository productRatingRepository) {
        this.productRatingRepository = productRatingRepository;
    }

    /**
     * Retrieves all existing results from db.
     *
     * @return A list of results, is empty if no results exist
     * */
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

            for(Integer key: table.keySet()){
                table.get(key).setCounts();
            }

            return new ArrayList<>(table.values());
        } catch (Exception e){
            throw new NullPointerException("List<ProductRatingEntity> not initilized.");
        }
    }

    /**
     * Updates the result rating for a result entity
     *
     * @param table A list of results which should get updated
     * @param p Product Rating's database entity
     * @throws NullPointerException if the rating table or the ProductRatingEntity is missing
     */
    public void updateResultRating(Hashtable<Integer, ResultDto> table, ProductRatingEntity p){

        try {
            ResultDto tmp = getResultDto(table, p.productRatingId.getProduct().id);
            tmp.updateProductInfos(p.productRatingId.getProduct().id, p.productRatingId.getProduct().name);
            if (RATINGS.contains(p.productRatingId.getRating().id)) {
                tmp.ratings.add(new ProductRatingDto(p.answer, p.score, p.productRatingId.getRating()));
            }
            table.put(p.productRatingId.getProduct().id, tmp);
        }catch (Exception e){
            throw new NullPointerException("Table is Empty or ProductRatingEntity is missing");
        }

    }

    /**
     * This method can update a result score between 1 and 9
     *
     * @param table The resultDto contains information for a result
     * @param p Product Rating's database entity
     */
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

    /**
     * Retrieves a result data transfer object from db.
     *
     * @param table The resultDto contains information for a result
     * @param productId The productID of the product entity for which results want to get retrieved
     * @return
     */
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

}
