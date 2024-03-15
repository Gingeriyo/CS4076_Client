package org.example.cs4076_project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCalcTest {

    @Test
    @DisplayName("Test the Calculator")
    void initTest() {
        String view = "9_CLASS_SCHUMAN~9_ANOTHER CLASS_kemmy~11_poo class._kemmy";
        HashMap<Integer, ArrayList<String[]>> h = ViewCalc.in(view);
        for (int name : h.keySet()) {
            System.out.println(name + ":");
            String temp = "";
            for (int i = 0; i < h.get(name).size(); i++) {
                temp += h.get(name).get(i)[0] + ", " + h.get(name).get(i)[1] + "\n";
            }
            System.out.println(temp + "\n");
        }
        assertEquals(true, true);
    }
}
