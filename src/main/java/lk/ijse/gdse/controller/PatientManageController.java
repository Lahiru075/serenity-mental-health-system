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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.tm.PatientTm;
import lk.ijse.gdse.dto.tm.TherapistTm;
import lk.ijse.gdse.entity.Patient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class PatientManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colPatientId;

    @FXML
    private TableColumn<PatientTm, Date> colRegDate;

    @FXML
    private TableColumn<PatientTm, String> colMedicalHistory;

    @FXML
    private DatePicker dpRegisterDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private TableView<PatientTm> tblPatients;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtMedicalHistory;

    @FXML
    private TextField txtName;

    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = lblPatientId.getText();

        boolean isDeleted = patientBo.delete(id);

        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Patient deleted successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete patient").showAndWait();
        }
    }



    @FXML
    void btnFilterPatientOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/filterPatient.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Filter Patients Form");
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void btnAssignProgramOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/assignProgram.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Assign Program Form");
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    void btnViewPatientProgramsOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/patientTherapyProgram.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Patient's Program Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = lblPatientId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String medicalHistory = txtMedicalHistory.getText();
        Date registerDate = Date.valueOf(dpRegisterDate.getValue());

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty() || medicalHistory.isEmpty() || dpRegisterDate.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidDescription = medicalHistory.matches(descriptionPattern);

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR, "Invalid name").showAndWait();
            return;
        }

        if (!isValidEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid email").showAndWait();
            return;
        }

        if (!isValidContact){
            new Alert(Alert.AlertType.ERROR, "Invalid contact").showAndWait();
            return;
        }

        if (!isValidDescription){
            new Alert(Alert.AlertType.ERROR, "Invalid description").showAndWait();
            return;
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setId(id);
        patientDto.setName(name);
        patientDto.setEmail(email);
        patientDto.setContact(Integer.parseInt(contact));
        patientDto.setMedical_history(medicalHistory);
        patientDto.setRegisterDate(registerDate);

        boolean isSaved = patientBo.save(patientDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Patient saved successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save patient").showAndWait();
        }

    }


    @FXML
    void btnTherapyProgramsOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/viewPatientInAllPrograms.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Patient In All Programs Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = lblPatientId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String medicalHistory = txtMedicalHistory.getText();
        Date registerDate = Date.valueOf(dpRegisterDate.getValue());

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty() || medicalHistory.isEmpty() || dpRegisterDate.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidDescription = medicalHistory.matches(descriptionPattern);

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR, "Invalid name").showAndWait();
            return;
        }

        if (!isValidEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid email").showAndWait();
            return;
        }

        if (!isValidContact){
            new Alert(Alert.AlertType.ERROR, "Invalid contact").showAndWait();
            return;
        }

        if (!isValidDescription){
            new Alert(Alert.AlertType.ERROR, "Invalid description").showAndWait();
            return;
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setId(id);
        patientDto.setName(name);
        patientDto.setEmail(email);
        patientDto.setContact(Integer.parseInt(contact));
        patientDto.setMedical_history(medicalHistory);
        patientDto.setRegisterDate(registerDate);

        boolean isSaved = patientBo.update(patientDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Patient saved successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save patient").showAndWait();
        }
    }

    @FXML
    void tblPatientsOnMouseClicked(MouseEvent event) {
        PatientTm patientTm = tblPatients.getSelectionModel().getSelectedItem();

        lblPatientId.setText(patientTm.getId());
        txtName.setText(patientTm.getName());
        txtEmail.setText(patientTm.getEmail());
        txtContact.setText(String.valueOf(patientTm.getContact()));
        txtMedicalHistory.setText(patientTm.getMedical_history());
        dpRegisterDate.setValue(patientTm.getRegisterDate().toLocalDate());

        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    void reset() throws SQLException {
        String id = patientBo.getNextId();
        lblPatientId.setText(id);

        load();

        txtName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtMedicalHistory.clear();
        dpRegisterDate.setValue(null);

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

    void load() throws SQLException {
        ArrayList<PatientDto> patientDtos = patientBo.getAll();

        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : patientDtos){
            PatientTm patientTm = new PatientTm();
            patientTm.setId(patientDto.getId());
            patientTm.setName(patientDto.getName());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setContact(patientDto.getContact());
            patientTm.setRegisterDate(patientDto.getRegisterDate());
            patientTm.setMedical_history(patientDto.getMedical_history());

            patientTms.add(patientTm);
        }

        tblPatients.setItems(patientTms);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medical_history"));

        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
