package org.example.cs4076_project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TCPTest {
    // For all these tests to pass, the server must be restarted after
    // every test (assuming it does not have permanent storage)
    @Test
    @DisplayName("Initialise TCP Client")
    void initTest() {
        TCP test = new TCP();
        // This test will only return true if the server is online
        assertEquals("OK", test.init());
    }

    @Test
    @DisplayName("Send Stop")
    void sendStop() {
        TCP test = new TCP();
        test.init();
        assertEquals("TERMINATE", test.send("STOP"));
    }

    @Test
    @DisplayName("Send Add Success")
    void sendAddSuccess() {
        TCP test = new TCP();
        test.init();
        assertEquals("ADD_CLASS_SUCCESS", test.send("ADD_CS2004_S205_2024-03-16_10"));
    }

    @Test
    @DisplayName("Send Add Success 2")
    void sendAddSuccessTwo() {
        TCP test = new TCP();
        test.init();
        assertEquals("ADD_CLASS_SUCCESS", test.send("ADD_TEMU-UNIVERSITY_KBG_2024-03-16_11"));
    }

    @Test
    @DisplayName("Send Add Incorrect Date")
    void sendAddIncorrectDate() {
        TCP test = new TCP();
        test.init();
        assertEquals("ERR: Invalid date!", test.send("ADD_CS2004_S205_LOL_10"));
    }

    @Test
    @DisplayName("Send Add Incorrect Time")
    void sendAddIncorrectTime() {
        TCP test = new TCP();
        test.init();
        assertEquals("ERR: Invalid time slot!", test.send("ADD_CS2004_S205_2024-04-05_8"));
    }

    @Test
    @DisplayName("Send Add Not enough args")
    void sendAddNotEnoughArgs() {
        TCP test = new TCP();
        test.init();
        assertEquals("ERR: Not enough arguments!", test.send("ADD"));
    }

    @Test
    @DisplayName("Send Add Duplicate")
    void sendAddDuplicate() {
        TCP test = new TCP();
        test.init();
        test.send("ADD_DUPECLASS_S205_2024-03-14_10");
        assertEquals("ADD_CLASS_FAIL", test.send("ADD_SOME-OTHER-CLASS_S205_2024-03-14_10"));
    }

    @Test
    @DisplayName("Send Add and Remove")
    void sendAddAndRemove() {
        TCP test = new TCP();
        test.init();
        test.send("ADD_TO-BE-REMOVED_STUPID-ROOM_2024-03-15_10");
        assertEquals("REMOVE_CLASS_SUCCESS", test.send("REMOVE_TO-BE-REMOVED_STUPID-ROOM_2024-03-15_10"));
    }

    @Test
    @DisplayName("View Added Classes")
    void viewAddedClasses() {
        TCP test = new TCP();
        test.init();
        test.send("ADD_777_BACKROOMS_2024-03-16_17");
        test.send("ADD_COOL-CLASS_BACKROOMS_2024-03-16_11");
        test.send("ADD_SUPER-DUPER-CLASS_BACKROOMS_2024-03-16_9");
        assertEquals("17_777_BACKROOMS~9_SUPER-DUPER-CLASS_BACKROOMS~11_COOL-CLASS_BACKROOMS~", test.send("VIEW_2024-03-16"));
    }

    @Test
    @DisplayName("View Added Classes by Name")
    void viewAddedClassesNamed() {
        TCP test = new TCP();
        test.init();
        test.send("ADD_777_BACKROOMS_2024-03-16_17");
        test.send("ADD_COOL-CLASS_BACKROOMS_2024-03-16_11");
        test.send("ADD_SUPER-DUPER-CLASS_BACKROOMS_2024-03-16_9");
        assertEquals("11_COOL-CLASS_BACKROOMS~", test.send("VIEWCLASS_2024-03-16_COOL-CLASS"));
    }

    @Test
    @DisplayName("View Added Classes by Name Same Day")
    void viewAddedClassesNamedSameDay() {
        TCP test = new TCP();
        test.init();
        test.send("ADD_777_kemmy_2024-03-16_17");
        test.send("ADD_COOL-CLASS_BACKROOMS_2024-03-16_11");
        test.send("ADD_777_BACKROOMS_2024-03-16_9");
        test.send("ADD_777_The-Garage_2024-03-16_9");
        assertEquals("17_777_kemmy~9_777_BACKROOMS~9_777_The-Garage~", test.send("VIEWCLASS_2024-03-16_777"));
    }
}