package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Service.ProjectUserService;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/projects/{projectID}/users")
public class ProjectUserController {

    @Autowired
    private ProjectUserService service;

    @GetMapping(produces = "application/json")
    public List<ProjectUserDto> findProjectUsersByProjectId(@PathVariable int projectID) {
        return service.getProjectUsersByProjectId(projectID);
    }

    @PostMapping(value = "/{email}",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProjectUser(@RequestBody ProjectUserDto projectUserDto,
                                            @PathVariable int projectID, @PathVariable String email) {
        service.createProjectUser(projectID, email, projectUserDto);
    }

    @PutMapping(value = "/{userID}",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProjectUser(@RequestBody ProjectUserDto projectUserDto,
                                  @PathVariable int projectID, @PathVariable String userID) {
        service.updateProjectUser(projectID, UUID.fromString(userID), projectUserDto);
    }

    // TODO: implement delete
//    @DeleteMapping(produces = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void updateProjectUser(@RequestBody ProjectUserDto projectUserDto,
//                                  @PathVariable int projectID, @PathVariable String userID) {
//        service.updateProjectUser(projectID, UUID.fromString(userID), projectUserDto);
//    }

}
