package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductAreaRepository;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAreaService {

    private ProductAreaRepository productAreaRepository;

    @Autowired
    public ProductAreaService(ProductAreaRepository productAreaRepository) {
        this.productAreaRepository = productAreaRepository;
    }

    public List<ProductAreaDto> getAllProductAreas() {

        List<ProductAreaDto> productAreaDtos = new ArrayList<>() {
        };
        Iterable<ProductAreaEntity> productAreaEntities = productAreaRepository.findAll();

        for(ProductAreaEntity tmp : productAreaEntities){
            productAreaDtos.add(new ProductAreaDto(tmp.id, tmp.name, tmp.category));
        }

        return productAreaDtos;
    }

}
