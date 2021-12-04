package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    // TODO: should we return a Reource not found if no projects exist?
    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        return service.getAllProjects();
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
    public List<ProductDto> findProductsByProject(@PathVariable int projectID) {
        return productService.getProductsByProjectId(projectID);
    }


    @GetMapping("{projectID}/productareas/{projectAreaID}/products")
    public List<ProductDto> findProductsByProductAndProjectArea(@PathVariable int projectID,
                                                                @PathVariable int projectAreaID) {
        return productService.getProductsByProjectIdAndProductAreaId(projectID, projectAreaID);
    }


    @PostMapping(value = "/{projectID}/productareas/{productAreaID}/products",
            consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@PathVariable int projectID, @PathVariable int productAreaID, @RequestBody ProductDto productDto) {
        ProductDto tmp = productService.createProduct(projectID, productAreaID, productDto);
        if(tmp == null){
            throw new BadRequest("Incorrect Input.");
        }else{
            return tmp;
        }
    }

}
