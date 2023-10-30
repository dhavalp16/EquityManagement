package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccount implements Initializable {

    @FXML
    private Button btn_del_acc;
    @FXML
    private Button btn_no;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String name = u.getName();

        btn_no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Profile.fxml","Welcome!",null);
            }
        });
        btn_del_acc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.delAcc(name);
                DBUtils.changeScene(event, "Login.fxml", "Welcome!", null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Account Deleted Successfully!");
                alert.show();
            }
        });

    }
}
