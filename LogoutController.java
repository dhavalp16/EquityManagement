package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LogoutController implements Initializable {

    UserHolder holder = UserHolder.getInstance();
    User u = holder.getUser();
    String name = u.getName();
    @FXML
    private Button btn_log_out;
    @FXML
    private Button btn_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_log_out.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Login.fxml", "Login", null);
            }
        });
        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", name);
            }
        });
    }
}