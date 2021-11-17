package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.ProductRepository;
import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectRepository;
import com.tu.FinancialQuickCheck.dto.ProjectDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {


    private ProjectRepository projectRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProductRepository productRepository) {

        this.projectRepository = projectRepository;
        this.productRepository = productRepository;
    }

    // only return projectId, projectName
    public List<SmallProjectDto> getAllProjects(){

        List<SmallProjectDto> smallProjectDtos = new ArrayList<>() {
        };
        Iterable<ProjectEntity> projectEntities = projectRepository.findAll();
        
        for(ProjectEntity tmp : projectEntities){
            smallProjectDtos.add(new SmallProjectDto(tmp.id, tmp.name));
        }

        return smallProjectDtos;
    }


    public ProjectDto createProject(ProjectDto projectDto) {
        // create db entry
        ProjectEntity newProject = new ProjectEntity();
        newProject.creator_id = projectDto.creatorID;
        newProject.name = projectDto.projectName;
        projectRepository.save(newProject);

        // add product areas to project through DUMMY data in product_entity table
        for (int productArea : projectDto.productAreas){

            ProductEntity product = new ProductEntity();
            product.projectid = newProject.id;
            product.productareaid = productArea;
            product.name = "DUMMY";
            productRepository.save(product);
        }

        // return created projectID
        projectDto.projectID = newProject.id;
        return projectDto;
    }


    // TODO: hier fehlen noch die members
    public ProjectDto findById(int projectID) {

        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);

        if (projectEntity.isEmpty()) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            Integer[] members = {99};
            return new ProjectDto(projectEntity.get().id, projectEntity.get().name,
                    projectEntity.get().creator_id, members, projectEntity.get().productEntities);
        }

    }


    // TODO: hier fehlt noch das update von den members
    public void updateById(ProjectDto projectDto, Integer projectID) {

        if (!projectRepository.existsById(projectID)) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{

            // update project name
            projectRepository.findById(projectID).map(
                    project -> {
                        project.name = projectDto.projectName;
                        project.creator_id = projectDto.creatorID;
                        return projectRepository.save(project);
                    });

            // add none existing product areas
            for (int productArea : projectDto.productAreas){

                if(!productRepository.existsByProjectidAndProductareaid(projectID, productArea)){
                    ProductEntity product = new ProductEntity();
                    product.projectid = projectID;
                    product.productareaid = productArea;
                    product.name = "DUMMY";
                    productRepository.save(product);

                }
            }
        }
    }


    public void deleteProject(int projectID) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectID);
        if (projectEntity.isEmpty()) {
            throw new ResourceNotFound("projectID " + projectID + " not found");
        }else{
            projectRepository.deleteById(projectID);
        }
    }

}
