package com.example.demo;

import com.example.demo.LoggedinController.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Button btn_back;
    @FXML
    private Label label_pf_name;
    @FXML
    private Label label_pf_age;
    @FXML
    private Button btn_acc_delete;

    public String y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String name = u.getName();

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Loggedin.fxml","Welcome!",name);
            }
        });
        btn_acc_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.delAcc(y);
            }
        });
    }

}