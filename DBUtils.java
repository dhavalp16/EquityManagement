package com.example.demo;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        Parent root = null;

        if(username!=null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedinController loggedinController = loader.getController();
                loggedinController.setUserInformation(username);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, int age){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user_info WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO user_info(username, password, age) VALUES (? , ? , ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setInt(3, age);
                psInsert.executeUpdate();

                changeScene(event, "Loggedin.fxml", "Welcome!", username);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null){
                try{
                    psCheckUserExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert!=null){
                try{
                    psInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            preparedStatement = connection.prepareStatement("SELECT password FROM user_info WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "Loggedin.fxml", "Welcome!", username);
                    }else{
                        System.out.println("Incorrect password!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
