package com.tu.FinancialQuickCheck.Controller;


import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productID}")
    public ProductDto findById(@PathVariable int productID) {
        return productService.findById(productID);
    }

    @PostMapping()
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.createProduct(productDto.projectID, productDto.productAreaID,productDto);
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
