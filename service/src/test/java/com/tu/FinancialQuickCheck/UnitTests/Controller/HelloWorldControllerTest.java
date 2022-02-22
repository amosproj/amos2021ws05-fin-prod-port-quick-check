package com.tu.FinancialQuickCheck.UnitTests.Controller;

import com.tu.FinancialQuickCheck.Controller.HelloWorldController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldControllerTest {

    static Logger log = Logger.getLogger(HelloWorldControllerTest.class.getName());

    private HelloWorldController controller;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in HelloWorldControllerTest.class");

        controller = new HelloWorldController();
    }

    @Test
    public void test1_index() {
        String expectedOut = "Hello World!";
        String out = controller.index();

        assertEquals(expectedOut, out);
    }

}
