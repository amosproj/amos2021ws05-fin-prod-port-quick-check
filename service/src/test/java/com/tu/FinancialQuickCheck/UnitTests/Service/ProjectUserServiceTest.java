package com.tu.FinancialQuickCheck.UnitTests.Service;

import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Role;
import com.tu.FinancialQuickCheck.Service.ProjectUserService;
import com.tu.FinancialQuickCheck.db.*;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class ProjectUserServiceTest {

    static Logger log = Logger.getLogger(ProjectUserServiceTest.class.getName());

    @Mock
    ProjectUserRepository repository;
    @Mock
    UserRepository userRepository;
    @Mock
    ProjectRepository projectRepository;

    private ProjectUserService service;

    private ProjectUserDto dto1;
    private ProjectUserDto dto2;
    private List<ProjectUserDto> listDtos;
    private ProjectUserEntity projectUserEntity;

    private ProjectEntity project1;
    private UserEntity user1;
    private ProjectUserId projectUserId;

    int projectID1 = 77777;
    int projectID2 = 12999292;


    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectUserServiceTest.class");
        // init ProjectService
        service = new ProjectUserService(userRepository, projectRepository, repository);

        dto1 = new ProjectUserDto();
        dto1.userID = UUID.randomUUID();
        dto2 = new ProjectUserDto();
        dto2.userID = UUID.randomUUID();

        listDtos = new ArrayList<>();

        project1 = new ProjectEntity();
        project1.id = projectID2;

        List<ProjectUserEntity> projectUserEntities = new ArrayList<>();
        project1.projectUserEntities = projectUserEntities;

        user1 = new UserEntity();
        user1.id = dto1.userID.toString();

        projectUserId = new ProjectUserId();
        projectUserId.setProject(project1);
        projectUserId.setUser(user1);

        projectUserEntity = new ProjectUserEntity();
        projectUserEntity.projectUserId = projectUserId;
    }

    @Test
    public void testGetProjectUsersByProjectId_projectIdDoesNotExist() {
        // init test object
        int projectID = 1;

        // provide knowledge
        when(projectRepository.findById(projectID)).thenReturn(Optional.empty());

        // execute test methode and assert result
        assertNull(service.getProjectUsersByProjectId(projectID));
    }

    @Test
    public void testGetProjectUsersByProjectId_projectIdExists() {
        // init test object
        int projectID = 1;

        // provide knowledge
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));

        // execute test methode and assert result
        List<ProjectUserDto> out = service.getProjectUsersByProjectId(projectID);
        assertEquals(project1.projectUserEntities.size(), out.size());
    }

    /**
     * tests for updateProjectUsers() --> list of ProjectUserDtos
     *
     * testUpdateProjectUsers1: userID does not exist --> return null
     * testUpdateProjectUsers2: projectID does not exist --> return null
     * testUpdateProjectUsers3: IDs exist, Role is missing --> return NOT updated List<ProjectUserEntity>
     * testUpdateProjectUsers4: IDs exist, Role exists --> return updated List<ProjectUserEntity>
     */
    @Test
    public void testUpdateListProjectUsers_projectIdDoesNotExist() {
        // init test object
        int projectID = 1;

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.FALSE);

        // execute test methode and assert result
        assertNull(service.updateProjectUsers(projectID, listDtos));
    }

    @Test
    public void testUpdateListProjectUsers_oneUser_userIdDoesNotExist() {
        // init test object
        int projectID = 1;
        String id = dto1.userID.toString();
        listDtos.add(dto1);

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(id)).thenReturn(Boolean.FALSE);

        // execute test methode and assert result
        assertNull(service.updateProjectUsers(projectID, listDtos));
    }

    @Test
    public void testUpdateListProjectUsers_multipleUsers_userIdDoesNotExist() {
        // init test object
        int projectID = 1;
        listDtos.add(dto1);
        listDtos.add(dto2);

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(dto1.userID.toString())).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(dto2.userID.toString())).thenReturn(Boolean.FALSE);
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        assertNull(service.updateProjectUsers(projectID, listDtos));
    }

    @Test
    public void testUpdateListProjectUsers_resourcesExist_oneUser_roleNotUpdated() {
        // init test object
        int projectID = 1;
        String id = dto1.userID.toString();
        listDtos.add(dto1);

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(id)).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        List<ProjectUserDto> out = service.updateProjectUsers(projectID, listDtos);

        assertEquals(listDtos.size(), out.size());
        out.forEach(user -> assertNull(user.role));
    }

    @Test
    public void testUpdateListProjectUsers_resourcesExist_multipleUsers_roleNotUpdated() {
        // init test object
        int projectID = 1;
        listDtos.add(dto1);
        listDtos.add(dto2);

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(dto1.userID.toString())).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(dto2.userID.toString())).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(dto2.userID.toString())).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        List<ProjectUserDto> out = service.updateProjectUsers(projectID, listDtos);

        assertEquals(listDtos.size(), out.size());
        out.forEach(user -> assertNull(user.role));
    }

    @Test
    public void testUpdateListProjectUsers_resourcesExist_oneUser_roleUpdated() {
        // init test object
        int projectID = 1;
        String id = dto1.userID.toString();
        dto1.role = Role.CLIENT;
        listDtos.add(dto1);

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(id)).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        List<ProjectUserDto> out = service.updateProjectUsers(projectID, listDtos);

        assertEquals(listDtos.size(), out.size());
        out.forEach(user -> assertEquals(Role.CLIENT, user.role));
    }

    @Test
    public void testUpdateListProjectUsers_resourcesExist_multipleUsers_roleUpdated() {
        // init test object
        int projectID = 1;
        dto1.role = Role.CLIENT;
        dto2.role = Role.CLIENT;
        listDtos.add(dto1);
        listDtos.add(dto2);

        // provide knowledge
        when(projectRepository.existsById(projectID)).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(dto1.userID.toString())).thenReturn(Boolean.TRUE);
        when(userRepository.existsById(dto2.userID.toString())).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project1));
        when(userRepository.findById(dto2.userID.toString())).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        List<ProjectUserDto> out = service.updateProjectUsers(projectID, listDtos);

        assertEquals(listDtos.size(), out.size());
        out.forEach(user -> assertEquals(Role.CLIENT, user.role));
    }

    /**
     * tests for updateProjectUser()
     *
     * testUpdateProjectUser1: userID does not exist --> return null
     * testUpdateProjectUser2: projectID does not exist --> throw BadRequest
     * testUpdateProjectUser3: IDs exist, Role is missing --> return NOT updated ProjectUserEntity
     * testUpdateProjectUser4: IDs exist, Role exists --> return updated ProjectUserEntity
     */
    @Test
    public void testUpdateProjectUser_userIdDoesNotExist() {
        // init test object
        String id = dto1.userID.toString();
        // Step 1: provide knowledge
        when(userRepository.existsById(id)).thenReturn(Boolean.FALSE);
        // Step 3: execute test methode and assert result
        assertNull(service.updateProjectUser(projectID1,dto1));
    }

    @Test
    public void testUpdateProjectUser_projectIdDoesNotExist() {
        // init test object
        String id = dto1.userID.toString();

        // provide knowledge
        when(userRepository.existsById(id)).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.empty());


        // execute test methode and assert result
        Exception exception = assertThrows(BadRequest.class, ()
                -> service.updateProjectUser(projectID2, dto1));

        String expectedMessage = "User is not assigned to project.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateProjectUser_resourceExists_roleNotUpdated() {
        // init test object
        String id = dto1.userID.toString();

        // provide knowledge
        when(userRepository.existsById(id)).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        ProjectUserEntity out = service.updateProjectUser(projectID2, dto1);

        assertNull(out.role);

    }

    @Test
    public void testUpdateProjectUser_resourceExists_roleUpdated() {
        // init test object
        String id = dto1.userID.toString();
        dto1.role = Role.CLIENT;

        // provide knowledge
        when(userRepository.existsById(id)).thenReturn(Boolean.TRUE);
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(repository.findById(projectUserId)).thenReturn(Optional.of(projectUserEntity));

        // execute test methode and assert result
        ProjectUserEntity out = service.updateProjectUser(projectID2, dto1);

        assertEquals(out.role, Role.CLIENT);
    }

    
    /**
     * tests for deleteProjectUser()
     *
     * testUpdateProjectUser1: one or both of the necessary IDs do not exist --> throw ResourceNotFound Exception
     * testUpdateProjectUser2: IDs exist, Role is missing --> throw BadRequest Exception
     * testUpdateProjectUser3: IDs exist, Role does not exist --> throw BadRequest Exception
     * testUpdateProjectUser4: Input correct --> tbd
     */
    @Test
    public void testDeleteProjectUser_userIdNull() {
        // init test object
        dto2.userID = null;

        // Execute and assert test method()
        assertFalse(service.deleteProjectUser(projectID2,dto2));
    }

    @Test
    public void testDeleteProjectUser_resourceNotFound() {
        // Provide knowledge
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(repository.existsById(projectUserId)).thenReturn(Boolean.FALSE);

        // Execute and assert test method()
        Exception exception = assertThrows(ResourceNotFound.class, ()
                -> service.deleteProjectUser(projectID2,dto1));

        String expectedMessage = "User is not assigned to project.";
        String actualMessage = exception.getMessage();

        // Step 3: assert result
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteProjectUser_resourceExists() {
        // Provide knowledge
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(repository.existsById(projectUserId)).thenReturn(Boolean.TRUE);
        when(userRepository.getById(dto1.userID.toString())).thenReturn(user1);
        when(projectRepository.getById(projectID2)).thenReturn(project1);

        // Execute and assert test method()
        assertTrue(service.deleteProjectUser(projectID2,dto1));
    }

    @Test
    public void testWrapperDeleteProjectUser_oneUser_userIdNull() {
        // init test object
        dto2.userID = null;
        listDtos.add(dto2);

        // Execute and assert test method()
        assertFalse(service.wrapperDeleteProjectUser(projectID2, listDtos));
    }

    @Test
    public void testWrapperDeleteProjectUser_multipleUser_userIdNull() {
        // init test object
        dto2.userID = null;
        listDtos.add(dto1);
        listDtos.add(dto2);

        // Execute and assert test method()
        assertFalse(service.wrapperDeleteProjectUser(projectID2, listDtos));
    }

    @Test
    public void testWrapperDeleteProjectUser_success_oneUser() {
        // init test object
        listDtos.add(dto1);

        //provide knowledge
        when(projectRepository.existsById(projectID2)).thenReturn(Boolean.TRUE);
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(repository.existsById(projectUserId)).thenReturn(Boolean.TRUE);
        when(userRepository.getById(dto1.userID.toString())).thenReturn(user1);
        when(projectRepository.getById(projectID2)).thenReturn(project1);

        // Execute and assert test method()
        assertTrue(service.wrapperDeleteProjectUser(projectID2, listDtos));
    }

    @Test
    public void testWrapperDeleteProjectUser_success_multipleUser() {
        // init test object
        listDtos.add(dto1);
        listDtos.add(dto2);

        //provide knowledge
        when(projectRepository.existsById(projectID2)).thenReturn(Boolean.TRUE);
        when(userRepository.findById(dto1.userID.toString())).thenReturn(Optional.of(user1));
        when(projectRepository.findById(projectID2)).thenReturn(Optional.of(project1));
        when(repository.existsById(projectUserId)).thenReturn(Boolean.TRUE);
        when(userRepository.getById(dto1.userID.toString())).thenReturn(user1);
        when(projectRepository.getById(projectID2)).thenReturn(project1);
        when(userRepository.findById(dto2.userID.toString())).thenReturn(Optional.of(user1));
        when(userRepository.getById(dto2.userID.toString())).thenReturn(user1);

        // Execute and assert test method()
        assertTrue(service.wrapperDeleteProjectUser(projectID2, listDtos));
    }
}