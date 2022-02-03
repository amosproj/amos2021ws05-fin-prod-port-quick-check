package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.dto.ListOfProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * The ProductAreaService class performs service tasks and defines the logic for the product area. This includes
 * creating or giving back product areas.
 */
@Service
public class ProductAreaService {

    private ProductAreaRepository repository;

    /**
     * Class constructor initilizes productArea repository
     * */
    public ProductAreaService(ProductAreaRepository productAreaRepository) {
        this.repository = productAreaRepository;
    }

    /**
     * Retrieves all existing product areas from db.
     *
     * @return A list of product areas, is empty if no product areas exist
     * */
    public List<ProductAreaDto> getAllProductAreas() {
        return new ListOfProductAreaDto(this.repository.findAll()).productAreas;
    }

    /**
     * Creates and persists a productArea entity to db if name and category are provided.
     *
     * @param productArea The productArea object contains the necessary information.
     * @return The created product area incl. unique identifier or null if input is missing
     */
    // TODO: (prio: super low) check einbauen, ob Kombination aus Name und Category bereits besteht (wie geht man mit Rechtschreibfehlern um?)
    public ProductAreaDto createProductArea(ProductAreaDto productArea) {

        if(productArea.name != null && productArea.category != null ){
            ProductAreaEntity newEntity = new ProductAreaEntity();
            newEntity.category = productArea.category;
            newEntity.name = productArea.name;

            repository.save(newEntity);

            return new ProductAreaDto(newEntity);
        }else{
            return null;
        }
    }

}
