package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button btn_log_in;
    @FXML
    private Button btn_sign_up;
    @FXML
    private TextField tf_user_name;
    @FXML
    private TextField tf_pass_word;
    @FXML
    private TextField tf_age;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(tf_user_name.getText().trim().isEmpty() && tf_pass_word.getText().trim().isEmpty() && tf_age.getText().trim().isEmpty()){
                    System.out.println("Please fill all information to SignUp!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all information to SignUp!");
                    alert.show();
                } else if(tf_user_name.getText().trim().isEmpty()) {
                    System.out.println("Please create a username to SignUp!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please create a username to SignUp!");
                    alert.show();
                } else if(tf_pass_word.getText().trim().isEmpty()){
                    System.out.println("Please create a password to SignUp!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please create a password to SignUp!");
                    alert.show();
                } else if(tf_age.getText().trim().isEmpty()){
                    System.out.println("Please enter an age to SignUp!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter an age to SignUp!");
                    alert.show();
                } else {
                    DBUtils.signUpUser(event, tf_user_name.getText(), tf_pass_word.getText(), Integer.parseInt(tf_age.getText()));
                }
            }
        });
        btn_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Login.fxml", "Login", null);
            }
        });
    }
}