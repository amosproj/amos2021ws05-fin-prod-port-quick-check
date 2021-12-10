package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAreaService {

    private ProductAreaRepository repository;

    public ProductAreaService(ProductAreaRepository productAreaRepository) {
        this.repository = productAreaRepository;
    }

    public List<ProductAreaDto> getAllProductAreas() {

        List<ProductAreaDto> productAreaDtos = new ArrayList<>() {
        }; //TODO: (Alex) check diferent definition
        Iterable<ProductAreaEntity> productAreaEntities = repository.findAll();

        for(ProductAreaEntity tmp : productAreaEntities){
            productAreaDtos.add(new ProductAreaDto(tmp.id, tmp.name, tmp.category));
        }

        return productAreaDtos;
    }

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
