package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.dto.ProductDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProductService productService;



    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        return projectService.getAllProjects();
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createByName(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("/{projectID}")
    public ProjectDto findById(@PathVariable int projectID) {
        return projectService.findById(projectID);
    }

    // TODO: Should we return the updated ProjectedDTO?
    @PutMapping("/{projectID}")
    public void updateById(@RequestBody ProjectDto projectDto, @PathVariable int projectID) {

        projectService.updateById(projectDto, projectID);
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


    @GetMapping("{projectID}/productArea/{projectAreaID}/products")
    public List<ProductDto> findProductsByProductAndProjectArea(@PathVariable int projectID,
                                                                @PathVariable int projectAreaID) {
        return productService.getProductsByProjectIdAndProductAreaId(projectID, projectAreaID);
    }

    @PostMapping(value = "/{projectID}/productArea/{productAreaID}/products",
            consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@PathVariable int projectID, @PathVariable int productAreaID, @RequestBody ProductDto productDto) {
        return productService.createProduct(projectID, productAreaID, productDto);
    }

}
