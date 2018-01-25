package modules.project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import modules.common.Utils;
import modules.common.model.Project;
import modules.common.model.ResponseObject;
import modules.common.model.Task;
import modules.common.model.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class myProjectsController {

    @FXML
    public TableView<Project> table;
    @FXML
    public TableView<User> tableMembers;
    @FXML
    public TableView<Task> tableTasks;
    @FXML
    public TableColumn title;
    @FXML
    public TableColumn status;
    @FXML
    public TableColumn lastName;
    @FXML
    public TableColumn firstName;
    @FXML
    public TableColumn titleTask;
    @FXML
    public TableColumn timer;
    @FXML
    public TableColumn dueDate;
    @FXML
    public ObservableList<Project> data;
    @FXML
    public ObservableList<User> dataMembers;
    @FXML
    public ObservableList<Task> dataTasks;
    @FXML
    public Button modify;
    @FXML
    public Button save;

    public void initialize(){
        setTableView();
        listProject();
    }

    public void setTableView(){
        // Set main table
        title = new TableColumn("Title");
        status = new TableColumn("Status");
        title.setCellValueFactory(new PropertyValueFactory<Project, String>("title"));
        status.setCellValueFactory(new PropertyValueFactory<Project, String>("status"));
        table.getColumns().addAll(title, status);
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        // Set table members
        lastName = new TableColumn("Last name");
        lastName.setCellValueFactory(new PropertyValueFactory<Project, String>("lastName"));
        firstName = new TableColumn("First name");
        firstName.setCellValueFactory(new PropertyValueFactory<Project, String>("firstName"));
        tableMembers.getColumns().addAll(lastName, firstName);

        // Set table tasks
        titleTask = new TableColumn("Title");
        titleTask.setCellValueFactory(new PropertyValueFactory<Project, String>("title"));
        timer = new TableColumn("Timer");
        timer.setCellValueFactory(new PropertyValueFactory<Project, String>("timer"));
        dueDate = new TableColumn("Due date");
        dueDate.setCellValueFactory(new PropertyValueFactory<Project, String>("dueDate"));
        tableTasks.getColumns().addAll(titleTask, timer, dueDate);
   }

   public void selectProject(){
       dataMembers = FXCollections.observableArrayList();
       dataTasks = FXCollections.observableArrayList();
       try {
           // Set table members
           String[] membersID = table.getSelectionModel().getSelectedItem().getMembers();
           for (String member : membersID){
               ResponseObject res = Utils.sendGetRequest("/users/"+member.toString());
               ObjectMapper mapper = new ObjectMapper();
               dataMembers.add(mapper.readValue(res.getResponse(), User.class));
           }
           tableMembers.setItems(dataMembers);

           // Set table tasks
           String[] tasksID = table.getSelectionModel().getSelectedItem().getTasks();
           for (String task : tasksID){
               ResponseObject res = Utils.sendGetRequest("/tasks/"+task.toString());
               ObjectMapper mapper = new ObjectMapper();
               dataTasks.add(mapper.readValue(res.getResponse(), Task.class));
           }
           tableTasks.setItems(dataTasks);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public void deleteProject(){
        String id = table.getSelectionModel().getSelectedItem().getId();
        try {
            Utils.sendDeleteRequest("/projects/"+id);
            listProject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyProject(){
        table.setEditable(true);
        modify.setDisable(true);
        save.setDisable(false);
    }

    public void saveProject(){
        modify.setDisable(false);
        save.setDisable(true);
        //data = table.getItems();
        System.out.println(data);
        for(Project project : data){
            System.out.println(project.getTitle());
        }
    }

    public void listProject(){
        data = FXCollections.observableArrayList();
        try {
            // Send request
            ResponseObject res = Utils.sendGetRequest("/projects/");

            // Add project on array
            ObjectMapper mapper = new ObjectMapper();
            Project[] projectArray = mapper.readValue(res.getResponse(), Project[].class);
            data.addAll(projectArray);
            // Set column and data
            table.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
