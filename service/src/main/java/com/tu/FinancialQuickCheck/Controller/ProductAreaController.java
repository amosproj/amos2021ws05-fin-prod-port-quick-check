package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("productareas")
public class ProductAreaController {

    private ProductAreaService service;

    public ProductAreaController(ProductAreaService productAreaService){
        this.service = productAreaService;
    }

    @GetMapping(produces = "application/json")
    public List<ProductAreaDto> findALL() {

        List<ProductAreaDto> p = service.getAllProductAreas();

        if(p.isEmpty()){
            throw new ResourceNotFound("No Product Areas found.");
        }else{
            return p;
        }
    }


    @PostMapping(consumes = "application/json")
    public ProductAreaDto createProductArea(@RequestBody ProductAreaDto productArea) {
        ProductAreaDto tmp = service.createProductArea(productArea);

        if (tmp == null) {
            throw new BadRequest("ProductArea cannot be created due to missing information.");
        }else {
            return tmp;
        }
    }
}
