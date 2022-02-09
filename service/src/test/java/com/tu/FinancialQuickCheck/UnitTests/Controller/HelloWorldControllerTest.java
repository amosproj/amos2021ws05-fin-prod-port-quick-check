package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.HelloWorldController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HelloWorldControllerTest {
    static Logger log = Logger.getLogger(HelloWorldControllerTest.class.getName());
    private HelloWorldController controller;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in HelloWorldControllerTest.class");

        controller = new HelloWorldController();

    }

    @Test
    public void testIndex() {
        // Step 1: execute test method and assert
        String expectedOut = "Hello World!";
        String out = controller.index();

        assertTrue(out == expectedOut);
    }
}
