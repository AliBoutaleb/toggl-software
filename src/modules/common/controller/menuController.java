package modules.common.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import modules.common.Utils;
import modules.common.model.ResponseObject;

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

    public void listTask(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(getClass().getResource("../../task/views/task.fxml"));
        commonAnchor.getChildren().setAll(node);
    }
}
