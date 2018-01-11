package modules.task.controller;

import javafx.scene.control.ChoiceBox;
import modules.common.Utils;
import modules.common.model.ResponseObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TaskController {

    public ChoiceBox projects;

    public void initialize(){
        listProject();
    }

    public void listProject(){
        try {
            ResponseObject res = Utils.sendGetRequest("/projects", Utils.token);
            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(res.getResponse());
            projects.getItems().addAll(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
