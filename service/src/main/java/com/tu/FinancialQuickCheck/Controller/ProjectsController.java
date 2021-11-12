package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("projects")
public class ProjectsController {

    @Autowired
    private ProjectService projectService;


    // TODO: custome http responses implementieren (siehe projects.yaml)
    @GetMapping(produces = "application/json")
    public List<SmallProjectDto> findALL() {
        return projectService.getAllProjects();
    }

    // TODO: custome http responses implementieren (siehe projects.yaml)
    // TODO: response body evtl. nochmal anpassen, wenn klar ist ob nur projectID zurück geschickt werden soll
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createByName(@RequestBody ProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("/{projectID}")
    public ProjectDto findById(@PathVariable int projectID) {
        return projectService.findById(projectID);
    }


    @PutMapping("/{projectID}")
    public void findById(@RequestBody ProjectDto projectDto) {
        projectService.updateById(projectDto);
    }


    @DeleteMapping("/{projectID}")
    void deleteByID(@PathVariable int projectID) {

        projectService.deleteProject(projectID);

    }

}
