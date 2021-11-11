package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.db.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping(produces = "application/json")
    public Iterable<ProjectEntity> findALL() {
        return projectService.getAllProjects();
    }

    @PostMapping(consumes = "application/json")
    ProjectEntity createByName(@RequestBody ProjectEntity project) {
        return projectService.createProject(project);
    }

    // TODO: id nicht gefunden
    @GetMapping("/{projectID}")
    public Optional<ProjectEntity> findById(@PathVariable int projectID) {
        return projectService.findById(projectID);
    }

    // TODO: id nicht gefunden
    @DeleteMapping("/{projectID}")
    void deleteByID(@PathVariable int projectID) {
        projectService.deleteProject(projectID);
    }

    @GetMapping("/test")
    public String index() {
        return "Test";
    }

}
