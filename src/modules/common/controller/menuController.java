package modules.common.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class menuController {

    @FXML
    private AnchorPane commonAnchor;

    @FXML
    public void addTask(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(getClass().getResource("../../task/views/form.fxml"));
        commonAnchor.getChildren().setAll(node);
    }

    public void myTasks(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(getClass().getResource("../../task/views/myTasks.fxml"));
        commonAnchor.getChildren().setAll(node);
    }

    @FXML
    public void addProject(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(getClass().getResource("../../project/views/form.fxml"));
        commonAnchor.getChildren().setAll(node);
    }

    public void myProjects(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(getClass().getResource("../../project/views/myProjects.fxml"));
        commonAnchor.getChildren().setAll(node);
    }
}
