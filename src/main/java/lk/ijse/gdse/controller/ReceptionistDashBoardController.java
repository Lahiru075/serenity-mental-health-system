package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReceptionistDashBoardController {

    @FXML
    private Button btnCredentialManage;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPatientManage;

    @FXML
    private Button btnPaymentInvoiceManage;

    @FXML
    private Button btnReportingAnalytics;

    @FXML
    private Button btnTherapySessionScheduling;

    @FXML
    private Label lblAvailableTherapists;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblDashboardTitle;

    @FXML
    private Label lblPendingPayments;

    @FXML
    private Label lblReceptionistName;

    @FXML
    private Label lblTodaysAppointments;

    @FXML
    private Label lblTodaysNewPatients;

    @FXML
    private Label lblTotalPatients;

    @FXML
    private Label lblTotalSessions;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane subAnchorPaneOne;

    @FXML
    void btnCredentialManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/changeCredential.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) mainAnchorPane.getScene().getWindow();
        currentStage.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Login Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnPatientManageOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentInvoiceManageOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportingAnalyticsOnAction(ActionEvent event) {

    }

    @FXML
    void btnTherapySessionSchedulingOnAction(ActionEvent event) {

    }

    private void navigateToAnchorPane(String path) throws IOException {
        subAnchorPaneOne.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
        subAnchorPaneOne.getChildren().add(anchorPane);
    }

}
