package modules.task.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modules.common.Utils;
import modules.common.model.ResponseObject;
import modules.common.model.Task;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class myTasksController{

    @FXML
    public TableView<Task> table;
    @FXML
    public Label selectTitle;
    @FXML
    public Label selectDueDate;
    @FXML
    public Label selectTimer;
    public String selectID;
    public long startTime;
    public long currentTime;

    public void initialize(){
        setTableView();
        listUserTasks();
    }

    public void reload(){
        listUserTasks();
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    public void stopTimer(){
        // Time spend on task
        long spendTime = System.currentTimeMillis() - startTime;

        // Current timer value
        String[] timer = selectTimer.getText().split(":");
        currentTime = timerToMilliseconds(
                Long.valueOf(timer[0]).longValue(),
                Long.valueOf(timer[1]).longValue(),
                Long.valueOf(timer[2]).longValue());

        // Update new timer of task
        String newTime = millisecondsToTimer(currentTime + spendTime);
        HashMap<String, String> params = new HashMap<>();
        params.put("timer", newTime);
        try {
            ResponseObject res = Utils.sendPostRequest("/tasks/" + selectID, params);
            System.out.println(res.getStatus());
            System.out.println(res.getResponse());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Convert ms to timer (hh:mm:ss)
    public String millisecondsToTimer(long duration) {
        long ms = (duration%1000)/100;
        long sec  = (duration/1000)%60;
        long min  = ((duration/(1000*60))%60);
        long hours  = ((duration/(1000*60*60))%24);

        String timer = hours+":"+min+":"+sec;

        return timer;
    }

    // Convert timer to ms
    public long timerToMilliseconds(long hours, long minutes, long seconds ) {
        return (hours*1000*60*60)+(minutes*1000*60)+(seconds*1000);
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
        selectTimer.setText(task.getTimer());
        selectID = task.getId();

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