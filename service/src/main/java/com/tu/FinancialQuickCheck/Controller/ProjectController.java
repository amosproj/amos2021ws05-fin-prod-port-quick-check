package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


@CrossOrigin
@RestController
@RequestMapping("projects")
public class ProjectController {

    private ProjectService service;
    private ProductService productService;

    public ProjectController(ProjectService projectService, ProductService productService){

        this.service = projectService;
        this.productService = productService;
    }


    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        List<SmallProjectDto> tmp = service.getAllProjects();

        if(tmp.isEmpty()){
            throw new ResourceNotFound("No projects found.");
        }else{
            return tmp;
        }
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createByName(@RequestBody ProjectDto projectDto) {
        ProjectDto tmp = service.createProject(projectDto);

        if (tmp == null) {
            throw new BadRequest("Project cannot be created due to missing information.");
        }else {
            return tmp;
        }
    }

    @GetMapping("/{projectID}")
    public ProjectDto findById(@PathVariable int projectID) {
        return service.getProjectById(projectID);
    }

    // TODO: Should we return the updated ProjectedDTO?
    @PutMapping("/{projectID}")
    public void updateById(@RequestBody ProjectDto projectDto, @PathVariable int projectID) {

        service.updateProject(projectDto, projectID);
    }

// TODO: auskommentiert lassen bisher keine Anforderung für diese Funktionalität
//    @DeleteMapping("/{projectID}")
//    void deleteByID(@PathVariable int projectID) {
//
//        projectService.deleteProject(projectID);
//
//    }


    @GetMapping("/{projectID}/products")
    public List<ProductDto> findProductsByProject(@PathVariable int projectID,
                                                  @RequestParam(required = false) Optional<String> productArea) {
        List<ProductDto> tmp;

        if(productArea.isEmpty()){
            tmp = productService.getProductsByProjectId(projectID);
        }else{
            try{
                int area = Integer.parseInt(productArea.get());
                tmp = productService.getProductsByProjectIdAndProductAreaId(projectID, area);
            }catch (Exception e){
                throw new BadRequest("Input missing/incorrect.");
            }
        }

        if(tmp.isEmpty()){
            throw new ResourceNotFound("No products found");
        }else{
            return tmp;
        }

    }


    @PostMapping(value = "/{projectID}/productareas/{productAreaID}/products",
            consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@PathVariable int projectID, @PathVariable int productAreaID,
                                    @RequestBody ProductDto productDto) {
        ProductDto tmp = productService.createProduct(projectID, productAreaID, productDto);
        if(tmp == null){
            throw new BadRequest("Incorrect Input.");
        }else{
            return tmp;
        }
    }

}
