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
     * lookup of all ProjectEntities in DB
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
     * adds a new ProjectEntity to DB
     * required information: see Project.yaml
     * creator of project needs to be included in projectDto.members
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
     * lookup of ProjectEntity in DB based on projectID
     *
     * @param projectID unique identifier for ProjectEntity
     * @return ProjectDto
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
     * updates an existing ProjectEntity in DB
     * attributes/relations that can be updated: projectName, productAreas, members
     * attributes that can not be updated: creatorID, projectID
     * @param projectID unique identifier for ProjectEntity
     * @param projectDto contains data that needs to be updated
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
                if(projectDto.productAreas != null){
                    List<ProductEntity> newProductEntities = assignProductAreasToProject(projectDto, entity, true);
                    entity.productEntities.addAll(newProductEntities);
                    productRepository.saveAllAndFlush(newProductEntities);
                }

                // unassign existing users from project
                projectUserRepository.deleteByProjectUserId_project(entity);
                repository.flush();

                // assign new users to project
                entity.projectUserEntities = assignMembersToProject(projectDto.members, entity);

                repository.saveAndFlush(entity);
                return new ProjectDto(entity);
            }
        }
    }


    //TODO: (done - needs review) --> write automated tests
    public List<ProjectUserDto> createProjectUsers(int projectID, List<ProjectUserDto> members) {

        if(!repository.existsById(projectID)){
            return null;
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


    //TODO: (done - needs review) --> user_entity primary key changed
    public List<ProjectUserEntity> assignMembersToProject(List<ProjectUserDto> members, ProjectEntity projectEntity){
        List<ProjectUserEntity> assignedMembers = new ArrayList<>();

        // check if members exist
        Set<ProjectUserDto> newUsers = new LinkedHashSet<>(members);
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


    public List<ProductEntity> assignProductAreasToProject(ProjectDto projectDto, ProjectEntity project, Boolean isPut){

        List<ProductEntity> productEntities = new ArrayList<>();
        Set<ProductAreaDto> newProductAreas = new LinkedHashSet<>(projectDto.productAreas);
        for (ProductAreaDto productArea : newProductAreas){
            Optional<ProductAreaEntity> tmp = productAreaRepository.findById(productArea.id);
            if(tmp.isPresent()){
                if(isPut && productRepository.existsByProjectAndProductarea(project, tmp.get())){
                    continue;
                }

                ProductEntity product = new ProductEntity();
                product.project = project;
                product.productarea = tmp.get();
                product.name = "DUMMY";
                productEntities.add(product);
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
