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

}
