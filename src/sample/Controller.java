package sample;

import javafx.scene.control.Label;

import java.awt.event.ActionEvent;

public class Controller {

    public Label helloWorld;

    public void sayHelloWorld(javafx.event.ActionEvent actionEvent) {
        helloWorld.setText("Hellooooo");
    }
}
