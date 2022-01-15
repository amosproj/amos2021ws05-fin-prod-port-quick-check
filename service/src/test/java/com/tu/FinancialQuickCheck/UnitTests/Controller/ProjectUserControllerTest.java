package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.ProjectUserController;
import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProjectUserService;
import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectUserControllerTest {

    static Logger log = Logger.getLogger(ProjectUserControllerTest.class.getName());

    @Mock
    private ProjectUserService service;

    private ProjectUserController controller;

    private ProjectUserDto dto1;
    private ProjectUserDto dto2;
    private List<ProjectUserDto> listDtos;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectUserControllerTest.class");

        controller = new ProjectUserController(service);

        dto1 = new ProjectUserDto();
        dto2 = new ProjectUserDto();
        listDtos = new ArrayList<>();
    }

    @Test
    public void testFindProjectUsersByProjectId_resourceNotFound() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.getProjectUsersByProjectId(projectID)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.findProjectUsersByProjectId(projectID));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindProjectUsersByProjectId_resourceExists() {
        listDtos.add(dto1);
        listDtos.add(dto2);
        int projectID = 1;

        // Step 1: provide knowledge
        when(service.getProjectUsersByProjectId(projectID)).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<ProjectUserDto> out = controller.findProjectUsersByProjectId(projectID);

        assertTrue(out.size() == listDtos.size());
    }

    @Test
    public void testUpdateProjectUser_resourceNotFound() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.updateProjectUsers(projectID, listDtos)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.updateProjectUser(listDtos, projectID));

        String expectedMessage = "Project or User not Found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateProjectUser_resourceExists() {
        listDtos.add(dto1);
        listDtos.add(dto2);
        int projectID = 1;

        // Step 1: provide knowledge
        when(service.updateProjectUsers(projectID, listDtos)).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<ProjectUserDto> out = controller.updateProjectUser(listDtos, projectID);

        assertTrue(out.size() == listDtos.size());
    }

    @Test
    public void testDeleteProjectUser_badRequest() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.wrapperDeleteProjectUser(projectID, listDtos)).thenReturn(Boolean.FALSE);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.deleteProjectUser(listDtos, projectID));

        String expectedMessage = "Input missing/is incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteProjectUser_success() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.wrapperDeleteProjectUser(projectID, listDtos)).thenReturn(Boolean.TRUE);

        // Step 2: execute test method and assert
        controller.deleteProjectUser(listDtos, projectID);
    }
}
