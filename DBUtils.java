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

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedinController loggedinController = loader.getController();
                loggedinController.setUserInformation(username);

                UserHolder holder = UserHolder.getInstance();
                User user = new User(username);
                user.setName(username);
                holder.setUser(user);

                // Step 4
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password, int age) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user_info WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username. User already exists!");
                alert.show();
            }else {
                boolean hasUppercase = false;
                boolean hasLowercase = false;
                boolean hasSpecial = false;

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        hasUppercase = true;
                    } else if (c >= 'a' && c <= 'z') {
                        hasLowercase = true;
                    } else if ((c >= '!' && c <= '/') || (c >= ':' && c <= '@') || (c >= '[' && c <= '`') || (c >= '{' && c <= '~')) {
                        hasSpecial = true;
                    }
                }
                if (password.length() < 8 || !hasUppercase || !hasLowercase || !hasSpecial) {
                    System.out.println("Password must be at least 8 characters long with at least one uppercase letter, one lowercase letter, and one special character!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Password must be at least 8 characters long with at least one uppercase letter, one lowercase letter, and one special character!");
                    alert.show();
                }else {
                psInsert = connection.prepareStatement("INSERT INTO user_info(username, password, age) VALUES (? , ? , ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setInt(3, age);
                psInsert.executeUpdate();

                changeScene(event, "Loggedin.fxml", "Welcome!", username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            preparedStatement = connection.prepareStatement("SELECT password FROM user_info WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User not found in the database!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "Loggedin.fxml", "Welcome!", username);
                    } else {
                        System.out.println("Incorrect password!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect password!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void addInfo(String username, String equity, int amount, String category) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");

            preparedStatement = connection.prepareStatement("SELECT * FROM equity WHERE username = ? AND equity = ? AND amount = ? AND category = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, equity);
            preparedStatement.setInt(3, amount);
            preparedStatement.setString(4, category);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO equity(username, equity, amount, category) VALUES (? , ? , ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, equity);
            preparedStatement.setInt(3, amount);
            preparedStatement.setString(4, category);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void delInfo(String username, String equity, int amount, String category) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            preparedStatement = connection.prepareStatement("DELETE FROM equity WHERE username = ? AND equity = ? AND amount = ? AND category = ? LIMIT 1");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, equity);
            preparedStatement.setInt(3, amount);
            preparedStatement.setString(4, category);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean delAcc(String confirmUsername, String password, String username) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        boolean isDeleted = false;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
            preparedStatement = connection.prepareStatement("SELECT password FROM user_info WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String retrievedPassword = resultSet.getString("password");
                if (retrievedPassword.equals(password) && confirmUsername.equals(username)) {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiman", "root", "equity");
                    preparedStatement = connection.prepareStatement("DELETE FROM user_info WHERE username = ?");
                    preparedStatement1 = connection.prepareStatement("DELETE FROM equity WHERE username = ?");
                    preparedStatement.setString(1, username);
                    preparedStatement1.setString(1, username);

                    preparedStatement.executeUpdate();
                    preparedStatement1.executeUpdate();
                    isDeleted = true;
                } else if (!confirmUsername.equals(username) && !retrievedPassword.equals(password)) {
                    System.out.println("Incorrect Username & Password!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect Username & Password!");
                    alert.show();
                } else if (!confirmUsername.equals(username)) {
                    System.out.println("Incorrect Username!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect Username!");
                    alert.show();
                } else {
                    System.out.println("Incorrect password!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect password!");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement1 != null) {
                try {
                    preparedStatement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isDeleted;
    }
}



