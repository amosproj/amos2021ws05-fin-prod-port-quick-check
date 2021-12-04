package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
            Set<ProductAreaDto> newProductAreas = new HashSet<>(projectDto.productAreas);
            for (ProductAreaDto productArea : newProductAreas){
                Optional<ProductAreaEntity> tmp = productAreaRepository.findById(productArea.id);
                if(tmp.isPresent()){
                    System.out.println("Create DUMMY data for productArea: " + productArea.id);
                    ProductEntity product = new ProductEntity();
                    product.project = newProject;
                    product.productarea = tmp.get();
                    product.name = "DUMMY";
                    newProject.productEntities.add(product);
                }else{
                    throw new ResourceNotFound("productArea " + productArea.id + " does not exist" );
                }
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
            return new ProjectDto(
                    projectEntity.get().id,
                    projectEntity.get().name,
                    UUID.fromString(projectEntity.get().creator_id),
                    convertProductAreaEntities(projectEntity.get().productEntities),
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
                        if(!productRepository.existsByProjectAndProductarea(entity,
                                productAreaRepository.getById(productArea.id))){
                            ProductEntity product = new ProductEntity();
                            product.project = entity;
                            product.productarea = productAreaRepository.getById(productArea.id);
                            product.name = "DUMMY";
                            entity.productEntities.add(product);
                        }
                    }else{
                        throw new ResourceNotFound("productArea " + productArea + " does not exist");
                    }
                }
            }

            projectRepository.save(entity);
            return new ProjectDto(entity.id, entity.name, UUID.fromString(entity.creator_id),
                   convertProductAreaEntities(entity.productEntities) , entity.projectUserEntities);
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
        //TODO: greift alle Produktdaten für project ab, es würde ausreichen nur die DUMMY Daten abzugreifen
        HashSet<ProductAreaDto> areas = new HashSet<>();

        for (ProductEntity product: productEntities)
        {
            areas.add(new ProductAreaDto(
                    product.productarea.id,
                    product.productarea.name,
                    product.productarea.category
            ));
        }
        return new ArrayList<>(areas);
    }
}
