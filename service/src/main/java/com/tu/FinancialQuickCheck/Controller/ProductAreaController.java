package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductAreaService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * The ProductAreaController manages and processes requests for creating and returning product areas.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/productareas")
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
     * Retrieves all existing product areas from db.
     *
     * @throws ResourceNotFound if product_area_entity table is empty.
     * @return A list of all existing product areas.
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
     * Creates and persists a productArea entity to db.
     *
     * @param productArea The productArea object contains the necessary information.
     * @throws BadRequest if name and category of productArea are missing.
     * @return The created product area incl. unique identifier.
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductAreaDto createProductArea(@RequestBody ProductAreaDto productArea) {
        ProductAreaDto tmp = service.createProductArea(productArea);

        if (tmp == null) {
            throw new BadRequest("ProductArea cannot be created due to missing information.");
        }else {
            return tmp;
        }
    }
}
