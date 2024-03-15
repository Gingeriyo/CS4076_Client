package org.example.cs4076_project;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewCalc {

    public static HashMap<Integer, ArrayList<String[]>> in(String in) {

        HashMap<Integer, ArrayList<String[]>> h = new HashMap<>();

        ArrayList<String[]> result = new ArrayList<String[]>();
        String[] s = in.split("~");
        // The entries in this array will be in order of time from 9 to 17.
        // e.g. 9_NAME_ROOM
        // This is the order they will be in per entry of the ArrayList "result".
        for (String str : s) {
            result.add(str.split("_"));
        }

        for (int i = 9; i <= 17; i++) {
            // Initialise the lists inside the hashmap.
            h.put(i, new ArrayList<String[]>());
        }

        for (int i = 9; i <= 17; i++) {
            for (String[] strings : result) {
                // Check the time the class is on.
                int time_int = Integer.parseInt(strings[0]);
                if (time_int == i) {
                    // If the time is the same as the key for the hashmap, we will insert a String[] into the value.
                    String[] temp = {strings[1], strings[2]};
                    h.get(i).add(temp);
                }
            }
        }

        return h;
    }
}
