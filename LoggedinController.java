package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedinController implements Initializable {

    @FXML
    private Button button_home;
    @FXML
    private Button button_calculator;
    @FXML
    private Button button_account;
    @FXML
    private Button button_logout;
    @FXML
    private Button button_add;
    @FXML
    private Button button_reload;
    @FXML
    private Label label_welcome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Logout.fxml", "Logout", null);
            }
        });
        button_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", null);
            }
        });
    }
    public void setUserInformation(String username){
        label_welcome.setText("Welcome " + username + "!");
    }
}
