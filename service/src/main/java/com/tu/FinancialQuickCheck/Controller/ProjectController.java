package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.Service.ResultService;
import com.tu.FinancialQuickCheck.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private ResultService resultService;

    /**
     * Constructor for class ProjectController.
     *
     * @param projectService The different services for the project.
     * @param productService The different services for the products.
     */
    public ProjectController(ProjectService projectService, ProductService productService, ResultService resultService){

        this.service = projectService;
        this.productService = productService;
        this.resultService = resultService;
    }

    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        return service.getAllProjects();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto tmp = service.createProject(projectDto);

        if (tmp == null) {
            throw new BadRequest("Input is missing/incorrect");
        }else {
            return tmp;
        }
    }

    /**
     * This method is finding projects by their ID.
     *
     * @param projectID The ID of the project which has to be find.
     * @throws ResourceNotFound When the projectID was not found.
     * @return The project that had to be find by their ID.
     */
    @GetMapping("/{projectID}")
    public ProjectDto findById(@PathVariable int projectID) {
        ProjectDto tmp = service.getProjectById(projectID);

        if (tmp == null){
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            return tmp;
        }

    }

    /**
     * This method is updating project information like name, areas or members.
     *
     * @param projectDto The project data transfer object.
     * @param projectID The ID of the project that has to be updated.
     * @throws BadRequest When the input is missing or incorrect.
     * @return The updated ProjectEntity in DB.
     */
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

    /**
     * This method is finding the products for projects.
     *
     * @param projectID The ID of the project for which products can be find.
     * @param productArea The product area of the product which can be find.
     * @throws BadRequest When the input is missing or incorrect.
     * @return The products for a project or the products for a project and their related product area.
     */
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
                throw new BadRequest("Input is missing/incorrect.");
            }
        }

        return tmp;
    }

    /**
     * This method can create or add products to projects.
     *
     * @param projectID The ID of the project for which products can be added.
     * @param productDto The product data transfer object.
     * @throws BadRequest When the input is incorrect or missing.
     * @return A list of products which have been added to the project.
     */
    //TODO: (done: needs review) change Path (see api def)
    //TODO: (prio: ???) fix output --> does not propagate values for productArea and projectID
    @PostMapping(value = "/{projectID}/products",
            consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> createProduct(@PathVariable int projectID, @RequestBody ProductDto productDto) {

        if(productDto.productArea != null && productDto.productName != null){
            List<ProductDto> tmp = productService.wrapperCreateProduct(projectID, productDto);
            if(tmp == null){
                throw new BadRequest("Input is missing/incorrect");
            }else{

                return tmp;
            }
        }else{
            throw new BadRequest("Input is missing/incorrect");
        }
    }

    /**
     * This method can add users/members to projects.
     *
     * @param members The users/members who can be added to the project.
     * @param projectID The ID of the project for which members/users can be added.
     * @return New users/members were added to the project.
     */
//    TODO: (done - needs review) change according to API
    @PostMapping( value = "/{projectID}/users", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProjectUserDto> createProjectUser(@RequestBody List<ProjectUserDto> members,
                                                  @PathVariable int projectID) {

        List<ProjectUserDto> tmp = service.createProjectUsers(projectID, members);

        if(tmp == null){
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            return tmp;
        }
    }

    @GetMapping("/{projectID}/results")
    public List<ResultDto> getResults(@PathVariable int projectID,
                                       @RequestParam(required = false) Optional<String> productAreaID) {

        List<ResultDto> tmp = resultService.getResults(projectID, productAreaID);

        if(tmp == null){
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            return tmp;
        }
    }

}
