package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("projects")
public class ProjectsController {

    @Autowired
    private ProjectService projectService;


    // TODO: custom http responses implementieren (siehe projects.yaml)
    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        return projectService.getAllProjects();
    }

    // TODO: custome http responses implementieren (siehe projects.yaml)
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createByName() {
        return projectService.createProject();
    }

    @GetMapping("/{projectID}")
    public ProjectDto findById(@PathVariable int projectID) {
        return projectService.findById(projectID);
    }


    @PutMapping("/{projectID}")
    public void findById(@RequestBody ProjectDto projectDto, @PathVariable Integer projectID) {

        projectService.updateById(projectDto, projectID);
    }


    @DeleteMapping("/{projectID}")
    void deleteByID(@PathVariable int projectID) {

        projectService.deleteProject(projectID);

    }

}
