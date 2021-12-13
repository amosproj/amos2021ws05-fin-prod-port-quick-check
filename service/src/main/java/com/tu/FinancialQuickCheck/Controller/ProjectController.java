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

    //TODO: (done - need review) --> return empty list or resource not found, what do you prefer?
    //TODO: (prio: medium) User Management - change output according to api or define new endpoint including role and list of projects for each user
    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        return service.getAllProjects();
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createByName(@RequestBody ProjectDto projectDto) {
        ProjectDto tmp = service.createProject(projectDto);

        if (tmp == null) {
            throw new BadRequest("Input is missing/incorrect");
        }else {
            return tmp;
        }
    }

    @GetMapping("/{projectID}")
    public ProjectDto findById(@PathVariable int projectID) {
        ProjectDto tmp = service.getProjectById(projectID);

        if (tmp == null){
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            return tmp;
        }

    }


    @PutMapping("/{projectID}")
    public ProjectDto updateById(@RequestBody ProjectDto projectDto, @PathVariable int projectID) {

        if(projectDto.members == null ||
                (projectDto.members != null && projectDto.members.isEmpty())){
            throw new BadRequest("Input is missing/incorrect.");
        }else{
            return service.updateProject(projectDto, projectID);
        }
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


    //TODO: (prio: high) change Path (see api def)
    @PostMapping(value = "/{projectID}/products",
            consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> createProduct(@PathVariable int projectID, @PathVariable int productAreaID,
                                    @RequestBody ProductDto productDto) {
        List<ProductDto> tmp = productService.createProduct(projectID, productAreaID, productDto);
        if(tmp == null){
            throw new BadRequest("Incorrect Input.");
        }else{
            return tmp;
        }
    }

}
