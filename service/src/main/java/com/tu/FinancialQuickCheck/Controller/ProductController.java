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

    //TODO: ask frontend if endpoint is needed
    @GetMapping("/{productID}")
    public ProductDto findById(@PathVariable int productID) {
        return service.findById(productID);
    }

    //TODO: ask frontend if endpoint is needed
    @PutMapping("/{productID}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer productID) {
        ProductDto tmp = service.updateById(productDto, productID);
        if(tmp == null){
            throw new BadRequest("Input missing/incorrect");
        }
    }

// TODO: auskommentiert lassen bis geklärt ist, wie mit ProductVarianten umgegangen werden soll und was mit evtl. Ratings passieren soll die bereits existieren
//    @DeleteMapping("/{productID}")
//    void deleteByID(@PathVariable int productID) {
//        service.deleteProduct(productID);
//    }

}
