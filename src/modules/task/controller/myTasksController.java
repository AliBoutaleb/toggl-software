package modules.task.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import modules.common.Utils;
import modules.common.model.ResponseObject;
import modules.common.model.Task;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class myTasksController{

    @FXML
    public TableView<Task> table;
    @FXML
    public Label selectTitle;
    @FXML
    public Label selectDueDate;
    @FXML
    public Label selectTimer;

    public void initialize(){
        setTableView();
        listUserTasks();
    }

    public void reload(){
        listUserTasks();
    }

    public void startTimer(){
        Integer timer = 15;
        selectTimer.setText(timer.toString());

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500)));
        timeline.play();
        System.out.println(timeline.getDelay());
    }

    public void stopTimer(){

    }

    public void setTableView(){
        // Set table columns
        TableColumn title = new TableColumn("Title");
        TableColumn timer = new TableColumn("Timer");
        TableColumn dueDate = new TableColumn("Due date");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        timer.setCellValueFactory(new PropertyValueFactory<>("timer"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        table.getColumns().addAll(title, timer, dueDate);
    }

    public void selectTask(){
        // Set label values
        Task task = table.getSelectionModel().selectedItemProperty().getValue();
        selectTitle.setText(task.getTitle());
        selectDueDate.setText(task.getDueDate());

        // Set label visible
        selectTitle.setVisible(true);
        selectDueDate.setVisible(true);
        selectTimer.setVisible(true);
    }

    public void listUserTasks(){
        ObservableList<Task> data = FXCollections.observableArrayList();
        try {
            for(String task : Utils.user.getTasks()){
                // Send request
                ResponseObject res = Utils.sendGetRequest("/tasks/"+task);

                // Add task on array
                ObjectMapper mapper = new ObjectMapper();
                data.add(mapper.readValue(res.getResponse(),Task.class));
            }

            // Set column and data
            table.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}