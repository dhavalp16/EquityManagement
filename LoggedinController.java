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
import java.sql.*;
import java.util.ResourceBundle;

public class LoggedinController implements Initializable {

    @FXML
    private Button btn_home;
    @FXML
    private Button btn_calculator;
    @FXML
    private Button btn_equity;
    @FXML
    private Button btn_logout;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_update;
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
    private TextField tf_equityname;
    @FXML
    private TextField tf_amount;
    @FXML
    private ComboBox comb;

    public String s;

    @FXML
    void Select(ActionEvent event) {
        s = comb.getSelectionModel().getSelectedItem().toString();
    }

    ObservableList<User> listM;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Logout.fxml", "Logout", null);
            }
        });
        btn_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", c);
            }
        });
        btn_calculator.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "calculator.fxml", "Calculator", null);
            }
        });
        btn_equity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Profile.fxml", "Profile", null);
            }
        });

        btn_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.addInfo(c, tf_equityname.getText(), Integer.parseInt(tf_amount.getText()), s);
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", c);
            }
        });

        btn_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.delInfo(c, tf_equityname.getText(), Integer.parseInt(tf_amount.getText()), s);
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", c);
            }
        });

        ObservableList<String> list = FXCollections.observableArrayList("Stocks", "Mutual Funds", "Real Estate", "CryptoCurrency", "Other");
        comb.setItems(list);

        name.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        amount.setCellValueFactory(new PropertyValueFactory<User, Integer>("Amount"));
        category.setCellValueFactory(new PropertyValueFactory<User, String>("category"));

    }

    public String c;

    public void setUserInformation(String username) {
        c = username;
        label_welcome.setText("Welcome back " + username + "!");
        listM = getDatausers();
        table.setItems(listM);
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

    public ObservableList<User> getDatausers() {

        Connection connection = connectDB();
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM equity WHERE username = ?");
            preparedStatement.setString(1, c);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("equity"), Integer.parseInt(resultSet.getString("amount")), resultSet.getString("category")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
