package modules.project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modules.common.Utils;
import modules.common.model.Project;
import modules.common.model.ResponseObject;
import modules.common.model.Task;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class myProjectsController {

    @FXML
    public TableView<Project> table;

    public void initialize(){
        setTableView();
        listProject();
    }

    public void setTableView(){
        // Set table columns
        TableColumn title = new TableColumn("Title");
        TableColumn status = new TableColumn("Status");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.getColumns().addAll(title, status);
    }

    public void listProject(){
        ObservableList<Project> data = FXCollections.observableArrayList();
        try {
            // Send request
            ResponseObject res = Utils.sendGetRequest("/projects/");

            // Add project on array
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(res.getResponse());
            Project[] projectArray = mapper.readValue(res.getResponse(), Project[].class);
            data.addAll(projectArray);
            // Set column and data
            table.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
