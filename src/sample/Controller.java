package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class Controller {

    // API
    private static String API_URL = "http://localhost:8081";

    // Login
    public TextField email;
    public PasswordField password;

    // User
    public String token = "";

    public void login(javafx.event.ActionEvent actionEvent) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("email",email.getText());
            params.put("password",password.getText());
            sendPostRequest("/auth/login", params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPostRequest(String path, Map<String, String> params) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();

        // Config request
        HttpPost request = new HttpPost(API_URL + path);
        request.addHeader("Content-Type", "application/json");

        // Set params
        StringEntity parameters = new StringEntity(new JSONObject(params).toString());
        request.setEntity(parameters);

        // Execute request and get response
        HttpResponse response = httpClient.execute(request);
        int status = response.getStatusLine().getStatusCode();

        // Set the token or send error message
        if(status==200){
            token = EntityUtils.toString(response.getEntity());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Echec de l'authentification !");
            alert.showAndWait();
        }

    }

}
