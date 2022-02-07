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
     * Class Constructor specifying ProductService.
     */
    public ProductController(ProductService productService){
        this.service = productService;
    }

    /**
     * Retrieves product information from db based on productID.
     *
     * @param productID The productID is the unique identifier of the product entity in db
     * @throws ResourceNotFound if product entity with productID does not exist in db.
     * @return A ProductDto if exist
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
     * Updates attributes name and comment of product entity in db. At least one of the attributes
     * has to be provided.
     *
     * @param productDto The productDto contains attributes productName and comment for update
     * @param productID The productID is the unique identifier of the product entity in db
     * @throws BadRequest if no information for update is provided
     * @throws ResourceNotFound if product entity with productID does not exist in db.
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

}
