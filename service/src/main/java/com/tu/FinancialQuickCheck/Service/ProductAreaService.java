package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The ProductAreaService class performs service tasks and defines the logic for the product area. This includes
 * creating or giving back product areas.
 */
@Service
public class ProductAreaService {

    private ProductAreaRepository repository;

    public ProductAreaService(ProductAreaRepository productAreaRepository) {
        this.repository = productAreaRepository;
    }

    /**
     * This method is giving back a list of product areas with the ID, name and category.
     *
     * @return A list of product area DTO's.
     */
    public List<ProductAreaDto> getAllProductAreas() {

        List<ProductAreaDto> productAreaDtos = new ArrayList<>() {
        }; //TODO: (Alex) check different definition
        Iterable<ProductAreaEntity> productAreaEntities = repository.findAll();

        for(ProductAreaEntity tmp : productAreaEntities){
            productAreaDtos.add(new ProductAreaDto(tmp.id, tmp.name, tmp.category));
        }

        return productAreaDtos;
    }

    /**
     * This method is creating a new product area (credit, client or payment). Before the method is checking if there is
     * already a product area with same category, name and ID existent in database.
     *
     * @param productArea The product area which is to be created.
     * @return A new product area DTO.
     */
    // TODO: (prio: super low) check einbauen, ob Kombination aus Name und Category bereits besteht (wie geht man mit Rechtschreibfehlern um?)
    public ProductAreaDto createProductArea(ProductAreaDto productArea) {

        if(productArea.name != null && productArea.category != null ){
            ProductAreaEntity newEntity = new ProductAreaEntity();
            newEntity.category = productArea.category;
            newEntity.name = productArea.name;

            repository.save(newEntity);

            return new ProductAreaDto(newEntity.id, newEntity.name, newEntity.category);
        }else{
            return null;
        }
    }

}
