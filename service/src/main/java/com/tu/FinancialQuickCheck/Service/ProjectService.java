package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProductRepository productRepository;


    public ProjectService(ProjectRepository projectRepository, ProductRepository productRepository) {
        this.projectRepository = projectRepository;
        this.productRepository = productRepository;
    }


    /**
     * lookup of all ProjectEntities in DB
     *
     * @return List<SmallProjectDto> only provides projectID and projectName
     */
    public List<SmallProjectDto> getAllProjects(){

        List<SmallProjectDto> smallProjectDtos = new ArrayList<>() {
        };
        Iterable<ProjectEntity> projectEntities = projectRepository.findAll();
        
        for(ProjectEntity tmp : projectEntities){
            smallProjectDtos.add(new SmallProjectDto(tmp.id, tmp.name));
        }

        return smallProjectDtos;
    }


    /**
     * adds a new ProjectEntity to DB
     * required information: see Project.yaml
     * TODO: creator_id muss noch in members mit aufgenommen werden --> output und tests entsprechend anpassen
     * @return ProjectDto projectDto including created projectID
     */
    public ProjectDto createProject(ProjectDto projectDto) {
        if(projectDto.projectName != null && projectDto.productAreas != null && projectDto.creatorID != null){
            // create db entry
            ProjectEntity newProject = new ProjectEntity();
            newProject.creator_id = projectDto.creatorID.toString();
            newProject.name = projectDto.projectName;
            newProject.productEntities = new ArrayList<>();
            // add product areas to project through DUMMY data in product_entity table
            for (int productArea : projectDto.productAreas){
                ProductEntity product = new ProductEntity();
                product.projectid = newProject;
                product.productareaid = productArea;
                product.name = "DUMMY";
                newProject.productEntities.add(product);
            }
            projectRepository.save(newProject);


            // return created projectID
            return new ProjectDto(newProject.id, newProject.name, UUID.fromString(newProject.creator_id),
                    newProject.productEntities);
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
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            return new ProjectDto(projectEntity.get().id, projectEntity.get().name,
                    UUID.fromString(projectEntity.get().creator_id), projectEntity.get().productEntities,
                    projectEntity.get().projectUserEntities);
        }

    }


    /**
     * updates an existing ProjectEntity in DB
     * attributes that can be updated: projectName, productAreas
     * attributes that can not be updated: members, creator_id, projectID
     * @param projectID unique identifier for ProjectEntity
     * @param projectDto contains data that needs to changed
     */
    public void updateProject(ProjectDto projectDto, int projectID) {

        if (!projectRepository.existsById(projectID)) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{

            // update project name
            projectRepository.findById(projectID).map(
                    project -> {
                        if(projectDto.projectName != null){project.name = projectDto.projectName;}

                        return projectRepository.save(project);
                    });


            // add none existing product areas
            for (int productArea : projectDto.productAreas){

                if(!productRepository.existsByProjectidAndProductareaid(projectRepository.findById(projectID).get(),
                        productArea)){
                    ProductEntity product = new ProductEntity();
                    product.projectid = projectRepository.findById(projectID).get();
                    product.productareaid = productArea;
                    product.name = "DUMMY";
                    productRepository.save(product);
                }
            }
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
