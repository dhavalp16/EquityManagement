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
                double a= calculateCompoundInterest(Double.parseDouble(tf_ci_pamount.getText()),Double.parseDouble(tf_ci_time.getText()),Double.parseDouble(tf_ci_irate.getText()));
                label_ci_answer.setText(" " + (df.format(a)));
            }
        });

        btn_cagr_calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double b = calculateCAGR(Double.parseDouble(tf_cagr_pamount.getText()),Double.parseDouble(tf_cagr_famount.getText()),Double.parseDouble(tf_cagr_time.getText()));
                label_cagr_answer.setText(" " + (df.format(b)));
            }
        });

        btn_mc_calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double c = calculateMortgage(Double.parseDouble(tf_mc_eprice.getText()),Double.parseDouble(tf_mc_dpayment.getText()),Double.parseDouble(tf_mc_lterm.getText()),Double.parseDouble(tf_mc_irate.getText()));
                label_mc_mpayment.setText(" " + (df.format(c)));
                double d = calculateLoanAmount(Double.parseDouble(tf_mc_eprice.getText()),Double.parseDouble(tf_mc_dpayment.getText()));
                label_mc_lamount.setText(" " + (df.format(d)));
                double e = calculateDownPayment(Double.parseDouble(tf_mc_eprice.getText()),Double.parseDouble(tf_mc_dpayment.getText()));
                label_mc_dpayment.setText(" " + (df.format(e)));
                double f = calculatePaymentTenure(Double.parseDouble(tf_mc_lterm.getText()));
                label_mc_ptenure.setText(" " + (df.format(f)));
            }
        });

    }

    public static double calculateCompoundInterest(Double principleAmount, Double time, Double interestRate){
        double a,b;
        double finalAnswer;

        a = interestRate/100;
        b = 1 + a;

        finalAnswer = principleAmount*Math.pow(b,time);

        return finalAnswer  ;
    }

    public static double calculateCAGR(Double principleAmount, Double finalAmount, Double time){
        double a,b,c,d;
        a = finalAmount/principleAmount;
        c = 1/time;
        b = Math.pow(a,c);
        d = b -1;

        return  d*100;
    }

    public static double calculateMortgage(Double estatePrice, Double downPaymentPercentage, Double loanTermYears, Double annualInterestRate) {
        double downPaymentAmount = (downPaymentPercentage / 100) * estatePrice;
        double principalAmount = estatePrice - downPaymentAmount;
        double monthlyInterestRate = (annualInterestRate / 100) / 12;
        double totalNumberOfMonthlyPayments = loanTermYears * 12;
        double monthlyPayment = principalAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalNumberOfMonthlyPayments)) / (Math.pow(1 + monthlyInterestRate, totalNumberOfMonthlyPayments) - 1);
        return monthlyPayment;
    }


    public static double calculateLoanAmount(Double estatePrice, Double downPayment){
        Double a,b,c;
        b = downPayment/100;
        a = estatePrice*b;
        c = estatePrice - a;

        return c;
    }

    public static double calculateDownPayment(Double estatePrice, Double downPayment){
        Double a,b;
        b = downPayment/100;
        a = estatePrice*b;
        return a;
    }

    public static double calculatePaymentTenure(Double loanTerm){
        Double a;
        a = loanTerm*12;
        return a;
    }
}