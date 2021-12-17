package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.dto.ListOfProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAreaService {

    private ProductAreaRepository repository;

    public ProductAreaService(ProductAreaRepository productAreaRepository) {
        this.repository = productAreaRepository;
    }

    public List<ProductAreaDto> getAllProductAreas() {

        return new ListOfProductAreaDto(this.repository.findAll()).productAreas;

    }

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
