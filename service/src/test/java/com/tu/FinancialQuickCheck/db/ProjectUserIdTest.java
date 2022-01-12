package com.tu.FinancialQuickCheck.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;


public class ProjectUserIdTest {

    static Logger log = Logger.getLogger(ProjectUserIdTest.class.getName());

    ProjectUserId id1;
    ProjectUserId id1_copy;
    ProjectUserId id2;
    ProjectUserId id2_copy;
    UserEntity user1;
    UserEntity user2;
    ProjectEntity project1;
    ProjectEntity project2;

    @BeforeEach
    public void init() {
        log.info("@BeforeEach - setup for Tests in ProjectUserId.class");

        // Define UserEntities
        user1 = new UserEntity();
        user1.username = "Name 1";
        user1.email = "user1@email.com";
        user2 = new UserEntity();
        user2.username = "Name 2";
        user2.email = "user2@email.com";

        // Define ProjectEntities
        project1 = new ProjectEntity();
        project1.id = 1;
        project1.name = "Project 1";

        project2 = new ProjectEntity();
        project2.id = 2;
        project2.name = "Project 2";

        // Define ProjectUserId
        id1 = new ProjectUserId();
        id1.setProject(project1);
        id1.setUser(user1);
        id2 = new ProjectUserId();
        id2.setProject(project2);
        id2.setUser(user2);

        id1_copy = new ProjectUserId();
        id1_copy.setProject(project1);
        id1_copy.setUser(user1);
        id2_copy = new ProjectUserId();
        id2_copy.setProject(project2);
        id2_copy.setUser(user2);
    }

    /**
     * tests for equals()
     *
     * testEqualsTrue: two objects are equal --> return True
     * testEqualsTrue: two objects are  NOT equal --> return False
     */
    @Test
    public void testEquals_true() {
        assertTrue(id1.equals(id1_copy));
        assertTrue(id2.equals(id2_copy));
    }

    @Test
    public void testEquals_false() {
        assertFalse(id1.equals(id2));
        assertFalse(id2.equals(id1));
    }

    /**
     * tests for hashCode()
     *
     * testHashCode: hashCodes of the same object are equal
     * testHashCode: hashCodes of the two different objects are NOT equal
     */
    @Test
    public void testHashCode_equal() {
        assertEquals(id1.hashCode(), id1_copy.hashCode());
        assertEquals(id2.hashCode(), id2_copy.hashCode());
    }

    @Test
    public void testHashCode_notEqual() {
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

}
