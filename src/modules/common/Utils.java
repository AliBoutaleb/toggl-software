package modules.common;

import modules.common.model.ResponseObject;
import modules.common.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class Utils {

    // API
    private static String API_URL = "http://localhost:8081";
    public static String token = "";
    public static User user;

    // Send http post request
    public static ResponseObject sendPostRequest(String path, Map<String, String> params) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();

        // Config request
        HttpPost request = new HttpPost(API_URL + path);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", Utils.token);

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

    // Send http get request
    public static ResponseObject sendGetRequest(String path) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();

        // Config request
        HttpGet request = new HttpGet(API_URL + path);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", Utils.token);

        // Execute request and get response
        HttpResponse response = httpClient.execute(request);

        // Set ResponseObject
        ResponseObject res = new ResponseObject();
        res.setStatus(response.getStatusLine().getStatusCode());
        res.setResponse(EntityUtils.toString(response.getEntity()));

        return res;
    }
}
