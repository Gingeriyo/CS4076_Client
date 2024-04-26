package org.example.cs4076_project;

import javafx.concurrent.Task;

import java.util.Objects;

// performs a task asynchronously and will return a value of whatever data type is in <>
public class ConcurrentCalc extends Task<String> {

    //App application = new App();

    private final String date;
    private final String type;
    private final String module;

    public ConcurrentCalc(String date, String type, String module) {
        this.date = date;
        this.type = type;
        this.module = module;
    }

    @Override
    protected String call() {
        updateMessage("Processing... ");
        TCP tcp = new TCP();
        String m = tcp.init();
        if (!Objects.equals(m, "OK")) {
            updateMessage(m);
            return "";
        } else if (Objects.equals(type, "VIEW_")){
            updateMessage("Success.");

            String input = tcp.send("VIEW_" + date);
            return ViewCalc.in(input);
        } else if (Objects.equals(type, "VIEWCLASS_")){
            updateMessage("Success.");

            String input = tcp.send("VIEWCLASS_" + date + "_" + module);
            return ViewCalc.in(input);
        } else {
            updateMessage("Lecture Times Changed Successfully.");

            String input = tcp.send("EARLYLECTURES");
            return ViewCalc.in(input);
        }
    }


}
