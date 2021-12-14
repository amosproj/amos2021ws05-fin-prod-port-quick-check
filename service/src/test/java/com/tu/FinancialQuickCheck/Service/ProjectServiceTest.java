package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Role;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProductAreaDto;
import com.tu.FinancialQuickCheck.dto.SmallProjectDto;
import com.tu.FinancialQuickCheck.dto.UserDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.tu.FinancialQuickCheck.dto.ProjectDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
    @Mock
    UserRepository userRepository;
    @Mock
    ProjectUserRepository projectUserRepository;

    private ProjectService service;

    private ProjectDto emptyProject;
    private String projectName;
    private String projectName1;
    private UUID creatorID;
    private String userEmail1;
    private String userEmail2;
    private String userEmail3;
    private String notAnEmail;
    private List<ProductAreaDto> productAreas;
    private List<ProductAreaDto> newProductAreas;
    private List<ProductAreaDto> productAreasDoNotExist;
    private List<UserDto> members;

    private ProjectEntity entity;
    private List<ProductAreaEntity> productAreaEntities;
    private List<UserEntity> userEntities;
    private int projectID;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectServiceTest.class");
        // init ProjectService
        service = new ProjectService(repository, productRepository, productAreaRepository, userRepository,
                projectUserRepository);
        // init empty test object
        emptyProject = new ProjectDto();
        // init necessary information for test objects
        creatorID = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        projectName = "DKB";
        projectName1 = "Sparkasse";
        userEmail1 = "test@gmx.de";
        userEmail2 = "max.mustermann@gmail.com";
        userEmail3 = "maria_mustermann@tu-berlin.de";
        notAnEmail = "test";

        productAreas = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            productAreas.add(new ProductAreaDto(i));
        }

        newProductAreas = new ArrayList<>();
        for(int i = 4; i < 6; i++){
            newProductAreas.add(new ProductAreaDto(i));
        }

        productAreasDoNotExist = new ArrayList<>();
        productAreasDoNotExist.add(new ProductAreaDto(7));

        members = new ArrayList<>();
        members.add(new UserDto(userEmail1, Role.CLIENT));
        members.add(new UserDto(userEmail2, Role.PROJECT_MANAGER));
        members.add(new UserDto(userEmail3, Role.ADMIN));


//        UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d")
//        UUID.fromString("0fef539d-69be-4013-9380-6a12c3534c67")

        // init ProjectEntity
        entity = new ProjectEntity();

        productAreaEntities = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            ProductAreaEntity tmp = new ProductAreaEntity();
            tmp.id = i;
            productAreaEntities.add(tmp);
        }

        List<ProductEntity> productEntities = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            ProductEntity product = new ProductEntity();
            product.name = "DUMMY";
            product.productarea = productAreaEntities.get(i);
            productEntities.add(product);
        }

        userEntities = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.id = "65119d5f-039e-404e-973e-f12c35fe9fef";
        user1.email = userEmail1;
        userEntities.add(user1);

        UserEntity user2 = new UserEntity();
        user2.id = "0fef539d-69be-4013-9380-6a12c3534c67";
        user2.email = userEmail2;
        userEntities.add(user2);

        UserEntity user3 = new UserEntity();
        user3.id = "2375e026-d348-4fb6-b42b-891a76758d5d";
        user3.email = userEmail3;
        userEntities.add(user3);

        List<ProjectUserEntity> projectUserEntities = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            ProjectEntity p = new ProjectEntity();
            ProjectUserEntity tmp = new ProjectUserEntity();
            tmp.projectUserId = new ProjectUserId(p, userEntities.get(i));
            tmp.role = Role.PROJECT_MANAGER;
            projectUserEntities.add(tmp);
        }

        projectID = entity.id;
        entity.name = projectName;
        entity.creatorID = creatorID.toString();
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
    public void testGetAllProjects_returnEmptyList() {
        // Step 1: init test object
        List<ProjectEntity> projectEntities = Collections.EMPTY_LIST;

        // Step 2: provide knowledge
        when(repository.findAll()).thenReturn(projectEntities);

        // Step 3: execute getProjectById()
        List<SmallProjectDto> projectsOut = service.getAllProjects();
        List<SmallProjectDto> expected = new ArrayList<>();

        assertEquals(expected, projectsOut);
    }


    @Test
    public void testGetAllProjects_returnAllExistingProjects() {
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
     * testCreateProject2: input contains resources that do not exist
     *                      --> return ResourceNotFound
     */
    @Test
    public void testCreateProject_allNecessaryInformationProvided_withMultipleMembersAndProductAreas() {
        for(int i = 0; i <= 10; i++){
            // Step 0: init test object
            ProjectDto projectIn = new ProjectDto();
            projectIn.creatorID = creatorID;
            projectIn.projectName = projectName;
            projectIn.productAreas = productAreas;
            int numProductAreasIn = projectIn.productAreas.size();
            projectIn.members = members;
            int numMembersIn = projectIn.members.size();

            //Step 1: provide knowledge
            when(productAreaRepository.findById(1)).thenReturn(Optional.of(productAreaEntities.get(0)));
            when(productAreaRepository.findById(2)).thenReturn(Optional.of(productAreaEntities.get(1)));
            when(productAreaRepository.findById(3)).thenReturn(Optional.of(productAreaEntities.get(2)));
            when(userRepository.findByEmail(userEmail1)).thenReturn(Optional.of(userEntities.get(0)));
            when(userRepository.findByEmail(userEmail2)).thenReturn(Optional.of(userEntities.get(1)));
            when(userRepository.findByEmail(userEmail3)).thenReturn(Optional.of(userEntities.get(2)));

            // Step 2: execute createProject()
            log.info("@Test createProject()- test object : " + projectIn.projectName);
            ProjectDto projectOut = service.createProject(projectIn);
            log.info("@Test createProject()- return object id : " + projectOut.projectID);

            // Step 3: assert result
            assertAll("createProject",
                    () -> assertEquals(projectIn.projectName, projectOut.projectName),
                    () -> assertEquals(projectIn.creatorID, projectOut.creatorID),
                    () -> assertNotNull(projectOut.projectID),
                    () -> assertNotNull(projectOut.productAreas),
                    () -> assertNotNull(projectOut.members),
                    () -> assertNotNull(projectOut)
            );

            assertThat(projectOut.members.size()).isEqualTo(numMembersIn);
            assertThat(projectOut.productAreas.size()).isEqualTo(numProductAreasIn);
        }
    }


    @Test
    public void testCreateProject_allNecessaryInformationProvided_withOneMemberAndProductArea() {
        for(int i = 0; i <= 10; i++){
            // Step 0: init test object
            ProjectDto projectIn = new ProjectDto();
            projectIn.creatorID = creatorID;
            projectIn.projectName = projectName;
            projectIn.productAreas = new ArrayList<>();
            projectIn.productAreas.add(productAreas.get(0));
            int numProductAreasIn = projectIn.productAreas.size();
            projectIn.members = new ArrayList<>();
            projectIn.members.add(members.get(0));
            int numMembersAssigned = projectIn.members.size();

            //Step 1: provide knowledge
            when(productAreaRepository.findById(productAreas.get(0).id)).
                    thenReturn(Optional.of(productAreaEntities.get(0)));
            when(userRepository.findByEmail(members.get(0).userEmail)).
                    thenReturn(Optional.of(userEntities.get(0)));


            // Step 2: execute createProject()
            log.info("@Test createProject()- test object : " + projectIn.projectName);
            ProjectDto projectOut = service.createProject(projectIn);
            log.info("@Test createProject()- return object id : " + projectOut.projectID);

            // Step 3: assert result
            assertAll("createProject",
                    () -> assertEquals(projectIn.projectName, projectOut.projectName),
                    () -> assertEquals(projectIn.creatorID, projectOut.creatorID),
                    () -> assertThat(projectOut.productAreas.get(0).equals(projectIn.productAreas.get(0))),
                    () -> assertThat(projectOut.members.get(0).equals(projectIn.members.get(0))),
                    () -> assertNotNull(projectOut.projectID),
                    () -> assertNotNull(projectOut)
            );

            assertThat(projectOut.members.size()).isEqualTo(numMembersAssigned);
            assertThat(projectOut.productAreas.size()).isEqualTo(numProductAreasIn);
        }
    }


    @Test
    public void testCreateProject_misssingOneRequiredInformation() {
        // missing productAreas
        ProjectDto project1 = new ProjectDto();
        project1.creatorID = creatorID;
        project1.projectName = projectName;
        project1.members = members;

        // missing projectName
        ProjectDto project2 = new ProjectDto();
        project2.creatorID = creatorID;
        project2.productAreas = productAreas;
        project2.members = members;

        // missing creatorID
        ProjectDto project3 = new ProjectDto();
        project3.projectName = projectName;
        project3.productAreas = productAreas;
        project3.members = members;

        // missing members
        ProjectDto project4 = new ProjectDto();
        project4.creatorID = creatorID;
        project4.projectName = projectName;
        project4.productAreas = productAreas;

        // Step 2 and 3: execute createProject and assert result
        assertNull(service.createProject(project1));
        assertNull(service.createProject(project2));
        assertNull(service.createProject(project3));
        assertNull(service.createProject(project4));
        assertNull(service.createProject(emptyProject));

    }


    @Test
    public void testCreateProject_emptyMembersList() {
        // Step 1: init test object: empty members list
        ProjectDto project1 = new ProjectDto();
        project1.creatorID = creatorID;
        project1.projectName = projectName;
        project1.productAreas = productAreas;
        project1.members = new ArrayList<>();

        // Step 2 and 3: execute createProject and assert result
        assertNull(service.createProject(project1));
    }


    @Test
    public void testCreateProject_emptyProductAreaList() {
        // Step 1: init test object: empty productArea list
        ProjectDto project1 = new ProjectDto();
        project1.creatorID = creatorID;
        project1.projectName = projectName;
        project1.productAreas = new ArrayList<>();
        project1.members = members;

        // Step 2 and 3: execute createProject and assert result
        assertNull(service.createProject(project1));
    }


    @Test
    public void testCreateProject_productAreaDoesNotExist() {
        for(int i = 0; i <= 10; i++){
            // Step 0: init test object
            ProjectDto projectIn = new ProjectDto();
            projectIn.creatorID = creatorID;
            projectIn.projectName = projectName;
            projectIn.productAreas = productAreas;
            projectIn.members = members;

            //Step 1: provide knowledge
            when(productAreaRepository.findById(1)).thenReturn(Optional.of(productAreaEntities.get(0)));
            when(productAreaRepository.findById(2)).thenReturn(Optional.of(productAreaEntities.get(1)));
            when(productAreaRepository.findById(3)).thenReturn(Optional.empty());

            // Step 2: execute createProject()
            Exception exception = assertThrows(ResourceNotFound.class, () -> service.createProject(projectIn));
            String expectedMessage = "productArea 3 does not exist";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));


            // Step 3: assert result

        }
    }


    @Test
    public void testCreateProject_memberDoesNotExist() {
        for(int i = 0; i <= 10; i++){
            // Step 0: init test object
            ProjectDto projectIn = new ProjectDto();
            projectIn.creatorID = creatorID;
            projectIn.projectName = projectName;
            projectIn.productAreas = productAreas;
            projectIn.members = members;

            //Step 1: provide knowledge
            when(productAreaRepository.findById(1)).thenReturn(Optional.of(productAreaEntities.get(0)));
            when(productAreaRepository.findById(2)).thenReturn(Optional.of(productAreaEntities.get(1)));
            when(productAreaRepository.findById(3)).thenReturn(Optional.of(productAreaEntities.get(2)));
            when(userRepository.findByEmail(userEmail1)).thenReturn(Optional.of(userEntities.get(0)));
            when(userRepository.findByEmail(userEmail3)).thenReturn(Optional.empty());

            // Step 2: execute createProject()
            Exception exception = assertThrows(ResourceNotFound.class, () -> service.createProject(projectIn));
            String expectedMessage = "User does not exist.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
    }


    /**
     * tests for findById()
     *
     * testFindById1: projectID exists
     *                  --> return projectDto for projectID
     * testFindById2: projectID does not exists
     *                  --> return null
     */
    @Test
    public void testFindById1_projectIdExists() {
        // Step 1: init test object
        // refer to @BeforeEach

        // Step 2: provide knowledge
        when(repository.findById(projectID)).thenReturn(Optional.of(entity));

        // Step 3: execute getProjectById()
        ProjectDto projectOut = service.getProjectById(projectID);

        assertAll("get project",
                () -> assertEquals(projectName, projectOut.projectName),
                () -> assertEquals(creatorID, projectOut.creatorID),
                () -> assertNotNull(projectOut.productAreas),
                () -> assertNotNull(projectOut.members),
                () -> assertEquals(projectID, projectOut.projectID)
        );
    }


    @Test
    public void testFindById2_projectIdNotFound() {

        assertNull(service.getProjectById(1));

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
        //TODO: anpassen
//        project2.productAreas = newProductAreas;

        ProjectDto project3 = new ProjectDto();
        project3.projectName = projectName1;
//      TODO: anpassen
//        project3.productAreas = newProductAreas;

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
            // TODO: anpassen
//            projectIn.productAreas = newProductAreas;
            // cannot be updated
            projectIn.creatorID = UUID.fromString("0fef539d-69be-4013-9380-6a12c3534c67");
            projectIn.projectID = 1000;
            // TODO: anpassen an neue dto
//            projectIn.members = members;

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
        // TODO: anpassen
//        project.productAreas = productAreasDoNotExist;

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



    @Test
    public void testAssignMembersToProject_userEmailDoesNotExist() {
        // Step 1: init test object
        List<UserDto> membersIn = members;
        int numMembersIn = membersIn.size();
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findByEmail(membersIn.get(2).userEmail)).thenReturn(Optional.empty());


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * tests for assignMembersToProject()
     *
     * testAssignMembersToProject: input contains required information
     *                              --> ProjectEntity contains assigned members
     * testAssignMembersToProject: input missing required information
     *                              --> throw Exception BadRequest
     * testAssignMembersToProject: userEmail does not exists
     *                             --> throw Exception ResourceNotFound
     * testAssignMembersToProject: input contains same member twice
     *                             --> ProjectEntity contains member only once
     */

    @Test
    public void testAssignMembersToProject_userEmailMissing() {
        // Step 1: init test object
        List<UserDto> membersIn = new ArrayList<>();
        UserDto emptyUser = new UserDto();
        membersIn.add(emptyUser);
        int numMembersIn = membersIn.size();
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(emptyUser.userEmail)).thenReturn(Optional.empty());


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    @Disabled
    public void testAssignMembersToProject_userEmailAndIdMissing() {
        // Step 1: init test object
        List<UserDto> membersIn = new ArrayList<>();
        UserDto emptyUser = new UserDto();
        membersIn.add(emptyUser);
        int numMembersIn = membersIn.size();
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(emptyUser.userEmail)).thenReturn(Optional.empty());


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testAssignMembersToProject_duplicateMember() {
        // Step 1: init test object
        List<UserDto> membersIn = members;
        membersIn.add(members.get(0));
        int numMembersIn = membersIn.size();
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findByEmail(membersIn.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));
        when(userRepository.findByEmail(membersIn.get(2).userEmail)).thenReturn(Optional.of(userEntities.get(2)));
        when(userRepository.findByEmail(membersIn.get(3).userEmail)).thenReturn(Optional.of(userEntities.get(0)));


        //Step 3: execute assignMembersToProject()
        service.assignMembersToProject(membersIn, projectEntity);

        assertThat(projectEntity.projectUserEntities.size()).isEqualTo(numMembersIn-1);
        assertNotEquals(projectEntity.projectUserEntities.size(), numMembersIn);
    }
}
