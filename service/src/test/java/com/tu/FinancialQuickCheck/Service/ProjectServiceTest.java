package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.tu.FinancialQuickCheck.dto.ProjectDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static com.tu.FinancialQuickCheck.Role.PROJECT_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    static Logger log = Logger.getLogger(ProjectServiceTest.class.getName());

    @Mock
    ProjectRepository repository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductAreaRepository productAreaRepository;

    private ProjectService service;

    private ProjectDto emptyProject;
    private String projectName;
    private String projectName1;
    private UUID creator_id;
    private HashSet<Integer> productAreas;
    private HashSet<Integer> newProductAreas;
    private HashSet<Integer> productAreasDoNotExist;
    private HashSet<UUID> members;

    private ProjectEntity entity;
    private int projectID;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectServiceTest.class");
        // init ProjectService
        service = new ProjectService(repository, productRepository, productAreaRepository);
        // init empty test object
        emptyProject = new ProjectDto();
        // init necessary information for test objects
        creator_id = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        projectName = "DKB";
        projectName1 = "Sparkasse";
        productAreas = new HashSet<>(Arrays.asList(1,2,3));
        newProductAreas = new HashSet<>(Arrays.asList(4,5));
        productAreasDoNotExist = new HashSet<>(Arrays.asList(7));
        members = new HashSet<>(Arrays.asList(
                UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d"),
                UUID.fromString("0fef539d-69be-4013-9380-6a12c3534c67")));

        // init ProjectEntity
        entity = new ProjectEntity();
        List<ProductEntity> productEntities = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            ProductEntity product = new ProductEntity();
            product.name = "DUMMY";
            product.productareaid = i;
            productEntities.add(product);
        }

        List<ProjectUserEntity> projectUserEntities = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            ProjectEntity p = new ProjectEntity();
            UserEntity u = new UserEntity();
            u.id = creator_id.toString();
            ProjectUserEntity tmp = new ProjectUserEntity();
            tmp.projectUserId = new ProjectUserId(p, u);
            tmp.role = PROJECT_MANAGER;
            projectUserEntities.add(tmp);
        }

        projectID = entity.id;
        entity.name = projectName;
        entity.creator_id = creator_id.toString();
        entity.productEntities = productEntities;
        entity.projectUserEntities = projectUserEntities;
    }
    
    
    /**
     * tests for getAllProjects()
     *
     * testGetAllProjects1: no projects exist --> return empty List<SmallProjectDto> 
     * testGetAllProjects2: projects exist --> return List<SmallProjectDto>
     */
    @Test
    public void testGetAllProjects1() {
        // Step 1: init test object         
        Iterable<ProjectEntity> projectEntities = Collections.EMPTY_LIST;
        
        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(projectEntities);

        // Step 3: execute getProjectById()
        List<SmallProjectDto> projectsOut = service.getAllProjects();
        List<SmallProjectDto> expected = new ArrayList<>();

        assertEquals(expected, projectsOut);
    }


    @Test
    public void testGetAllProjects2() {
        // Step 1: init test object
        ProjectEntity project1 = new ProjectEntity();
        project1.name = projectName;
        ProjectEntity project2 = new ProjectEntity();
        project2.name = projectName1;

        List<ProjectEntity> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);

        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(projects);

        // Step 3: execute getProjectById()
        List<SmallProjectDto> projectsOut = service.getAllProjects();

        projectsOut.forEach(
                project -> assertAll("get Projects",
                        () -> assertNotNull(project.projectName),
                        () -> assertNotNull(project.projectID)
                    )
                );

        assertThat(projectsOut.size()).isEqualTo(2);
    }


    /**
     * tests for createProject()
     *
     * testCreateProject1: input contains required information
     *                      --> project is created correctly and projectID returned
     * testCreateProject2: input missing required information
     *                      --> output == null
     * testCreateProject3: input contains more than required information
     *                      --> project is created correctly, projectID returned and additional information is ignored
     */
    @Test
    public void testCreateProject1() {
        for(int i = 0; i <= 10; i++){
            // Step 1: init test object
            ProjectDto projectIn = new ProjectDto();
            projectIn.creatorID = creator_id;
            projectIn.projectName = projectName;
            projectIn.productAreas = productAreas;

            // Step 2: execute createProject()
            log.info("@Test createProject()- test object : " + projectIn.projectName);
            ProjectDto projectOut = service.createProject(projectIn);
            log.info("@Test createProject()- return object id : " + projectOut.projectID);

            // Step 3: assert result
            assertAll("createProject",
                    () -> assertEquals(projectName, projectOut.projectName),
                    () -> assertEquals(creator_id, projectOut.creatorID),
                    () -> assertEquals(productAreas, projectOut.productAreas),
                    () -> assertNotNull(projectOut)
            );
        }
    }


    @Test
    public void testCreateProject2() {
        // Step 1: init test object
        ProjectDto project1 = new ProjectDto();
        project1.creatorID = creator_id;
        project1.projectName = projectName;

        ProjectDto project2 = new ProjectDto();
        project2.creatorID = creator_id;
        project2.productAreas = productAreas;

        ProjectDto project3 = new ProjectDto();
        project3.projectName = projectName;
        project3.productAreas = productAreas;

        // Step 2 and 3: execute createProject and assert result
        assertNull(service.createProject(project1));
        assertNull(service.createProject(project2));
        assertNull(service.createProject(project3));
        assertNull(service.createProject(emptyProject));

    }


    @Test
    public void testCreateProject3() {
        for(int i = 0; i <= 10; i++){
            // Step 1: init test object
            ProjectDto projectIn = new ProjectDto();
            projectIn.creatorID = creator_id;
            projectIn.projectName = projectName;
            projectIn.productAreas = productAreas;
            projectIn.projectID = 1;
            projectIn.members = members;

            // Step 2: execute createProject()
            log.info("@Test createProject()- test object : " + projectIn.projectName);
            ProjectDto projectOut = service.createProject(projectIn);
            log.info("@Test createProject()- return object id : " + projectOut.projectID);

            // Step 3: assert result
            assertAll("createProject",
                    () -> assertEquals(projectName, projectOut.projectName),
                    () -> assertEquals(creator_id, projectOut.creatorID),
                    () -> assertEquals(productAreas, projectOut.productAreas),
                    () -> assertNotEquals(projectIn.projectID, projectOut.projectID),
                    () -> assertNull(projectOut.members),
                    () -> assertNotNull(projectOut)
            );
        }
    }


    /**
     * tests for findById()
     *
     * testFindById1: projectID exists
     *                  --> return projectDto for projectID
     * testFindById2: projectID does not exists
     *                  --> throw Exception ResourceNotFound
     */
    @Test
    public void testFindById1() {
        // Step 1: init test object
        // refer to @BeforeEach

        // Step 2: provide knowledge
        when(repository.findById(projectID)).thenReturn(Optional.of(entity));

        // Step 3: execute getProjectById()
        ProjectDto projectOut = service.getProjectById(projectID);

        assertAll("createProject",
                () -> assertEquals(projectName, projectOut.projectName),
                () -> assertEquals(creator_id, projectOut.creatorID),
                () -> assertEquals(productAreas, projectOut.productAreas),
                () -> assertEquals(new HashSet<>(Collections.singletonList(creator_id)) , projectOut.members),
                () -> assertEquals(projectID, projectOut.projectID)
        );
    }


    @Test
    public void testFindById2() {
        Exception exception = assertThrows(ResourceNotFound.class, () -> service.getProjectById(1));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }



    /**
     * tests for updateProject()
     *
     * testUpdateProject1: input contains required information
     *                      --> ProjectEntity attributes are updated accordingly
     * testUpdateProject2: input missing required information
     *                      --> throw Exception BadRequest
     * testUpdateProject3: input contains more than required information
     *                      --> ProjectEntity attributes are updated accordingly and additional information is ignored
     * testUpdateProject4: projectID does not exists
     *                     --> throw Exception ResourceNotFound
     * testUpdateProject5: productArea does not exist
     *                     --> throw Exception ResourceNotFound
     */

    @Test
    public void testUpdateProject1(){
        // Step 0: init test object
        ProjectDto project1 = new ProjectDto();
        project1.projectName = projectName1;

        ProjectDto project2 = new ProjectDto();
        project2.productAreas = newProductAreas;

        ProjectDto project3 = new ProjectDto();
        project3.projectName = projectName1;
        project3.productAreas = newProductAreas;

        // Step 1: provide knowledge
        when(repository.existsById(entity.id)).thenReturn(true);
        when(repository.findById(entity.id)).thenReturn(Optional.of(entity));
        when(productAreaRepository.existsById(4)).thenReturn(true);
        when(productAreaRepository.existsById(5)).thenReturn(true);

        // Step 2: execute updateProject()
        ProjectDto projectOut1 = service.updateProject(project1, entity.id);
        ProjectDto projectOut2 = service.updateProject(project2, entity.id);
        ProjectDto projectOut3 = service.updateProject(project3, entity.id);

        // Step 3: assert result
        assertAll("updateProject",
                () -> assertEquals(project1.projectName, projectOut1.projectName),
                () -> assertNotNull(projectOut1.productAreas),
                () -> project2.productAreas.forEach(
                        productArea -> assertTrue(projectOut2.productAreas.contains(productArea))),
                () -> assertNotNull(projectOut2.projectName),
                () -> assertEquals(project3.projectName, projectOut3.projectName),
                () -> project3.productAreas.forEach(
                        productArea -> assertTrue(projectOut3.productAreas.contains(productArea)))
        );


    }


    @Test
    public void testUpdateProject2() {
        // Step 0: init test object
        // see @BeforeEach

        // Step 1: provide knowledge
        when(repository.existsById(entity.id)).thenReturn(true);

        // Step 2: Execute updateProject()
        Exception exception = assertThrows(BadRequest.class, () -> service.updateProject(emptyProject, entity.id));

        // Step 3: assert exception
        String expectedMessage = "Nothing to update.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testUpdateProject3() {
        for(int i = 0; i <= 10; i++){
            // Step 1: init test object
            ProjectDto projectIn = new ProjectDto();
            // can be updated
            projectIn.projectName = projectName1;
            projectIn.productAreas = newProductAreas;
            // cannot be updated
            projectIn.creatorID = UUID.fromString("0fef539d-69be-4013-9380-6a12c3534c67");
            projectIn.projectID = 1000;
            projectIn.members = members;

            // Step 2: provide knowledge
            when(repository.existsById(entity.id)).thenReturn(true);
            when(repository.findById(entity.id)).thenReturn(Optional.of(entity));
            when(productAreaRepository.existsById(4)).thenReturn(true);
            when(productAreaRepository.existsById(5)).thenReturn(true);


            // Step 3: execute createProject()
            ProjectDto projectOut = service.updateProject(projectIn, entity.id);


            // Step 4: assert result
            assertAll("updateProject",
                    () -> assertEquals(projectIn.projectName, projectOut.projectName),
                    () -> projectIn.productAreas.forEach(
                            productArea -> assertTrue(projectOut.productAreas.contains(productArea))),
                    () -> assertNotEquals(projectIn.creatorID, projectOut.creatorID),
                    () -> assertNotEquals(projectIn.projectID, projectOut.projectID),
                    () -> assertNotEquals(projectIn.members, projectOut.members)
            );
        }
    }


    @Test
    public void testUpdateProject4() {
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.updateProject(new ProjectDto(),1));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testUpdateProject5() {
        // Step 1: init test object
        ProjectDto project = new ProjectDto();
        project.projectName = projectName;
        project.productAreas = productAreasDoNotExist;

        //Step 2: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(productAreaRepository.existsById(7)).thenReturn(false);

        //Step 3: execute updateProject()
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.updateProject(project,1));

        String expectedMessage = "productArea 7 does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}
