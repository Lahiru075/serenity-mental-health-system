package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashBoardController {

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPatientManage;

    @FXML
    private Button btnPaymentInvoiceManage;

    @FXML
    private Button btnReportingAnalytics;

    @FXML
    private Button btnTherapistManage;

    @FXML
    private Button btnTherapistProgramManage;

    @FXML
    private Button btnTherapySessionScheduling;

    @FXML
    private Button btnUserManage;

    @FXML
    private Label lblAdminName;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblDashboardTitle;

    @FXML
    private Label lblTodaySessions;

    @FXML
    private Label lblTotalPatients;

    @FXML
    private Label lblTotalPrograms;

    @FXML
    private Label lblTotalRevenue;

    @FXML
    private Label lblTotalSessions;

    @FXML
    private Label lblTotalTherapists;

    @FXML
    private Label lblTotalUsers;

    @FXML
    private AnchorPane subAnchorPaneOne;

    @FXML
    private AnchorPane mainAnchorPane;

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
    void btnPatientManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/patientManage.fxml");
    }

    @FXML
    void btnPaymentInvoiceManageOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportingAnalyticsOnAction(ActionEvent event) {

    }

    @FXML
    void btnTherapySessionSchedulingOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/sessionManage.fxml");
    }

    @FXML
    void btnTherapistManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/therapistManage.fxml");
    }

    @FXML
    void btnTherapistProgramManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/programManage.fxml");
    }

    @FXML
    void btnUserManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/adminUserManage.fxml");
    }

    private void navigateToAnchorPane(String path) throws IOException {
        subAnchorPaneOne.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
        subAnchorPaneOne.getChildren().add(anchorPane);
    }

}
