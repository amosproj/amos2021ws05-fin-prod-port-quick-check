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

@Service
@Slf4j
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

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
//        this.projectRepository = projectRepository;
//        this.productRepository = productRepository;
//        this.productAreaRepository = productAreaRepository;
//        this.userRepository = userRepository;
//        this.projectUserRepository = projectUserRepository;
    }


    /**
     * lookup of all ProjectEntities in DB
     *
     * @return List<SmallProjectDto> only provides projectID and projectName
     */
    public List<SmallProjectDto> getAllProjects(){

        List<SmallProjectDto> smallProjectDtos = new ArrayList<>() {};
        Iterable<ProjectEntity> projectEntities = projectRepository.findAll();
        
        for(ProjectEntity tmp : projectEntities){
            smallProjectDtos.add(new SmallProjectDto(tmp.id, tmp.name));
        }

        return smallProjectDtos;
    }


    /**
     * adds a new ProjectEntity to DB
     * required information: see Project.yaml
     *
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

            // add product areas to project through DUMMY data in product_entity table
            newProject.productEntities = new ArrayList<>();
            Set<ProductAreaDto> newProductAreas = new HashSet<>(projectDto.productAreas);
            for (ProductAreaDto productArea : newProductAreas){
                Optional<ProductAreaEntity> tmp = productAreaRepository.findById(productArea.id);
                if(tmp.isPresent()){
//                    System.out.println("Create DUMMY data for productArea: " + productArea.id);
                    ProductEntity product = new ProductEntity();
                    product.project = newProject;
                    product.productarea = tmp.get();
                    product.name = "DUMMY";
                    newProject.productEntities.add(product);
                }else{
                    throw new ResourceNotFound("productArea " + productArea.id + " does not exist" );
                }
            }

            // assign members to projects
            newProject.projectUserEntities = new ArrayList<>();
            assignMembersToProject(projectDto.members, newProject);

            projectRepository.save(newProject);

            // return created projectID
            return new ProjectDto(
                    newProject.id,
                    newProject.name,
                    UUID.fromString(newProject.creatorID),
                    newProject.productEntities,
                    newProject.projectUserEntities);
        }else{
            return null;
        }
    }


    /**
     * lookup of ProjectEntity in DB based on projectID
     *
     * @param projectID unique identifier for ProjectEntity
     * @return ProjectDto
     */
    public ProjectDto getProjectById(int projectID) {

        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);

        if (projectEntity.isEmpty()) {
            return null;
        }else{
            return new ProjectDto(
                    projectEntity.get().id,
                    projectEntity.get().name,
                    UUID.fromString(projectEntity.get().creatorID),
                    projectEntity.get().productEntities,
                    projectEntity.get().projectUserEntities);
        }

    }


    /**
     * updates an existing ProjectEntity in DB
     * attributes/relations that can be updated: projectName, productAreas, members
     * attributes that can not be updated: creatorID, projectID
     * @param projectID unique identifier for ProjectEntity
     * @param projectDto contains data that needs to be updated
     */
    @Transactional
    public ProjectDto updateProject(ProjectDto projectDto, int projectID) {

        if (!projectRepository.existsById(projectID)) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            ProjectEntity entity = projectRepository.findById(projectID).get();
            // update project name
            if(projectDto.projectName != null){entity.name = projectDto.projectName;}

            // add none existing product areas
            if(projectDto.productAreas != null){
                for (ProductAreaDto productArea : projectDto.productAreas){
                    if(productAreaRepository.existsById(productArea.id)){
                        if(!productRepository.existsByProjectAndProductarea(entity,
                                productAreaRepository.getById(productArea.id))){
                            ProductEntity product = new ProductEntity();
                            product.project = entity;
                            product.productarea = productAreaRepository.getById(productArea.id);
                            product.name = "DUMMY";
                            entity.productEntities.add(product);
                        }
                    }else{
                        throw new ResourceNotFound("productArea " + productArea.id + " does not exist");
                    }
                }
            }

            // unassign existing users from project
            projectUserRepository.deleteByProjectUserId_project(entity);
            projectRepository.flush();
            // assign new users to project
            assignMembersToProject(projectDto.members, entity);

            projectRepository.save(entity);
            return new ProjectDto(entity.id, entity.name, UUID.fromString(entity.creatorID),
                   entity.productEntities , entity.projectUserEntities);
        }
    }


    //TODO: (done - needs review) --> user_entity primary key changed
    private void assignMembersToProject(List<UserDto> members, ProjectEntity projectEntity){
        // check if members exist
        Set<UserDto> newUsers = new HashSet<>(members);
        for(UserDto member: newUsers){
            Optional<UserEntity> u = userExists(member);
            if(u.isPresent()){
                ProjectUserEntity newUserProject = new ProjectUserEntity();
                newUserProject.projectUserId = new ProjectUserId(projectEntity, u.get());
                newUserProject.role = member.role;
                projectEntity.projectUserEntities.add(newUserProject);
            }else{
                throw new ResourceNotFound("User does not exist.");
            }
        }
    }


    //TODO: (test)
    private Optional<UserEntity> userExists(UserDto user){


        if(user.userID != null && user.userID.toString().length() == 16){
            return userRepository.findById(user.userID.toString());
        }else if(!user.validateEmail(user.userEmail)){
            throw new BadRequest("Input is missing/incorrect");
        }else{
            return userRepository.findByEmail(user.userEmail);
        }
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
