package com.tu.FinancialQuickCheck.Controller;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProjectUserService;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ProjectUserController manages and processes requests for adding and deleting users from projects.
 */
@CrossOrigin(origins = "http://localhost:3000")
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

    /**
     * This method is updating/adding users to a project.
     *
     * @param projectUsers The user who can be added or updated to a project.
     * @param projectID The ID of the project for that a user can be added or updated.
     * @throws ResourceNotFound When the project or the user cannot be found.
     * @return The updated or added users to a project.
     */
    @PutMapping(produces = "application/json")
    public List<ProjectUserDto> updateProjectUser(@RequestBody List<ProjectUserDto> projectUsers,
                                  @PathVariable int projectID) {

        List<ProjectUserDto> tmp = service.updateProjectUsers(projectID, projectUsers);

        if(tmp == null){
            throw new ResourceNotFound("Project or User not Found.");
        }else{
            return tmp;
        }
    }

    /**
     * This method is deleting users from a project.
     *
     * @param projectUsers The user which can be deleted out of the project.
     * @param projectID The ID of the project for that a user can be deleted.
     */
     @DeleteMapping(produces = "application/json")
     public void deleteProjectUser(@RequestBody List<ProjectUserDto> projectUsers,
                                  @PathVariable int projectID) {

        if(!service.wrapperDeleteProjectUser(projectID, projectUsers)){
            throw new BadRequest("Input missing/is incorrect");
        }
    }

}
