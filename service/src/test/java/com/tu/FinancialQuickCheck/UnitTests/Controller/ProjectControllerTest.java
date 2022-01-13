package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.ProjectController;
import com.tu.FinancialQuickCheck.Exceptions.BadRequest;
import com.tu.FinancialQuickCheck.Exceptions.ResourceNotFound;
import com.tu.FinancialQuickCheck.Service.ProductService;
import com.tu.FinancialQuickCheck.Service.ProjectService;
import com.tu.FinancialQuickCheck.Service.ResultService;
import com.tu.FinancialQuickCheck.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    static Logger log = Logger.getLogger(ProjectControllerTest.class.getName());

    @Mock
    private ProjectService service;
    @Mock
    private ProductService productService;
    @Mock
    private ResultService resultService;

    private ProjectController controller;

    private ProjectDto dto1;
    private List<SmallProjectDto> listDtos;
    private ProductDto productDto;
    private List<ProductDto> listProductDtos;
    private List<ProjectUserDto> listProjectUserDtos;
    private List<ResultDto> listOfResults;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectControllerTest.class");

        controller = new ProjectController(service, productService, resultService);

        dto1 = new ProjectDto();
        ProjectDto dto2 = new ProjectDto();
        listDtos = new ArrayList<>();

        productDto = new ProductDto();
        productDto.productName = "Produktname";
        productDto.productArea = new ProductAreaDto();
        listProductDtos = new ArrayList<>();

        listProjectUserDtos = new ArrayList<>();
        listOfResults = new ArrayList<>();
    }

    @Test
    public void testFindALL() {
        // Step 1: provide knowledge
        when(service.getAllProjects()).thenReturn(listDtos);

        // Step 2: execute test method and assert
        List<SmallProjectDto> out = controller.findALL();

        assertEquals(out.size(), listDtos.size());
    }

    @Test
    public void testCreateProject_badRequest() {
        // Step 1: provide knowledge
        when(service.createProject(dto1)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.createProject(dto1));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateProject_success() {
        // Step 1: provide knowledge
        when(service.createProject(dto1)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProjectDto out = controller.createProject(dto1);

        assertSame(out, dto1);
    }

    @Test
    public void testFindByIdProject_resourceNotFound() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.getProjectById(projectID)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.findById(projectID));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByIdProject_resourceExists() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.getProjectById(projectID)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProjectDto out = controller.findById(projectID);

        assertSame(out, dto1);
    }

    @Test
    public void testUpdateByIdProject_badRequest() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.updateProject(dto1, projectID)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.updateById(dto1, projectID));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateByIdProject_success() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.updateProject(dto1, projectID)).thenReturn(dto1);

        // Step 2: execute test method and assert
        ProjectDto out = controller.updateById(dto1, projectID);

        assertSame(out, dto1);
    }

    @Test
    public void testFindProductsByProject_badrequest() {
        int projectID = 1;
        String productArea = "invalid";

        // execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.findProductsByProject(projectID, Optional.of(productArea)));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindProductsByProject_success_productAreaNotNull() {
        int projectID = 1;
        String productArea = "1";

        //execute test method and assert
        List<ProductDto> out = controller.findProductsByProject(projectID, Optional.of(productArea));

        assertEquals(out.size(), listProductDtos.size());
    }

    @Test
    public void testFindProductsByProject_success_productAreaNull() {
        int projectID = 1;

        //execute test method and assert
        List<ProductDto> out = controller.findProductsByProject(projectID, Optional.empty());

        assertEquals(out.size(), listProductDtos.size());
    }

    @Test
    public void testCreateProduct_badRequest_missingProductAreaOfProduct() {
        int projectID = 1;
        productDto.productArea = null;
        // execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.createProduct(projectID, productDto));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateProduct_badRequest_missingProductNameOfProduct() {
        int projectID = 1;
        productDto.productName = null;
        // execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.createProduct(projectID, productDto));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateProduct_badRequest_missingProductNameOfProductVariation() {
        int projectID = 1;

        // provide knowledge
        when(productService.wrapper_createProduct(projectID, productDto)).thenReturn(null);

        // execute test method and assert
        Exception exception;
        exception = assertThrows(BadRequest.class,
                () -> controller.createProduct(projectID, productDto));

        String expectedMessage = "Input is missing/incorrect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateProduct_success() {
        int projectID = 1;

        // provide knowledge
        when(productService.wrapper_createProduct(projectID, productDto)).thenReturn(listProductDtos);

        // execute test method and assert
        List<ProductDto> out = controller.createProduct(projectID, productDto);

        assertEquals(out.size(), listProductDtos.size());
    }

    @Test
    public void testCreateProjectUser_badRequest() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.createProjectUsers(projectID, listProjectUserDtos)).thenReturn(null);

        // Step 2: execute test method and assert
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.createProjectUser(listProjectUserDtos, projectID));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateProjectUser_success() {
        int projectID = 1;
        // Step 1: provide knowledge
        when(service.createProjectUsers(projectID, listProjectUserDtos)).thenReturn(listProjectUserDtos);

        // Step 2: execute test method and assert
        List<ProjectUserDto> out = controller.createProjectUser(listProjectUserDtos, projectID);

        assertEquals(out.size(), listProjectUserDtos.size());
    }

    @Test
    public void testGetResults_resourceNotFound(){
        int projectID = 1;

        // provide knowledge
        when(resultService.getResults(projectID, Optional.empty())).thenReturn(null);

        // execute and assert test methode
        Exception exception;
        exception = assertThrows(ResourceNotFound.class,
                () -> controller.getResults(projectID, Optional.empty()));

        String expectedMessage = "projectID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetResults_resourceExists(){
        int projectID = 1;

        // provide knowledge
        when(resultService.getResults(projectID, Optional.empty())).thenReturn(listOfResults);

        // execute and assert test methode
        List<ResultDto> out = controller.getResults(projectID, Optional.empty());

        assertEquals(out.size(), listOfResults.size());
    }
}
