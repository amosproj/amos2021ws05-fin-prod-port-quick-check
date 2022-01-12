package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * The ProjectService class performs service tasks and defines the logic for the projects. This includes creating,
 * updating, finding or giving back projects
 */
@Service
@Slf4j
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAreaRepository productAreaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectUserRepository projectUserRepository;


    public ProjectService(ProjectRepository projectRepository, ProductRepository productRepository,
                          ProductAreaRepository productAreaRepository, UserRepository userRepository, ProjectUserRepository projectUserRepository) {
        this.repository = projectRepository;
        this.productRepository = productRepository;
        this.productAreaRepository = productAreaRepository;
        this.userRepository = userRepository;
        this.projectUserRepository = projectUserRepository;
    }


    /**
     * Lookup of all ProjectEntities in DB
     *
     * @return List<SmallProjectDto> only provides projectID and projectName
     */
    public List<SmallProjectDto> getAllProjects(){

        List<SmallProjectDto> smallProjectDtos = new ArrayList<>() {};
        Iterable<ProjectEntity> projectEntities = repository.findAll();
        
        for(ProjectEntity tmp : projectEntities){
            smallProjectDtos.add(new SmallProjectDto(tmp.id, tmp.name));
        }

        return smallProjectDtos;
    }


    /**
     * Adds a new ProjectEntity to DB
     * Required information: see Project.yaml
     * Creator of project needs to be included in projectDto.members
     * @return ProjectDto projectDto including created projectID
     */
    //TODO: (done - nothing changed) is the creator of the project included in the members list? --> answer: yes
    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        // Step 0: Check if input contains required information
        if(projectDto.projectName != null && projectDto.productAreas != null && projectDto.creatorID != null
                && projectDto.members != null && !projectDto.members.isEmpty() && !projectDto.productAreas.isEmpty()){

            // create db entry
            ProjectEntity newProject = new ProjectEntity();
            newProject.creatorID = projectDto.creatorID.toString();
            newProject.name = projectDto.projectName;

            // assign productAreas to project through DUMMY data in product_entity table
            newProject.productEntities = assignProductAreasToProject(projectDto, newProject, false);

            // assign members to projects
            newProject.projectUserEntities = new ArrayList<>();
            newProject.projectUserEntities = assignMembersToProject(projectDto.members, newProject);

            repository.save(newProject);

            // return created projectID
            return new ProjectDto(newProject);
        }else{
            return null;
        }
    }


    /**
     * Lookup of ProjectEntity in DB based on projectID
     *
     * @param projectID unique identifier for ProjectEntity
     * @return The project data transfer object for the belonging project ID.
     */
    public ProjectDto getProjectById(int projectID) {

        Optional<ProjectEntity> projectEntity = repository.findById(projectID);

        if (projectEntity.isEmpty()) {
            return null;
        }else{
            return new ProjectDto(projectEntity.get());
        }

    }


    /**
     * Updates an existing ProjectEntity in DB
     * Attributes/relations that can be updated: projectName, productAreas, members
     * Attributes that can not be updated: creatorID, projectID
     * @param projectID unique identifier for ProjectEntity
     * @param projectDto contains data that needs to be updated
     * @throws ResourceNotFound When the project ID is not found.
     */
    @Transactional
    public ProjectDto updateProject(ProjectDto projectDto, int projectID) {

        if(projectDto.members == null || projectDto.members.isEmpty()) {
            return null;
        }else{
            if (!repository.existsById(projectID)) {
                throw new ResourceNotFound("projectID " + projectID + " not found");
            }else{
                ProjectEntity entity = repository.findById(projectID).get();
                // update project name
                if(projectDto.projectName != null){entity.name = projectDto.projectName;}

                // add none existing product areas
                List<ProductEntity> newProductEntities = assignProductAreasToProject(projectDto, entity, true);
                entity.productEntities.addAll(newProductEntities);
                productRepository.saveAllAndFlush(newProductEntities);

                // unassign existing users from project
                long numDeletedRecords = projectUserRepository.deleteByProjectUserId_project(entity);
                repository.flush();

                // assign new users to project
                List<ProjectUserDto> newUsers = createProjectUsers(projectID, projectDto.members);

                repository.saveAndFlush(entity);
                return new ProjectDto(entity);
            }
        }
    }


    /**
     * This method is creating or adding users for projects.
     *
     * @param projectID The ID of the project for which users should be added.
     * @param members The project user with information like email, name or ID.
     * @return A list of project user data transfer objects for a specific project.
     */
    //TODO: (done - needs review) --> write automated tests
    public List<ProjectUserDto> createProjectUsers(int projectID, List<ProjectUserDto> members) {

        if(!repository.existsById(projectID)){
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            List<ProjectUserDto> out = new ArrayList<>();
            ProjectEntity project = repository.findById(projectID).get();

            List<ProjectUserEntity> newMembers = assignMembersToProject(members, project);

            for (ProjectUserEntity projectUserEntity : newMembers) {
                out.add(new ProjectUserDto(projectUserEntity));
            }

            projectUserRepository.saveAll(newMembers);
            return out;
        }
    }

    /**
     * This method is assigning users and their roles to specific projects.
     *
     * @param members The users which should be assigned to a project.
     * @param projectEntity The project for witch users should be assigned.
     * @throws BadRequest When the input is missing or incorrect.
     * @throws ResourceNotFound When the user does not exist.
     * @return A list of the users which are assgined to the specific project.
     */
    //TODO: (done - needs review) --> user_entity primary key changed
    public List<ProjectUserEntity> assignMembersToProject(List<ProjectUserDto> members, ProjectEntity projectEntity){
        List<ProjectUserEntity> assignedMembers = new ArrayList<>();

        // check if members exist
        Set<ProjectUserDto> newUsers = new HashSet<>(members);
        for(ProjectUserDto member: newUsers){
            if((member.userID == null && member.userEmail == null) || member.role == null){
                throw new BadRequest("Input is missing/incorrect");
            }else{
                Optional<UserEntity> u = userExists(member);
                if(u.isPresent()){
                    ProjectUserEntity newUserProject = new ProjectUserEntity();
                    newUserProject.projectUserId = new ProjectUserId(projectEntity, u.get());
                    newUserProject.role = member.role;
                    assignedMembers.add(newUserProject);
                }else{
                    throw new ResourceNotFound("User does not exist.");
                }
            }
        }
        return assignedMembers;
    }


    /**
     * This method checks if a user exists in database.
     *
     * @param user The user for which the existence in database has to be checked.
     * @throws BadRequest When the input is missing or incorrect.
     * @return The entity of the user if already existent in database.
     */
    //TODO: (done - needs review)
    public Optional<UserEntity> userExists(ProjectUserDto user){

        if(user.userID != null){
            return userRepository.findById(user.userID.toString());
        }else if(user.userEmail != null && !user.validateEmail(user.userEmail)){
            throw new BadRequest("Input is missing/incorrect");
        }else{
            return userRepository.findByEmail(user.userEmail);
        }
    }


    /**
     * This method is defining a product area (credit, client or payment) for a project
     *
     * @param projectDto The project data transfer object for which product areas should be defined.
     * @param project The project entity for which product areas should be defined.
     * @param isPut
     * @throws ResourceNotFound When the product area ID does not exist.
     * @return A list of product entities with product areas assigned to a project.
     */
    public List<ProductEntity> assignProductAreasToProject(ProjectDto projectDto, ProjectEntity project, Boolean isPut){

        List<ProductEntity> productEntities = new ArrayList<>();
        Set<ProductAreaDto> newProductAreas = new HashSet<>(projectDto.productAreas);
        for (ProductAreaDto productArea : newProductAreas){
            Optional<ProductAreaEntity> tmp = productAreaRepository.findById(productArea.id);
            if(tmp.isPresent()){
                if(isPut && productRepository.existsByProjectAndProductarea(project, tmp.get())){
                    continue;
                }else{
                    ProductEntity product = new ProductEntity();
                    product.project = project;
                    product.productarea = tmp.get();
                    product.name = "DUMMY";
                    productEntities.add(product);
                }
            }else{
                throw new ResourceNotFound("productArea " + productArea.id + " does not exist" );
            }
        }

        return productEntities;
    }

// TODO: auskommentiert lassen bisher keine Anforderung dafür vorhanden
//    public void deleteProject(int projectID) {
//        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);
//        if (projectEntity.isEmpty()) {
//            throw new ResourceNotFound("projectID " + projectID + " not found");
//        }else{
//            projectRepository.deleteById(projectID);
//        }
//    }
}
