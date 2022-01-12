package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ProductAreaController manages and processes requests for creating product areas or finding existing ones
 */

@CrossOrigin
@RestController
@RequestMapping("productareas")
public class ProductAreaController {

    private ProductAreaService service;

    /**
     * Constructor for class ProductAreaController.
     *
     * @param productAreaService The different services for the product areas.
     */
    public ProductAreaController(ProductAreaService productAreaService){
        this.service = productAreaService;
    }

    /**
     * This method is returning existing product areas for a project.
     *
     * @throws ResourceNotFound When no product area was found.
     * @return All product areas for a project.
     */
    @GetMapping(produces = "application/json")
    public List<ProductAreaDto> findALL() {

        List<ProductAreaDto> p = service.getAllProductAreas();

        if(p.isEmpty()){
            throw new ResourceNotFound("No Product Areas found.");
        }else{
            return p;
        }
    }

    /**
     * This method is for creating and adding product areas to projects.
     *
     * @param productArea The product area with related information about name, id and category.
     * @throws BadRequest When a product area cannot be created.
     * @return The created product area as a ProductAreaDto.
     */
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
