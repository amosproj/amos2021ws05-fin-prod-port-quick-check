package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productareas")
public class ProductAreaController {

    @Autowired
    private ProductAreaService productAreaService;


    @GetMapping(produces = "application/json")
    public List<ProductAreaDto> findALL() {
        return productAreaService.getAllProductAreas();
    }

    @PostMapping(consumes = "application/json")
    public void createProductArea(@RequestBody ProductAreaDto productArea) {
        productAreaService.createProductArea(productArea);
    }
}
