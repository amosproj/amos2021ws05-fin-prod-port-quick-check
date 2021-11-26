package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.db.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.tu.FinancialQuickCheck.dto.ProjectDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static com.tu.FinancialQuickCheck.Role.PROJECT_MANAGER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    static Logger log = Logger.getLogger(ProjectServiceTest.class.getName());

    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProductRepository productRepository;

    private ProjectService service;

    private ProjectDto emptyProject;
    private String projectName;
    private UUID creator_id;
    private HashSet<Integer> productAreas;
    private HashSet<UUID> members;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectServiceTest.class");
        // init ProjectService
        service = new ProjectService(projectRepository, productRepository);
        // init empty test object
        emptyProject = new ProjectDto();
        // init necessary information for test objects
        creator_id = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        projectName = "DKB";
        productAreas = new HashSet<>(Arrays.asList(1,2,3));
        members = new HashSet<>(Arrays.asList(
                UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d"),
                UUID.fromString("0fef539d-69be-4013-9380-6a12c3534c67")));
    }
    

    /**
     * retrieves all project entities
     * TODO: test output against api definition
     * List<SmallProjectDto>
     */
//    @Test
//    @Disabled("Not implemented yet")
//    public void testGetAllProjects() {
//        // testcase 1: data retrieved correctly
//        // testcase 2: data not retrieved correctly
//    }


    /**
     * tests for createProject()
     *
     * testCreateProject1: input contains required information
     *                      --> project is created correctly and projectID returned
     * testCreateProject2: input missing required information
     *                      --> output = null
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

        int projectID;
        ProjectEntity project = new ProjectEntity();
        projectID = project.id;
        project.name = projectName;
        project.creator_id = creator_id.toString();
        project.productEntities = productEntities;
        project.projectUserEntities = projectUserEntities;


        // Step 2: provide knowledge
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project));

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
     * updates one project entity
     * TODO: test output against api definition
     * parameter: ProjectDto projectDto
     * parameter: int projectID
     * return: ProjectDto
     */
//    @Test
//    @Disabled("Not implemented yet")
//    public void testUpdateById() {
//        //testcase 1: attributes are updated correctly (changes only on provided attributes)
//        //testcase 2: attributes that are not supposed to be updated, keep the same value
//        //testcase 3: missing projectID
//    }



}
