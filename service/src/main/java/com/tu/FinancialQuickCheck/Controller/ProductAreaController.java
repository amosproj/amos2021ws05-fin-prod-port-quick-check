package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("productareas")
public class ProductAreaController {

    @Autowired
    private ProductAreaService productAreaService;


    // TODO: custome http responses implementieren (siehe projects.yaml)
    @GetMapping(produces = "application/json")
    public List<ProductAreaDto> findALL() {
        return productAreaService.getAllProductAreas();
    }
}
