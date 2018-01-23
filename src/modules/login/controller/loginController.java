package modules.login.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modules.common.Utils;
import modules.common.model.ResponseObject;
import modules.common.model.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class loginController {

    // Login
    public TextField email;
    public PasswordField password;

    public void login(ActionEvent event) {
        ResponseObject response = new ResponseObject();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("email", email.getText());
            params.put("password", password.getText());

            // Send request
            response = Utils.sendPostRequest("/auth/login", params);

            // Read response
            if (response.getStatus()==200){
                // Set token
                Utils.token = response.getResponse();
                getUserBytoken();
                setStage(event, "../../common/views/menu.fxml");
            }else{
                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Echec de l'authentification !");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set and switch to new stage
    public void setStage(ActionEvent event, String fxmlPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public User getUserBytoken(){
        ResponseObject res = new ResponseObject();
        try{
            res = Utils.sendGetRequest("/users");
            ObjectMapper mapper = new ObjectMapper();
            Utils.user = mapper.readValue(res.getResponse(),User.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return new User();
    }

}
