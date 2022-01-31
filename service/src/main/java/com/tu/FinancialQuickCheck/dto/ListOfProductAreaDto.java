package com.tu.FinancialQuickCheck.dto;


import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListOfProductAreaDto {

    public List<ProductAreaDto> productAreas;

    public ListOfProductAreaDto(Iterable<ProductAreaEntity> productAreaEntities){

        this.productAreas = new ArrayList<>();

        for(ProductAreaEntity tmp : productAreaEntities){
            productAreas.add(new ProductAreaDto(tmp));
        }
    }

    public ListOfProductAreaDto(ProjectEntity project) {
        //TODO: (prio: low) greift alle Produktdaten für project ab, es würde ausreichen nur die DUMMY Daten abzugreifen
        HashSet<ProductAreaDto> areas = new HashSet<>();

        for (ProductEntity product: project.productEntities)
        {
            areas.add(new ProductAreaDto(product.productarea));
        }

        this.productAreas = new ArrayList<>(areas);
    }

}
