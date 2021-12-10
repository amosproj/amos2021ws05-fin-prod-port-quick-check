package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProjectUserService;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: (ask frontend)(prio: high) check if endpoints needet -> endpoints definiton in projects.yaml


@RestController
@RequestMapping("/projects/{projectID}/users")
public class ProjectUserController {

    @Autowired
    private ProjectUserService service;

    @GetMapping(produces = "application/json")
    public List<ProjectUserDto> findProjectUsersByProjectId(@PathVariable int projectID) {
        return service.getProjectUsersByProjectId(projectID);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProjectUser(@RequestBody ProjectUserDto projectUserDto,
                                            @PathVariable int projectID) {
        service.createProjectUser(projectID, projectUserDto);
    }

    @PutMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProjectUser(@RequestBody ProjectUserDto projectUserDto,
                                  @PathVariable int projectID) {
        service.updateProjectUser(projectID, projectUserDto);
    }


     @DeleteMapping(produces = "application/json")
     public void deleteProjectUser(@RequestBody ProjectUserDto projectUserDto,
                                  @PathVariable int projectID) {
        service.deleteProjectUser(projectID, projectUserDto);
    }

}
