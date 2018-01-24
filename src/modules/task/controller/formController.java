package modules.task.controller;

import com.sun.javafx.scene.control.behavior.ListViewBehavior;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import modules.common.Utils;
import modules.common.model.Project;
import modules.common.model.ResponseObject;
import modules.common.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class formController {

    public ChoiceBox projects;
    public ChoiceBox users;
    public TextField title;
    public DatePicker datePicker;

    public void initialize(){
        listProjects();
        listUsers();
    }

    public void listProjects(){
        try {
            ResponseObject res = Utils.sendGetRequest("/projects");
            ObjectMapper mapper = new ObjectMapper();
            Project[] projectArray = mapper.readValue(res.getResponse(),Project[].class);
            projects.getItems().addAll(projectArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listUsers(){
        try {
            ResponseObject res = Utils.sendGetRequest("/users/all");
            ObjectMapper mapper = new ObjectMapper();
            User[] userArray = mapper.readValue(res.getResponse(),User[].class);
            users.getItems().addAll(userArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(){
        try {
            // Create params with task data
            HashMap<String, String> params = new HashMap<>();
            params.put("title", title.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            params.put("dueDate", datePicker.getValue().format(formatter));
            params.put("time","00:00:00");
            User owner = (User) users.getValue();
            params.put("owner", owner.getId());
            Project project = (Project) projects.getValue();
            params.put("project",project.getId());

            // Send request
            ResponseObject res = Utils.sendPostRequest("/tasks", params);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
