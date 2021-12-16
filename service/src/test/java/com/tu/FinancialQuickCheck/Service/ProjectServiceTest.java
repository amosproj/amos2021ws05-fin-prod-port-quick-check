package com.tu.FinancialQuickCheck.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Role;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private ProductAreaDto productAreaDoesNotExist;
    private List<ProjectUserDto> members;

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

        productAreaDoesNotExist = new ProductAreaDto(7);


        members = new ArrayList<>();
        members.add(new ProjectUserDto(userEmail1, Role.CLIENT));
        members.add(new ProjectUserDto(userEmail2, Role.PROJECT_MANAGER));
        members.add(new ProjectUserDto(userEmail3, Role.ADMIN));


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
        user1.id = creatorID.toString();
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
                        () -> assertEquals(0, project.projectID)
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
                    () -> assertEquals(0, projectOut.projectID),
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
                    () -> assertNotNull(projectOut.productAreas),
                    () -> assertNotNull(projectOut.members),
                    () -> assertEquals(0, projectOut.projectID),
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
    @Disabled
    public void testCreateProject_memberDoesNotExist() {
        //TODO: (fix) stubbing problem
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
//            when(userRepository.findByEmail(projectIn.members.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
//            when(userRepository.findByEmail(projectIn.members.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));
            when(userRepository.findByEmail(projectIn.members.get(2).userEmail)).thenReturn(Optional.empty());

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
    public void testFindById_projectIdExists() {
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
    public void testFindById_projectIdNotFound() {

        assertNull(service.getProjectById(1));

    }



    /**
     * tests for updateProject()
     * //TODO: (add testcase)
     * testUpdateProject: updates all attributes
     *                    --> ProjectEntity attributes are updated accordingly
     * //TODO: (add testcase)
     * testUpdateProject: partial update: only add new ProductAreas
     *                    --> ProjectEntity attributes are updated accordingly
     * testUpdateProject: partial update: replace existing project members with new ones
     *                    --> ProjectEntity attributes are updated accordingly
     * testUpdateProject: input missing required information: members
     *                      --> throw Exception BadRequest
     * testUpdateProject: projectID, member or productArea does not exists
     *                     --> throw Exception ResourceNotFound
     */

    @Test
    @Disabled("implement")
    public void testUpdateProject_success_updateAllAttributes(){


    }

    @Test
    @Disabled("implement")
    public void testUpdateProject_partialUpdate_onlyAddNewProductAreas(){


    }


    @Test
    @Disabled
    public void testUpdateProject_partialUpdate_replaceAllExistingProjectMembers(){
        //TODO: (discuss with Alex) Is this test necessary? Because it tests if delete works correctly, which is a SpringBoot method
        //TODO: (discuss with Alex) If we need the testcase, how to mock delete method?
        // Step 0: init test object
        ProjectDto projectIn = emptyProject;
        projectIn.productAreas = productAreas;
        emptyProject.members = new ArrayList<>();
        emptyProject.members.add(new ProjectUserDto(userEmail2, Role.CLIENT));
        emptyProject.members.add(new ProjectUserDto(userEmail3, Role.CLIENT));
        UserEntity userEntity2 = new UserEntity();
        userEntity2.id = creatorID.toString();
        userEntity2.email = userEmail2;
        UserEntity userEntity3 = new UserEntity();
        userEntity3.id = creatorID.toString();
        userEntity3.email = userEmail3;

        // Step 0: provide knowledge
        when(repository.existsById(entity.id)).thenReturn(true);
        when(repository.findById(entity.id)).thenReturn(Optional.of(entity));
        when(productAreaRepository.findById(productAreas.get(0).id)).thenReturn(Optional.of(productAreaEntities.get(0)));
        when(productAreaRepository.findById(productAreas.get(1).id)).thenReturn(Optional.of(productAreaEntities.get(1)));
        when(productAreaRepository.findById(productAreas.get(2).id)).thenReturn(Optional.of(productAreaEntities.get(2)));
        when(userRepository.findByEmail(userEmail2)).thenReturn(Optional.of(userEntity2));
        when(userRepository.findByEmail(userEmail3)).thenReturn(Optional.of(userEntity3));
        when(projectUserRepository.deleteByProjectUserId_project(entity)).thenReturn((long) entity.projectUserEntities.size());

        // Step 2: execute updateProject()
        ProjectDto out = service.updateProject(projectIn, entity.id);

        // Step 3: assert result
        assertEquals(projectIn.members.size(), out.members.size());
        assertNotEquals(3, out.members.size());

    }


    @Test
    public void testUpdateProject_missingMembers() {

        assertNull(service.updateProject(emptyProject, entity.id));

    }


    @Test
    public void testUpdateProject_emptyMembers() {
        emptyProject.members = new ArrayList<>();

        assertNull(service.updateProject(emptyProject, entity.id));

    }


    @Test
    public void testUpdateProject_resourceNotFound_projectId() {
        //Step 0: init test object
        emptyProject.members = members;

        //Step 1: run test
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.updateProject(emptyProject,1));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testUpdateProject_resourceNotFound_productAreas_singleProductArea() {
        //Step 0: init test object
        ProjectDto projectIn = new ProjectDto();
        projectIn.members = members;
        projectIn.productAreas = new ArrayList<>();
        projectIn.productAreas.add(productAreaDoesNotExist);

        //Step 1: provide knowledge
        when(repository.existsById(projectID)).thenReturn(true);
        when(repository.findById(projectID)).thenReturn(Optional.of(new ProjectEntity()));
        when(productAreaRepository.findById(productAreaDoesNotExist.id)).thenReturn(Optional.empty());

        //Step 2: execute updateProject()
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> service.updateProject(projectIn, projectID));

        String expectedMessage = "productArea " + productAreaDoesNotExist.id + " does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }


    @Test
    public void testUpdateProject_resourceNotFound_productAreas_multilpeProductAreas() {
        //Step 0: init test object
        ProjectDto projectIn = new ProjectDto();
        projectIn.members = members;
        projectIn.productAreas = productAreas;
        projectIn.productAreas.add(productAreaDoesNotExist);

        //Step 1: provide knowledge
        when(repository.existsById(projectID)).thenReturn(true);
        when(repository.findById(projectID)).thenReturn(Optional.of(entity));
        when(productAreaRepository.findById(productAreas.get(0).id)).thenReturn(Optional.of(productAreaEntities.get(0)));
        when(productAreaRepository.findById(productAreas.get(1).id)).thenReturn(Optional.of(productAreaEntities.get(1)));
        when(productAreaRepository.findById(productAreas.get(2).id)).thenReturn(Optional.of(productAreaEntities.get(2)));
        when(productAreaRepository.findById(productAreaDoesNotExist.id)).thenReturn(Optional.empty());

        //Step 2: execute updateProject()
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> service.updateProject(projectIn, projectID));

        String expectedMessage = "productArea " + productAreaDoesNotExist.id + " does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testUpdateProject_resourceNotFound_members_singleUsers() {
        //Step 0: init test object
        ProjectDto projectIn = new ProjectDto();
        projectIn.productAreas = productAreas;
        projectIn.members = new ArrayList<>();
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userID = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        userDoesNotExist.role = Role.CLIENT;
        projectIn.members.add(userDoesNotExist);

        //Step 1: provide knowledge
        when(repository.existsById(projectID)).thenReturn(true);
        when(repository.findById(projectID)).thenReturn(Optional.of(entity));
        when(productAreaRepository.findById(productAreas.get(0).id)).thenReturn(Optional.of(productAreaEntities.get(0)));
        when(productAreaRepository.findById(productAreas.get(1).id)).thenReturn(Optional.of(productAreaEntities.get(1)));
        when(productAreaRepository.findById(productAreas.get(2).id)).thenReturn(Optional.of(productAreaEntities.get(2)));
        when(userRepository.findById(userDoesNotExist.userID.toString())).thenReturn(Optional.empty());

        //Step 2: execute updateProject()
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> service.updateProject(projectIn, projectID));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }


    @Test
    public void testUpdateProject_resourceNotFound_members_multipleUsers() {
        //Step 0: init test object
        ProjectDto projectIn = new ProjectDto();
        projectIn.productAreas = productAreas;
        projectIn.members = members;
        projectIn.members.get(0).userID = creatorID;
        projectIn.members.get(1).userID = creatorID;
        projectIn.members.get(2).userID = creatorID;
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userID = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        userDoesNotExist.role = Role.CLIENT;
        projectIn.members.add(userDoesNotExist);

        //Step 1: provide knowledge
        when(repository.existsById(projectID)).thenReturn(true);
        when(repository.findById(projectID)).thenReturn(Optional.of(entity));
        when(productAreaRepository.findById(productAreas.get(0).id)).thenReturn(Optional.of(productAreaEntities.get(0)));
        when(productAreaRepository.findById(productAreas.get(1).id)).thenReturn(Optional.of(productAreaEntities.get(1)));
        when(productAreaRepository.findById(productAreas.get(2).id)).thenReturn(Optional.of(productAreaEntities.get(2)));
        when(userRepository.findById(projectIn.members.get(0).userID.toString())).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findById(projectIn.members.get(1).userID.toString())).thenReturn(Optional.of(userEntities.get(1)));
        when(userRepository.findById(projectIn.members.get(2).userID.toString())).thenReturn(Optional.of(userEntities.get(2)));
        when(userRepository.findById(userDoesNotExist.userID.toString())).thenReturn(Optional.empty());

        //Step 2: execute updateProject()
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> service.updateProject(projectIn, projectID));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }




    /**
     * tests for assignMembersToProject()
     *
     * testAssignMembersToProject: input contains required information
     *                              --> ProjectEntity contains assigned members
     * testAssignMembersToProject: invalid Email
     *                              --> throw Exception BadRequest
     * testAssignMembersToProject: userEmail/userID does not exist
     *                             --> throw Exception ResourceNotFound
     * testAssignMembersToProject: input contains same member twice
     *                             --> ProjectEntity contains member only once
     */

    @Test
    public void testAssignMembersToProject_successWithUserId_singleUser() {
        // Step 1: init test object
        ProjectUserDto newMember = new ProjectUserDto();
        newMember.userID = creatorID;
        newMember.role = Role.PROJECT_MANAGER;
        List<ProjectUserDto> membersIn = new ArrayList<>();
        membersIn.add(newMember);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findById(newMember.userID.toString())).thenReturn(Optional.of(userEntities.get(0)));

        //Step 3: execute assignMembersToProject()
        List<ProjectUserEntity> out = service.assignMembersToProject(membersIn, projectEntity);


        assertThat(out.size()).isEqualTo(membersIn.size());

        assertAll("assign single user to project",
                () -> assertEquals(newMember.userID.toString(), out.get(0).projectUserId.getUser().id),
                () -> assertEquals(newMember.role, out.get(0).role));
    }


    @Test
    public void testAssignMembersToProject_successWithUserId_multipleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = members;
        membersIn.get(0).userID = creatorID;
        membersIn.get(1).userID = creatorID;
        membersIn.get(2).userID = creatorID;

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findById(membersIn.get(0).userID.toString())).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findById(membersIn.get(1).userID.toString())).thenReturn(Optional.of(userEntities.get(1)));

        //Step 3: execute assignMembersToProject()
        List<ProjectUserEntity> out = service.assignMembersToProject(membersIn, projectEntity);

        assertThat(out.size()).isEqualTo(membersIn.size());

        out.forEach( projectUser -> assertAll("assign multiple user to project",
                () -> assertNotNull(projectUser.projectUserId.getUser().email),
                () -> assertNotNull(projectUser.role)));
    }


    @Test
    public void testAssignMembersToProject_successWithEmail_singleUser() {
        // Step 1: init test object
        ProjectUserDto newMember = new ProjectUserDto();
        newMember.userEmail = userEmail1;
        newMember.role = Role.CLIENT;
        List<ProjectUserDto> membersIn = new ArrayList<>();
        membersIn.add(newMember);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(newMember.userEmail)).thenReturn(Optional.of(userEntities.get(0)));

        //Step 3: execute assignMembersToProject()
        List<ProjectUserEntity> out = service.assignMembersToProject(membersIn, projectEntity);


        assertThat(out.size()).isEqualTo(membersIn.size());

        assertAll("assign single user to project",
                () -> assertEquals(newMember.userEmail, out.get(0).projectUserId.getUser().email),
                () -> assertEquals(newMember.role, out.get(0).role));
    }


    @Test
    public void testAssignMembersToProject_successWithEmail_multipleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = members;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findByEmail(membersIn.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));
        when(userRepository.findByEmail(membersIn.get(2).userEmail)).thenReturn(Optional.of(userEntities.get(2)));

        //Step 3: execute assignMembersToProject()
        List<ProjectUserEntity> out = service.assignMembersToProject(membersIn, projectEntity);

        assertThat(out.size()).isEqualTo(membersIn.size());

        out.forEach( projectUser -> assertAll("assign multiple user to project",
                () -> assertNotNull(projectUser.projectUserId.getUser().email),
                () -> assertNotNull(projectUser.role)));
    }


    @Test
    public void testAssignMembersToProject_invalidEmail_singleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        ProjectUserDto invalidEmail = new ProjectUserDto();
        invalidEmail.userEmail = notAnEmail;
        membersIn.add(invalidEmail);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(BadRequest.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    @Disabled
    public void testAssignMembersToProject_invalidEmail_multipleUsers() {
        //TODO: (figure out) why does the test not work when all test in this class are running but it does work when it is run by itself?
        // Step 1: init test object
        List<ProjectUserDto> membersIn = members;
        ProjectUserDto invalidEmail = new ProjectUserDto();
        invalidEmail.userEmail = notAnEmail;
        invalidEmail.role = Role.CLIENT;
        membersIn.add(invalidEmail);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findByEmail(membersIn.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));
        when(userRepository.findByEmail(membersIn.get(2).userEmail)).thenReturn(Optional.of(userEntities.get(2)));



        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(BadRequest.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testAssignMembersToProject_badRequest_userEmailAndIdMissing_singleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        ProjectUserDto emptyUser = new ProjectUserDto();
        membersIn.add(emptyUser);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(BadRequest.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testAssignMembersToProject_badRequest_missingUserRole_singleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userID = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        membersIn.add(userDoesNotExist);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(BadRequest.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testAssignMembersToProject_resourceNotFound_userIdDoesNotExist_singleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userID = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        userDoesNotExist.role = Role.CLIENT;
        membersIn.add(userDoesNotExist);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testAssignMembersToProject_resourceNotFound_userIdDoesNotExist_mulitpleUsers() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = members;
        for(ProjectUserDto userIn: membersIn){
            userIn.userID = UUID.fromString("2375e026-d348-4fb6-b42b-891a76758d5d");
        }
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.projectUserEntities = new ArrayList<>();

        //Step 2: provide knowledge
        when(userRepository.findById(membersIn.get(0).userID.toString())).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findById(membersIn.get(2).userID.toString())).thenReturn(Optional.empty());


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testAssignMembersToProject_userEmailDoesNotExist_singleUser() {
        // Step 1: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userEmail = "test@test.com";
        userDoesNotExist.role = Role.CLIENT;
        membersIn.add(userDoesNotExist);
        ProjectEntity projectEntity = new ProjectEntity();


        //Step 3: execute assignMembersToProject()
        Exception exception = assertThrows(ResourceNotFound.class,
                () -> service.assignMembersToProject(membersIn, projectEntity));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    @Disabled
    public void testAssignMembersToProject_userEmailDoesNotExist_multipleUsers() {
        //TODO: (figure out) why does the test not work when all test in this class are running but it does work when it is run by itself?
        // Step 1: init test object
        List<ProjectUserDto> membersIn = members;
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userEmail = "test@test.com";
        userDoesNotExist.role = Role.CLIENT;
        membersIn.add(userDoesNotExist);
        ProjectEntity projectEntity = new ProjectEntity();

        //Step 2: provide knowledge
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findByEmail(membersIn.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));
        when(userRepository.findByEmail(membersIn.get(2).userEmail)).thenReturn(Optional.of(userEntities.get(2)));
        when(userRepository.findByEmail(userDoesNotExist.userEmail)).thenReturn(Optional.empty());


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
        List<ProjectUserDto> membersIn = members;
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
        List<ProjectUserEntity> out = service.assignMembersToProject(membersIn, projectEntity);

        assertThat(out.size()).isEqualTo(numMembersIn-1);
        assertNotEquals(out.size(), numMembersIn);
    }


    /**
     * tests for createProjectUsers()
     *
     * testCreateProjectUser: projectID does not exist --> throw ResourceNotFound Exception
     * testCreateProjectUser2: IDs exist, Role is missing --> throw BadRequest Exception
     * testCreateProjectUser4: Input correct --> tbd
     */
    @Test
    public void testCreateProjectUser_resourceNotFound_projectID() {
        //Step 1: run test
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.createProjectUsers(1, new ArrayList<>()));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testCreateProjectUser_resourceNotFound_userEmail_singleUser() {
        // Step 0: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        ProjectUserDto userDoesNotExist = new ProjectUserDto();
        userDoesNotExist.userEmail = "test@test.com";
        userDoesNotExist.role = Role.CLIENT;
        membersIn.add(userDoesNotExist);

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        // Step 2: Execute test method()
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.createProjectUsers(1, membersIn));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        // Step 3: assert result

    }


    @Test
    public void testCreateProjectUser_resourceNotFound_userEmail_multipleUser() {
        // Step 0: init test object
        List<ProjectUserDto> membersIn = members;
        membersIn.get(2).userEmail = "test@test.com";

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        // Step 2: Execute test method()
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.createProjectUsers(1, membersIn));

        String expectedMessage = "User does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        // Step 3: assert result

    }


    @Test
    public void testCreateProjectUser_badRequest_missingProjectUserRole_singleUser() {
        // Step 0: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        members.get(0).role = null;
        membersIn.add(members.get(0));

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        // Step 2: Execute test method()
        Exception exception = assertThrows(BadRequest.class, ()
                -> service.createProjectUsers(1, membersIn));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        // Step 3: assert result

    }


    @Test
    @Disabled
    public void testCreateProjectUser_badRequest_missingProjectUserRole_multipleUser() {
        //TODO: (figure out) why does the test not work when all test in this class are running but it does work when it is run by itself?
        // Step 0: init test object
        List<ProjectUserDto> membersIn = members;
        membersIn.get(2).role = null;

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
        when(userRepository.findByEmail(membersIn.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));

        // Step 2: Execute test method()
        Exception exception = assertThrows(BadRequest.class, ()
                -> service.createProjectUsers(1, membersIn));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testCreateProjectUser_badRequest_missingUserEmail_singleUser() {
        // Step 0: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        members.get(0).userEmail = null;
        membersIn.add(members.get(0));

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        // Step 2: Execute test method()
        Exception exception = assertThrows(BadRequest.class, ()
                -> service.createProjectUsers(1, membersIn));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }


    @Test
    @Disabled
    public void testCreateProjectUser_badRequest_missingUserEmail_multipleUser() {
        //TODO: (fix) stubbing problem
        // Step 0: init test object
        List<ProjectUserDto> membersIn = members;
        membersIn.get(2).userEmail = null;

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));
//        when(userRepository.findByEmail(membersIn.get(1).userEmail)).thenReturn(Optional.of(userEntities.get(1)));

        // Step 2: Execute test method()
        Exception exception = assertThrows(BadRequest.class, ()
                -> service.createProjectUsers(1, membersIn));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testCreateProjectUser_success_singleUser() {
        // Step 0: init test object
        List<ProjectUserDto> membersIn = new ArrayList<>();
        membersIn.add(members.get(0));

        // Step 1: provide knowledge
        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(userRepository.findByEmail(membersIn.get(0).userEmail)).thenReturn(Optional.of(userEntities.get(0)));

        // Step 2: run test method
        List<ProjectUserDto> out = service.createProjectUsers(1, membersIn);

        assertThat(out.size()).isEqualTo(membersIn.size());
        assertAll("assign single User to project",
                () -> assertEquals(membersIn.get(0).userEmail, out.get(0).userEmail),
                () -> assertEquals(membersIn.get(0).role, out.get(0).role),
                () -> assertNotNull(out.get(0).userID));
    }

    @Test
    @Disabled("implement")
    public void testCreateProjectUser4() {
        // Step 0: init test object

        // Step 1: provide knowledge

        // Step 2: Execute test method()

        // Step 3: assert result

    }

}
