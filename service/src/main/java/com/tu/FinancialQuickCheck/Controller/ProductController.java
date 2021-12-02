package com.tu.FinancialQuickCheck.Controller;


import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService productService){
        this.service = productService;
    }

    @GetMapping("/{productID}")
    public ProductDto findById(@PathVariable int productID) {
        return service.findById(productID);
    }

    @PutMapping("/{productID}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer productID) {
        ProductDto tmp = service.updateById(productDto, productID);
        if(tmp == null){
            throw new BadRequest("Missing Input.");
        }
    }

    @DeleteMapping("/{productID}")
    void deleteByID(@PathVariable int productID) {
        service.deleteProduct(productID);
    }

}
