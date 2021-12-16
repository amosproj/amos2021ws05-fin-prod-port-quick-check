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

    //TODO: (done - needs review) add attribute comment
    //TODO: (prio: ??) change output --> waiting for API review to finilize
    @PutMapping("/{productID}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        if(productDto.productName == null && productDto.comment == null && productDto.resources == null){
            throw new BadRequest("Input is missing/incorrect");
        }else{
            ProductDto tmp = service.updateById(productDto, productID);
            if(tmp == null){
                throw new BadRequest("Input missing/incorrect");
            }
        }
    }

// TODO: auskommentiert lassen bis geklärt ist, wie mit ProductVarianten umgegangen werden soll und was mit evtl. Ratings passieren soll die bereits existieren
//    @DeleteMapping("/{productID}")
//    void deleteByID(@PathVariable int productID) {
//        service.deleteProduct(productID);
//    }

}
