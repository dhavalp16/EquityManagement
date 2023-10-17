package com.example.demo;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Button button_reload;
    @FXML
    private Label label_welcome;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> amount;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> category;
    @FXML
    private TableColumn<User, Integer> num;

    ObservableList<User> ListM;
    int index = -1;

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

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

                DBUtils.changeScene1(event, "InvestmentEntry.fxml", "Home", null);
            }
        });

        amount.setCellValueFactory((new PropertyValueFactory<User, Integer>("amount")));
        name.setCellValueFactory((new PropertyValueFactory<User, String>("name")));
        category.setCellValueFactory((new PropertyValueFactory<User, String>("category")));

        ListM = DBUtils.getDataUser();
        table.setItems(ListM);

    }
    public void setUserInformation(String username){

        label_welcome.setText("Welcome " + username + "!");
    }
}