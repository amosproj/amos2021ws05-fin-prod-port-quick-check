package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    /**
     * Constructor for class ProductController.
     *
     * @param productService The different services for the product.
     */
    public ProductController(ProductService productService){
        this.service = productService;
    }

    /**
     * This method can find different products by their related ID.
     *
     * @param productID The ID of the product.
     * @return The product to their related ID.
     */
    @GetMapping("/{productID}")
    public ProductDto findById(@PathVariable int productID) {
        ProductDto tmp = service.findById(productID);

        if (tmp == null) {
            throw new ResourceNotFound("productID " + productID + " not found");
        }else{
            return tmp;
        }
    }

    /**
     * This method can update the products information like name, comments or resources.
     *
     * @param productDto The product data transfer object.
     * @param productID The ID of the product.
     * @throws BadRequest When a product cannot be updated because the input is missing or incorrect.
     */
    @PutMapping("/{productID}")
    public void updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer productID) {

        if(productDto.productName == null && productDto.comment == null && productDto.resources == null){
            throw new BadRequest("Input is missing/incorrect");
        }else{
            ProductDto tmp = service.updateById(productDto, productID);
            if(tmp == null){
                throw new ResourceNotFound("productID " + productID + " not found");
            }
        }
    }

// TODO: auskommentiert lassen bis geklärt ist, wie mit ProductVarianten umgegangen werden soll und was mit evtl. Ratings passieren soll die bereits existieren
//    @DeleteMapping("/{productID}")
//    void deleteByID(@PathVariable int productID) {
//        service.deleteProduct(productID);
//    }

}
