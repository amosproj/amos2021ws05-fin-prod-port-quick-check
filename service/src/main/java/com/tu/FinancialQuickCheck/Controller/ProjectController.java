package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.dto.*;
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

    @Autowired
    private ProjectService service;
    @Autowired
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
        ProjectDto tmp = service.updateProject(projectDto, projectID);

        if(tmp == null){
            throw new BadRequest("Input is missing/incorrect.");
        }else{
            return tmp;
        }
    }


// TODO: auskommentiert lassen bisher keine Anforderung für diese Funktionalität
//    @DeleteMapping("/{projectID}")
//    void deleteByID(@PathVariable int projectID) {
//
//        projectService.deleteProject(projectID);
//
//    }

    //TODO (done - needs review) change output to empty list if no products exist
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

        return tmp;

    }


    //TODO: (done: needs review) change Path (see api def)
    //TODO: (prio: ???) fix output --> does not propagate values for productArea and projectID
    @PostMapping(value = "/{projectID}/products",
            consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> createProduct(@PathVariable int projectID, @RequestBody ProductDto productDto) {

        if(productDto.productArea != null && productDto.productName != null){
            List<ProductDto> tmp = productService.wrapper_createProduct(projectID, productDto);
            if(tmp == null){
                throw new BadRequest("Input is incorrect/missing");
            }else{

                return tmp;
            }
        }else{
            throw new BadRequest("Input is incorrect/missing");
        }
    }


//    TODO: (done - needs review) change according to API
    @PostMapping( value = "/{projectID}/users", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProjectUserDto> createProjectUser(@RequestBody List<ProjectUserDto> members,
                                                  @PathVariable int projectID) {

        return service.createProjectUsers(projectID, members);
    }

}
