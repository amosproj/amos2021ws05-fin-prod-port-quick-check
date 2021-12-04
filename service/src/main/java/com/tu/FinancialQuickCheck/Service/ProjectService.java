package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAreaRepository productAreaRepository;


    public ProjectService(ProjectRepository projectRepository, ProductRepository productRepository,
                          ProductAreaRepository productAreaRepository) {
//        this.projectRepository = projectRepository;
//        this.productRepository = productRepository;
//        this.productAreaRepository = productAreaRepository;
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
            for (ProductAreaDto productArea : projectDto.productAreas){
                System.out.println("Create DUMMY data for productArea: " + productArea.id);
                ProductEntity product = new ProductEntity();
                product.id = new ProductId(productAreaRepository.findById(productArea.id).get(), newProject);
//                product.project = newProject;
//                product.productarea = productAreaRepository.findById(productArea.id).get();
                product.name = "DUMMY";
                newProject.productEntities.add(product);
            }
            projectRepository.save(newProject);


            // return created projectID
            return new ProjectDto(newProject.id, newProject.name, UUID.fromString(newProject.creator_id),
                    convertProductAreaEntities(newProject.productEntities));
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
            List<ProductAreaDto> tmp = new ArrayList<>();
            tmp = convertProductAreaEntities(projectEntity.get().productEntities);

            return new ProjectDto(projectEntity.get().id, projectEntity.get().name,
                    UUID.fromString(projectEntity.get().creator_id), tmp,
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
    public ProjectDto updateProject(ProjectDto projectDto, int projectID) {

        if (!projectRepository.existsById(projectID)) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        } else if(projectDto.projectName == null && projectDto.productAreas == null) {
            throw new BadRequest("Nothing to update.");
        }else{

            ProjectEntity entity = projectRepository.findById(projectID).get();

            // update project name
            if(projectDto.projectName != null){entity.name = projectDto.projectName;}

            // add none existing product areas
            if(projectDto.productAreas != null){
                for (ProductAreaDto productArea : projectDto.productAreas){
                    if(productAreaRepository.existsById(productArea.id)){
//                        if(!productRepository.existsByProjectidAndProductareaid(entity, productArea.id)){
//                            ProductEntity product = new ProductEntity();
//                            product.project = entity;
//                            // TODO: anpassen
////                            product.productareaid = productArea;
//                            product.name = "DUMMY";
//                            entity.productEntities.add(product);
//                        }
                    }else{
                        throw new ResourceNotFound("productArea " + productArea + " does not exist");
                    }
                }
            }

            projectRepository.save(entity);
            //TODO: anpassen
//            return new ProjectDto(entity.id, entity.name, UUID.fromString(entity.creator_id),
//                    entity.productEntities, entity.projectUserEntities);
            return new ProjectDto();
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

    private List<ProductAreaDto> convertProductAreaEntities(List<ProductEntity> productEntities) {
        //TODO: lässt doppelte Werte zu, d.h. Datentyp anpassen
        List<ProductAreaDto> areas = new ArrayList<>();

        for (ProductEntity product: productEntities)
        {
            System.out.println("Create ProductAreaDto for : " + product.id.productarea.id);

//            int id = product.id.productarea.id;
//            ProductAreaEntity tmp = productAreaRepository.findById(id).get();
            areas.add(new ProductAreaDto(
                    product.id.getProductarea().id,
                    product.id.getProductarea().name,
                    product.id.getProductarea().category
            ));
        }
        return areas;
    }

}
