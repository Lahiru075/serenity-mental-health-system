package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.entity.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReceptionistDashBoardController implements Initializable {

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

    User user = new User();

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
    void btnPatientManageOnAction(ActionEvent event) throws IOException {

        user.setRole("Receptionist");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patientManage.fxml"));
        Parent root = loader.load();

        PatientManageController patientManageController = loader.getController();
        patientManageController.setPatientManageController(this);

        subAnchorPaneOne.getChildren().clear();
        subAnchorPaneOne.getChildren().add(root);

    }

    @FXML
    void btnPaymentInvoiceManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/paymentAndInvoiceManage.fxml");
    }

    @FXML
    void btnReportingAnalyticsOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/financialReport .fxml");
    }

    @FXML
    void btnTherapySessionSchedulingOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/sessionManage.fxml");
    }

    private void navigateToAnchorPane(String path) throws IOException {
        subAnchorPaneOne.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
        subAnchorPaneOne.getChildren().add(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCurrentDate.setText(LocalDate.now().toString());
    }
}
