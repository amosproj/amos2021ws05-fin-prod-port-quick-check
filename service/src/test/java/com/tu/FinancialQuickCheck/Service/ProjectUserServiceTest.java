//package com.tu.FinancialQuickCheck.Service;
//
//
//import com.tu.FinancialQuickCheck.db.*;
//import com.tu.FinancialQuickCheck.dto.ProjectUserDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Logger;
//
//@Service
//public class ProjectUserServiceTest {
//
//    static Logger log = Logger.getLogger(ProductRatingServiceTest.class.getName());
//
//    @Mock
//    ProjectUserRepository repository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    ProjectRepository projectRepository;
//
//    private ProjectUserService service;
//
//    private ProjectUserDto dto1;
//    private ProjectUserDto dto2;
//    private ProjectUserDto dto3;
//    private ProjectUserDto dto4;
//    private List<ProjectUserDto> dtos;
//
//    @BeforeEach
//    public void init() {
//        log.info("@BeforeEach - setup for Tests in ProjectUserServiceTest.class");
//        // init ProjectService
//        service = new ProjectUserService(userRepository, projectRepository, repository);
//
//        dto1 = new ProjectUserDto();
//        dto2 = new ProjectUserDto();
//        dto3 = new ProjectUserDto();
//        dto4 = new ProjectUserDto();
//
//        dtos = new ArrayList<>();
//        dtos.add(dto1);
//        dtos.add(dto2);
//        dtos.add(dto2);
//        dtos.add(dto4);
//    }
//
//    /**
//     * tests for getProjectUsersByProjectId()
//     *
//     * testGetProjectUsersByProjectId1: ID does not exist --> throw ResourceNotFound Exception
//     * testGetProjectUsersByProjectId2: ID exists --> return List<ProjectUserDto>
//     */
//    @Test
//    @Disabled
//    public void testGetProjectUsersByProjectId1() {
//        // Step 0: init test object
//
//
//        // Step 1: provide knowledge
//
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
////        String expectedMessage = ;
////        String actualMessage = exception.getMessage();
////
////        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    @Disabled
//    public void testGetProjectUsersByProjectId2() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//
//
//    /**
//     * tests for createProjectUser()
//     *
//     * testCreateProjectUser1: one or both of the necessary IDs do not exist --> throw ResourceNotFound Exception
//     * testCreateProjectUser2: IDs exist, Role is missing --> throw BadRequest Exception
//     * testCreateProjectUser3: IDs exist, Role does not exist --> throw BadRequest Exception
//     * testCreateProjectUser4: Input correct --> tbd
//     */
//    @Test
//    @Disabled
//    public void testCreateProjectUser1() {
//        // Step 0: init test object
//
//
//        // Step 1: provide knowledge
//
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
////        String expectedMessage = ;
////        String actualMessage = exception.getMessage();
////
////        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    @Disabled
//    public void testCreateProjectUser2() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//
//    @Test
//    @Disabled
//    public void testCreateProjectUser3() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//
//    @Test
//    @Disabled
//    public void testCreateProjectUser4() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//
//
//    /**
//     * tests for updateProjectUser()
//     *
//     * testUpdateProjectUser1: one or both of the necessary IDs do not exist --> throw ResourceNotFound Exception
//     * testUpdateProjectUser2: IDs exist, Role is missing --> throw BadRequest Exception
//     * testUpdateProjectUser3: IDs exist, Role does not exist --> throw BadRequest Exception
//     * testUpdateProjectUser4: Input correct --> tbd
//     */
//    @Test
//    @Disabled
//    public void testUpdateProjectUser1() {
//        // Step 0: init test object
//
//
//        // Step 1: provide knowledge
//
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
////        String expectedMessage = ;
////        String actualMessage = exception.getMessage();
////
////        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    @Disabled
//    public void testUpdateProjectUser2() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//
//    @Test
//    @Disabled
//    public void testUpdateProjectUser3() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//
//    @Test
//    @Disabled
//    public void testUpdateProjectUser4() {
//        // Step 0: init test object
//
//        // Step 1: provide knowledge
//
//        // Step 2: Execute test method()
//
//        // Step 3: assert result
//
//    }
//}
