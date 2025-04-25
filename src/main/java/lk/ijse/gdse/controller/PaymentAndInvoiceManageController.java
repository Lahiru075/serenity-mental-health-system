package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.PaymentAndInvoiceManageBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.bo.exception.PaymentProcessingException;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.dto.tm.PatientTm;
import lk.ijse.gdse.dto.tm.PaymentTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentAndInvoiceManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerateInvoice;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

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
    private TableView<PaymentTm> tblPayments;

    @FXML
    private TextField txtAmount;

    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    PaymentAndInvoiceManageBo paymentAndInvoiceManageBo = BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    private InvoiceDto invoiceDto = new InvoiceDto();

    public InvoiceDto getInvoiceDto(){
        return this.invoiceDto;
    }

    private void setInvoiceDto(String patientId, String patientName, String programName, String sessionId, Date invoiceDate, double fee){
        TherapySessionDto therapySessionDto = therapySessionBo.findById(cmbSessionId.getValue());

        this.invoiceDto.setPatientId(patientId);
        this.invoiceDto.setPatientName(patientName);
        this.invoiceDto.setProgramName(programName);
        this.invoiceDto.setSessionId(sessionId);
        this.invoiceDto.setSessionDate(therapySessionDto.getDate());
        this.invoiceDto.setInvoiceDate(invoiceDate);
        this.invoiceDto.setFee(fee);
        this.invoiceDto.setStatus("PAID");
    }

    @FXML
    void btnGenerateInvoiceOnAction(ActionEvent event) throws IOException {
//        Parent load =  FXMLLoader.load(getClass().getResource("/view/invoice.fxml"));
//        Scene scene = new Scene(load);
//
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Invoice Form");
//        stage.setResizable(false);
//        stage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/invoice.fxml"));
        Parent root = loader.load();

        InvoiceController invoiceController = loader.getController();
        invoiceController.setInvoiceController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Invoice");
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        String paymentId = lblPaymentId.getText();
        String patientId = cmbPatientId.getValue();
        String therapyProgramId = cmbProgramId.getValue();
        String therapySessionId = cmbSessionId.getValue();
        String status = cmbStatus.getValue();
        String stringAmount = txtAmount.getText();
        String stringCurrentPayment = lblCurrentStatus.getText();
        Date date = Date.valueOf(LocalDate.now());

        if (paymentId.isEmpty() || cmbPatientId.getValue() == null || cmbProgramId.getValue() == null || cmbSessionId.getValue() == null || cmbStatus.getValue() == null || patientId.isEmpty() || therapyProgramId.isEmpty() || therapySessionId.isEmpty() || txtAmount.getText().isEmpty() || status.isEmpty() || stringCurrentPayment.isEmpty() || stringAmount.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String amountPattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidAmount = txtAmount.getText().matches(amountPattern);

        if (!isValidAmount) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount").showAndWait();
            return;
        }

        double amount = Double.parseDouble(stringAmount);
        double currentPayment = Double.parseDouble(stringCurrentPayment);

        try {
            boolean isSaved = paymentAndInvoiceManageBo.save(paymentId, patientId, therapyProgramId, therapySessionId, amount, status, currentPayment, date);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment saved").showAndWait();

                String patientName = lblPatientName.getText();
                String programName = lblProgramName.getText();
                String sessionId = cmbSessionId.getValue();
                Date invoiceDate = date;
                double fee = Double.parseDouble(txtAmount.getText());

                setInvoiceDto(patientId, patientName, programName, sessionId, invoiceDate, fee);
                reset();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment not saved").showAndWait();
            }

        }catch (PaymentProcessingException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = lblPaymentId.getText();
        String newAmount = txtAmount.getText();

        if (newAmount.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill amount field").showAndWait();
            return;
        }

        String amountPattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidAmount = txtAmount.getText().matches(amountPattern);

        if (!isValidAmount) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount").showAndWait();
            return;
        }


        try {
            boolean isUpdated = paymentAndInvoiceManageBo.update(id, newAmount);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment updated").showAndWait();

                PatientDto patientDto = patientBo.findByName(lblPatientName.getText());
                TherapyProgramDto therapyProgramDto = therapyProgramBo.findByName(lblProgramName.getText());

                String patientId = patientDto.getId();
                String patientName = patientDto.getName();
                String programName = therapyProgramDto.getName();
                String sessionId = cmbSessionId.getValue();
                Date invoiceDate = Date.valueOf(LocalDate.now());
                double fee = Double.parseDouble(txtAmount.getText());

                setInvoiceDto(patientId, patientName, programName, sessionId, invoiceDate, fee);
                reset();
            }else {
                new Alert(Alert.AlertType.ERROR, "Payment not updated").showAndWait();
            }
        } catch (PaymentProcessingException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }



    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = lblPaymentId.getText();

        boolean isDeleted = paymentAndInvoiceManageBo.delete(id);

        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Payment deleted").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Payment not deleted").showAndWait();
        }

    }

    @FXML
    void tblPaymentsOnMouseClicked(MouseEvent event) {
        PaymentTm paymentTm = tblPayments.getSelectionModel().getSelectedItem();

        if (paymentTm != null) {
            lblPaymentId.setText(paymentTm.getId());
            cmbSessionId.setValue(paymentTm.getTherapySessionId());
            txtAmount.setText(String.valueOf(paymentTm.getAmount()));

            String patientId = paymentTm.getPatientId();
            PatientDto patientDto = patientBo.findById(patientId);

            String programId = paymentTm.getTherapyProgramId();
            TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(programId);

            lblPatientName.setText(patientDto.getName());
            lblProgramName.setText(therapyProgramDto.getName());

            String patientName = patientDto.getName();
            String programName = therapyProgramDto.getName();
            String sessionId = cmbSessionId.getValue();
            Date invoiceDate = paymentTm.getDate();
            double fee = Double.parseDouble(txtAmount.getText());

            setInvoiceDto(patientId, patientName, programName, sessionId, invoiceDate, fee);
        }

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
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

    private void loadStatuses() {
        ObservableList<String> statuses = FXCollections.observableArrayList("Completed", "Pending");
        cmbStatus.setItems(statuses);
    }

    private void reset() throws SQLException {
        String id = paymentAndInvoiceManageBo.getNextId();
        lblPaymentId.setText(id);

        getAllPayments();

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
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void getAllPayments() throws SQLException {
        ArrayList<PaymentDto> payments = paymentAndInvoiceManageBo.getAll();

        ObservableList<PaymentTm> paymentTms = FXCollections.observableArrayList();

        for (PaymentDto paymentDto : payments) {
            PaymentTm paymentTm = new PaymentTm();
            paymentTm.setId(paymentDto.getId());
            paymentTm.setAmount(paymentDto.getAmount());
            paymentTm.setStatus(paymentDto.getStatus());
            paymentTm.setDate(paymentDto.getDate());
            paymentTm.setPatientId(paymentDto.getPatientId());
            paymentTm.setTherapySessionId(paymentDto.getTherapySessionId());
            paymentTm.setTherapyProgramId(paymentDto.getTherapyProgramId());

            paymentTms.add(paymentTm);
        }

        tblPayments.setItems(paymentTms);
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

        try {
            loadPatientIds();
            reset();
            loadStatuses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblDate.setText(LocalDate.now().toString());

    }
}
