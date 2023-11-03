package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
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

    public String retrievedage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String name = u.getName();

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", name);
            }
        });
        btn_acc_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "DeleteAccount.fxml", "So Long :(", null);
            }
        });

        try{
            Connection connection = connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT age FROM user_info WHERE username = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                retrievedage = resultSet.getString("age");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        label_pf_name.setText("Name: " + name);
        label_pf_age.setText("Age: " + retrievedage);

    }
    public static Connection connectDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}