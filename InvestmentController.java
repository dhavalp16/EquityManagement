package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;


public class InvestmentController implements Initializable {

    @FXML
    private Button button_inv_back;
    @FXML
    private Button button_delete;
    @FXML
    private Button button_save;
    @FXML
    private ChoiceBox<String> choicebox_category;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_inv_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Loggedin.fxml","Profile",null);
            }
        });
    }
}
