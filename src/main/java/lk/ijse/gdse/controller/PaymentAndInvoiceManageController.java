package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.PaymentAndInvoiceManageBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.dto.tm.PatientTm;
import lk.ijse.gdse.dto.tm.PaymentTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentAndInvoiceManageController implements Initializable {

    @FXML
    private Button btnGenerateInvoice;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> cmbSessionId;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<PaymentTm, Double> colAmount;

    @FXML
    private TableColumn<PaymentTm, Date> colDate;

    @FXML
    private TableColumn<PaymentTm, String> colPatientId;

    @FXML
    private TableColumn<PaymentTm, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTm, String> colProgramId;

    @FXML
    private TableColumn<PaymentTm, String> colSessionId;

    @FXML
    private TableColumn<PaymentTm, String> colStatus;

    @FXML
    private Label lblCurrentStatus;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblFullPayment;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblProgramName;

    @FXML
    private Label lblSessionId;

    @FXML
    private TableView<PaymentTm> tblPayments;

    @FXML
    private TextField txtAmount;

    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    PaymentAndInvoiceManageBo paymentAndInvoiceManageBo = BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @FXML
    void btnGenerateInvoiceOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        String paymentId = lblPaymentId.getText();
        String patientId = cmbPatientId.getValue();
        String therapyProgramId = cmbProgramId.getValue();
        String therapySessionId = cmbSessionId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        double currentPayment = Double.parseDouble(lblCurrentStatus.getText());
        String status = cmbStatus.getValue();
        Date date = Date.valueOf(LocalDate.now());

        if (paymentId.isEmpty() || patientId.isEmpty() || therapyProgramId.isEmpty() || therapySessionId.isEmpty() || txtAmount.getText().isEmpty() || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String amountPattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidAmount = txtAmount.getText().matches(amountPattern);

        if (!isValidAmount) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount").showAndWait();
            return;
        }

        boolean isSaved = paymentAndInvoiceManageBo.save(paymentId, patientId, therapyProgramId, therapySessionId, amount, status, currentPayment, date);

        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment saved").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Payment not saved").showAndWait();
        }

    }

    @FXML
    void tblPaymentsOnMouseClicked(MouseEvent event) {
        PaymentTm paymentTm = tblPayments.getSelectionModel().getSelectedItem();

        lblPaymentId.setText(paymentTm.getId());
        lblPatientName.setText(paymentTm.getPatientId());
        lblProgramName.setText(paymentTm.getTherapyProgramId());
        lblSessionId.setText(paymentTm.getTherapySessionId());
        lblDate.setText(String.valueOf(paymentTm.getDate()));
        lblFullPayment.setText(String.valueOf(paymentTm.getAmount()));
        lblCurrentStatus.setText(paymentTm.getStatus());

        btnSave.setDisable(true);
    }

    @FXML
    void cmbPatientIdOnAction(ActionEvent event) throws SQLException {
        String id = cmbPatientId.getValue();

        if (id == null || id.isEmpty()) {
            return;
        }

        PatientDto patientDto = patientBo.findById(id);

        lblPatientName.setText(patientDto.getName());

        cmbProgramId.setValue(null);
        lblProgramName.setText("");
        lblFullPayment.setText("");
        lblCurrentStatus.setText("");
        cmbSessionId.setValue(null);

        loadTherapyProgramsIds(); // load therapy programs
    }

    @FXML
    void cmbProgramIdOnAction(ActionEvent event) throws SQLException {
        String programId = cmbProgramId.getValue();

        if (programId == null || programId.isEmpty()) {
            return;
        }

        TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(programId);

        lblProgramName.setText(therapyProgramDto.getName());
        lblFullPayment.setText(String.valueOf(therapyProgramDto.getFee())); // set full payment

        String patientId = cmbPatientId.getValue();

        String currentPaymentStatus = paymentAndInvoiceManageBo.findCurrentStatus(patientId, programId); // get current payment status
        lblCurrentStatus.setText(currentPaymentStatus);

        loadTherapySessionIds(); // load therapy sessions
    }

    private void loadPatientIds() throws SQLException {
        ArrayList<PatientDto> patientTms = patientBo.getAll();

        ArrayList<String> patientIds = new ArrayList<>();

        for (PatientDto patientDto : patientTms) {
            patientIds.add(patientDto.getId());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(patientIds);
        cmbPatientId.setItems(therapists);

    }

    private void loadTherapyProgramsIds() throws SQLException {
        String patientId = cmbPatientId.getValue();

        ArrayList<TherapyProgramDto> therapyProgramDtos = paymentAndInvoiceManageBo.findProgramsByPatientId(patientId);

        ArrayList<String> thrapyProgramIds = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            thrapyProgramIds.add(therapyProgramDto.getId());
        }

        ObservableList<String> therapyProgramsId = FXCollections.observableArrayList(thrapyProgramIds);
        cmbProgramId.setItems(therapyProgramsId);
    }

    private void loadTherapySessionIds() throws SQLException {
        String patientId = cmbPatientId.getValue();
        String programId = cmbProgramId.getValue();

        ArrayList<TherapySessionDto> therapySessionDtos = paymentAndInvoiceManageBo.findSessionsByProgramId(programId, patientId);

        ArrayList<String> therapySessionIds = new ArrayList<>();

        for (TherapySessionDto therapySessionDto : therapySessionDtos) {
            therapySessionIds.add(therapySessionDto.getId());
        }

        ObservableList<String> therapySessionId = FXCollections.observableArrayList(therapySessionIds);
        cmbSessionId.setItems(therapySessionId);
    }

    private void loadStatuses(){
        ObservableList<String> statuses = FXCollections.observableArrayList("Completed", "Pending");
        cmbStatus.setItems(statuses);
    }

    private void reset() throws SQLException {
        String id = paymentAndInvoiceManageBo.getNextId();
        lblPaymentId.setText(id);

        lblPatientName.setText("");
        lblProgramName.setText("");
        lblDate.setText("");
        lblFullPayment.setText("");
        lblCurrentStatus.setText("");
        txtAmount.setText("");
        cmbPatientId.setValue(null);
        cmbStatus.setValue(null);
        cmbProgramId.getItems().clear();
        cmbSessionId.getItems().clear();

        btnSave.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("therapySessionId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId"));

        lblDate.setText(LocalDate.now().toString());

        try {
            loadPatientIds();
            reset();
            loadStatuses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
