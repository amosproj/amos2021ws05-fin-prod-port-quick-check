package com.tu.FinancialQuickCheck.Controller;


import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/{productID}")
    public ProductDto findById(@PathVariable int productID) {
        return productService.findById(productID);
    }


    @PutMapping("/{productID}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        productService.updateById(productDto, productID);
    }

    @DeleteMapping("/{productID}")
    void deleteByID(@PathVariable int productID) {
        productService.deleteProduct(productID);
    }

    @GetMapping("/projects/{projectID}")
    public List<ProductDto> findProductsByProject(@PathVariable int projectID) {
        return productService.getProductsByProjectId(projectID);
    }

    @GetMapping("projects/{projectID}/productArea/{projectAreaID}")
    public List<ProductDto> findProductsByProductAndProjectArea(@PathVariable int projectID,
                                                                @PathVariable int projectAreaID) {
        return productService.getProductsByProjectIdAndProductAreaId(projectID, projectAreaID);
    }


}
