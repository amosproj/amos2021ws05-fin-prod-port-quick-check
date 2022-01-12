package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProjectUserService;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/projects/{projectID}/users")
public class ProjectUserController {

    private ProjectUserService service;

    public ProjectUserController(ProjectUserService projectUserService){
        this.service = projectUserService;
    }

    @GetMapping(produces = "application/json")
    public List<ProjectUserDto> findProjectUsersByProjectId(@PathVariable int projectID) {
        List<ProjectUserDto> tmp = service.getProjectUsersByProjectId(projectID);

        if(tmp == null){
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            return tmp;
        }
    }


    //TODO: (done - needs review) change according to API
    @PutMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProjectUserDto> updateProjectUser(@RequestBody List<ProjectUserDto> projectUsers,
                                  @PathVariable int projectID) {

        List<ProjectUserDto> tmp = service.updateProjectUsers(projectID, projectUsers);

        if(tmp == null){
            throw new ResourceNotFound("Project or User not Found.");
        }else{
            return tmp;
        }
    }


    //TODO: (done - needs review) change according to API
     @DeleteMapping(produces = "application/json")
     public void deleteProjectUser(@RequestBody List<ProjectUserDto> projectUsers,
                                  @PathVariable int projectID) {

        if(!service.wrapperDeleteProjectUser(projectID, projectUsers)){
            throw new BadRequest("Input missing/is incorrect");
        }
    }

}
