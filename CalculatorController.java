package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private TextField tf_ci_pamount;

    @FXML
    private TextField tf_ci_time;

    @FXML
    private TextField tf_ci_irate;

    @FXML
    private Button btn_ci_calculate;

    @FXML
    private Label label_ci_answer;

    @FXML
    private TextField tf_cagr_pamount;

    @FXML
    private TextField tf_cagr_famount;

    @FXML
    private TextField tf_cagr_time;

    @FXML
    private Button btn_cagr_calculate;

    @FXML
    private Label label_cagr_answer;

    @FXML
    private TextField tf_mc_eprice;

    @FXML
    private TextField tf_mc_dpayment;

    @FXML
    private TextField tf_mc_lterm;

    @FXML
    private TextField tf_mc_irate;

    @FXML
    private Button btn_mc_calculate;

    @FXML
    private Label label_mc_lamount;

    @FXML
    private Label label_mc_ptenure;

    @FXML
    private Label label_mc_dpayment;

    @FXML
    private Label label_mc_mpayment;

    @FXML
    private  Button btn_calculator_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final DecimalFormat df = new DecimalFormat("0.0000");

        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String name = u.getName();

        btn_calculator_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Loggedin.fxml","Home",name);
            }
        });

        btn_ci_calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double a= DBUtils.calculateCompoundInterest(Double.parseDouble(tf_ci_pamount.getText()),Double.parseDouble(tf_ci_time.getText()),Double.parseDouble(tf_ci_irate.getText()));
                label_ci_answer.setText(" " + (df.format(a)));
            }
        });

        btn_cagr_calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double b = DBUtils.calculateCAGR(Double.parseDouble(tf_cagr_pamount.getText()),Double.parseDouble(tf_cagr_famount.getText()),Double.parseDouble(tf_cagr_time.getText()));
                label_cagr_answer.setText(" " + (df.format(b)));
            }
        });

        btn_mc_calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double c = DBUtils.calculateMortgage(Double.parseDouble(tf_mc_eprice.getText()),Double.parseDouble(tf_mc_dpayment.getText()),Double.parseDouble(tf_mc_lterm.getText()),Double.parseDouble(tf_mc_irate.getText()));
                label_mc_mpayment.setText(" " + (df.format(c)));
                double d = DBUtils.calculateLoanAmount(Double.parseDouble(tf_mc_eprice.getText()),Double.parseDouble(tf_mc_dpayment.getText()));
                label_mc_lamount.setText(" " + (df.format(d)));
                double e = DBUtils.calculateDownPayment(Double.parseDouble(tf_mc_eprice.getText()),Double.parseDouble(tf_mc_dpayment.getText()));
                label_mc_dpayment.setText(" " + (df.format(e)));
                double f = DBUtils.calculatePaymentTenure(Double.parseDouble(tf_mc_lterm.getText()));
                label_mc_ptenure.setText(" " + (df.format(f)));
            }
        });

    }


}