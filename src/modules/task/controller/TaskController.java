package modules.task.controller;

import org.json.JSONObject;
import modules.common.Utils;
import modules.common.model.ResponseObject;

import java.io.IOException;

public class TaskController {

    public void initialize(){
        listProject();
    }

    public void listProject(){
        try {
            ResponseObject res = Utils.sendGetRequest("/projects", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YTRiNTMxZDg3YzA0YzA4ZTQ5Yjc4MDciLCJpYXQiOjE1MTU2NzY1NTMsImV4cCI6MTUxNTY4MDE1M30._DFGf47UkwSZeaEK4ojp18tOvGZNk8GERcd715VFR-w");
            System.out.println(res.getResponse());
            JSONObject json = new JSONObject(res.getResponse().replace("\"","\""));
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
