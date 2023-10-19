package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoggedinController implements Initializable {

    @FXML
    private Button button_home;
    @FXML
    private Button button_calculator;
    @FXML
    private Button button_equity;
    @FXML
    private Button button_logout;
    @FXML
    private Button button_add;
    @FXML
    private Button button_delete;
    @FXML
    private Button button_update;
    @FXML
    private Label label_welcome;
    @FXML
    private TableView<User> tb_table;
    @FXML
    private TableColumn<User, Integer> tb_amount;
    @FXML
    private TableColumn<User, String> tb_name;
    @FXML
    private TableColumn<User, String> tb_category;
    @FXML
    private TextField tf_equityname;
    @FXML
    private TextField tf_amount;
    @FXML
    private ComboBox comb;

    public String s;
    @FXML
    void Select(ActionEvent event){
         s = comb.getSelectionModel().getSelectedItem().toString();
    }


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
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", c);
            }
        });
        button_calculator.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"calculator.fxml","Calculator",null);
            }
        });
        button_equity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Profile.fxml","Profile",null);
            }
        });

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.addInfo(c, tf_equityname.getText() ,Integer.parseInt(tf_amount.getText()),s);
            }
        });

        button_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.delInfo(c, tf_equityname.getText() ,Integer.parseInt(tf_amount.getText()),s);
            }
        });

        ObservableList<String> list = FXCollections.observableArrayList("Stocks", "Mutual Funds", "Real Estate", "CryptoCurrency", "Other");
        comb.setItems(list);


    }
    public String c;
    public void setUserInformation(String username){
        c = username;
        label_welcome.setText("Welcome back " + username + "!");
    }

}