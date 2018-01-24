package modules.project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modules.common.Utils;
import modules.common.model.ResponseObject;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class formController {

    @FXML
    public TextField title;
    @FXML
    public DatePicker datePicker;

    public void addProject(){
        try {
            // Create params with project data
            HashMap<String, String> params = new HashMap<>();
            params.put("title", title.getText());
            params.put("creator", Utils.user.getId());
            params.put("status", "true");

            // Send request
            ResponseObject res = Utils.sendPostRequest("/projects", params);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
