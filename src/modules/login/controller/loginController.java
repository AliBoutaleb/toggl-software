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
import modules.common.model.ResponseObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class loginController {

    // API
    private static String API_URL = "http://localhost:8081";

    // Login
    public TextField email;
    public PasswordField password;

    // User
    public String token = "";

    public void login(ActionEvent event) {
        ResponseObject response = new ResponseObject();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("email",email.getText());
            params.put("password",password.getText());

            // Send request
            response = sendPostRequest("/auth/login", params);

            // Read response
            if (response.getStatus()==200){
                // Set token
                token = response.getResponse();
                setStage(event, "../../common/views/test.fxml");
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

    public void setStage(ActionEvent event, String fxml) throws IOException{
        Parent testParent = FXMLLoader.load(getClass().getResource(fxml));
        Scene testScene = new Scene(testParent);
        Stage testStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        testStage.setScene(testScene);
        testStage.show();
    }

    public ResponseObject sendPostRequest(String path, Map<String, String> params) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();

        // Config request
        HttpPost request = new HttpPost(API_URL + path);
        request.addHeader("Content-Type", "application/json");

        // Set params
        StringEntity parameters = new StringEntity(new JSONObject(params).toString());
        request.setEntity(parameters);

        // Execute request and get response
        HttpResponse response = httpClient.execute(request);

        // Set ResponseObject
        ResponseObject res = new ResponseObject();
        res.setStatus(response.getStatusLine().getStatusCode());
        res.setResponse(EntityUtils.toString(response.getEntity()));

        return res;
    }

}
