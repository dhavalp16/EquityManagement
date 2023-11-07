package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private Label label_total_investments;

    @FXML
    private Label label_total_capital;

    @FXML
    private Label label_category_highest;

    @FXML
    private Label label_category_lowest;

    @FXML
    private Label label_amt_highest;

    @FXML
    private Label label_amt_lowest;

    @FXML
    private Label label_lhighest_amount;

    @FXML
    private Label label_lowest_amount;
    @FXML
    private Button btn_back;
    @FXML
    private PieChart pie_chart;

    @FXML
    private BarChart<String, Double> bar_chart;

    @FXML
    private CategoryAxis bar_xAxis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String jdbcUrl = "jdbc:mysql://localhost:3306/equiman";
        String username = "root";
        String password = "equity";

        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String name = u.getName();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String username1 = name;

            // Fetch equity data for the logged-in user
            String equityQuery = "SELECT category, SUM(amount) AS total_amount FROM equity WHERE username = ? GROUP BY category";
            PreparedStatement preparedStatement = connection.prepareStatement(equityQuery);
            preparedStatement.setString(1, username1);
            ResultSet resultSet = preparedStatement.executeQuery();

            pie_chart.getData().clear();
            XYChart.Series<String, Double> series = new XYChart.Series<>();

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                double totalAmount = resultSet.getDouble("total_amount");

                PieChart.Data slice = new PieChart.Data(category, totalAmount);
                pie_chart.getData().add(slice);


                series.getData().add(new XYChart.Data<>(category, totalAmount));
            }

            bar_chart.getData().add(series);

            updateLabels(connection, username1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Loggedin.fxml", "Home", name);
            }
        });
    }

    private void updateLabels(Connection connection, String username) throws SQLException {

        String query1 = "SELECT COUNT(*) AS total_investments FROM equity WHERE username = ?";
        String query2 = "SELECT SUM(amount) AS total_investment FROM equity WHERE username = ?";
        String query3 = "SELECT category FROM equity WHERE amount = (SELECT MAX(amount) FROM equity WHERE username = ?)";
        String query4 = "SELECT category FROM equity WHERE amount = (SELECT MIN(amount) FROM equity WHERE username = ?)";
        String query5 = "SELECT equity FROM equity WHERE amount = (SELECT MAX(amount) FROM equity WHERE username = ?)";
        String query6 = "SELECT equity FROM equity WHERE amount = (SELECT MIN(amount) FROM equity WHERE username = ?)";
        String query7 = "SELECT MAX(amount) AS highest_amount FROM equity WHERE username = ?";
        String query8 = "SELECT MIN(amount) AS lowest_amount FROM equity WHERE username = ?";

        try (PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
             PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
             PreparedStatement preparedStatement4 = connection.prepareStatement(query4);
             PreparedStatement preparedStatement5 = connection.prepareStatement(query5);
             PreparedStatement preparedStatement6 = connection.prepareStatement(query6);
             PreparedStatement preparedStatement7 = connection.prepareStatement(query7);
             PreparedStatement preparedStatement8 = connection.prepareStatement(query8)) {

            preparedStatement1.setString(1, username);
            preparedStatement2.setString(1, username);
            preparedStatement3.setString(1, username);
            preparedStatement4.setString(1, username);
            preparedStatement5.setString(1, username);
            preparedStatement6.setString(1, username);
            preparedStatement7.setString(1, username);
            preparedStatement8.setString(1, username);

            ResultSet result1 = preparedStatement1.executeQuery();
            ResultSet result2 = preparedStatement2.executeQuery();
            ResultSet result3 = preparedStatement3.executeQuery();
            ResultSet result4 = preparedStatement4.executeQuery();
            ResultSet result5 = preparedStatement5.executeQuery();
            ResultSet result6 = preparedStatement6.executeQuery();
            ResultSet result7 = preparedStatement7.executeQuery();
            ResultSet result8 = preparedStatement8.executeQuery();

            if (result1.next()) {
                int totalInvestments = result1.getInt("total_investments");
                label_total_investments.setText("Total number of investments: " + totalInvestments);
            }

            if (result2.next()) {
                double totalInvestment = result2.getDouble("total_investment");
                label_total_capital.setText("Total capital invested: " + totalInvestment);
            }

            if (result3.next()) {
                String categoryHighest = result3.getString("category");
                label_category_highest.setText("Category with the highest capital: " + categoryHighest);
            }

            if (result4.next()) {
                String categoryLowest = result4.getString("category");
                label_category_lowest.setText("Category with the lowest capital: " + categoryLowest);
            }

            if (result5.next()) {
                String equityHighestAmount = result5.getString("equity");
                label_amt_highest.setText("Equity with the highest amount: " + equityHighestAmount);
            }

            if (result6.next()) {
                String equityLowestAmount = result6.getString("equity");
                label_amt_lowest.setText("Equity with the lowest amount: " + equityLowestAmount);
            }

            if (result7.next()) {
                double highestAmount = result7.getDouble("highest_amount");
                label_lhighest_amount.setText("Highest amount invested: " + highestAmount);
            }

            if (result8.next()) {
                double lowestAmount = result8.getDouble("lowest_amount");
                label_lowest_amount.setText("Lowest amount invested: " + lowestAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
