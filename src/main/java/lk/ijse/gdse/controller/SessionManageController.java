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
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;

import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.dto.tm.TherapyProgramTm;
import lk.ijse.gdse.dto.tm.TherapySessionTm;
import lk.ijse.gdse.dto.tm.UserTm;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.TherapyProgram;
import lk.ijse.gdse.entity.TherapySession;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SessionManageController implements Initializable {

    @FXML
    private Button btnBook;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnReschedule;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbSessionStatus;

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private TableColumn<TherapySessionTm, String> colPatientId;

    @FXML
    private TableColumn<TherapySessionTm, String> colProgramId;

    @FXML
    private TableColumn<TherapySessionTm, Date> colSessionDate;

    @FXML
    private TableColumn<TherapySessionTm, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionTm, String> colSessionStatus;

    @FXML
    private TableColumn<TherapySessionTm, Time> colSessionTime;

    @FXML
    private TableColumn<TherapySessionTm, String> colTherapistId;

    @FXML
    private DatePicker dpSessionDate;

    @FXML
    private Label lblSessionId;

    @FXML
    private TableView<TherapySessionTm> tblSessions;

    @FXML
    private TextField txtSessionTime;

    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    @FXML
    void btnBookOnAction(ActionEvent event) throws SQLException {
        String id = lblSessionId.getText();
        String time = txtSessionTime.getText();
        Date date = Date.valueOf(dpSessionDate.getValue());
        String status = cmbSessionStatus.getValue();
        String programIdId = cmbProgramId.getValue();
        String therapistId = cmbTherapistId.getValue();
        String patientId = cmbPatientId.getValue();

        if (id.isEmpty() || time.isEmpty() || dpSessionDate.getValue() == null || status.isEmpty() || programIdId.isEmpty() || therapistId.isEmpty() || patientId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String timePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

        boolean isValidPattern = time.matches(timePattern);

        if (!isValidPattern){
            new Alert(Alert.AlertType.ERROR, "Invalid Time").showAndWait();
            return;
        }

        Time newTime = Time.valueOf(time);

        boolean idBooked = therapySessionBo.save(new TherapySessionDto(
                id,
                newTime,
                date,
                status,
                programIdId,
                therapistId,
                patientId
        ));

        if (idBooked){
            new Alert(Alert.AlertType.INFORMATION, "Booking saved successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to save booking").showAndWait();
        }
    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) throws SQLException {
        String id = lblSessionId.getText();
        String time = txtSessionTime.getText();
        Date date = Date.valueOf(dpSessionDate.getValue());
        String status = cmbSessionStatus.getValue();
        String programIdId = cmbProgramId.getValue();
        String therapistId = cmbTherapistId.getValue();
        String patientId = cmbPatientId.getValue();

        if (id.isEmpty() || time.isEmpty() || dpSessionDate.getValue() == null || status.isEmpty() || programIdId.isEmpty() || therapistId.isEmpty() || patientId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String timePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

        boolean isValidPattern = time.matches(timePattern);

        if (!isValidPattern){
            new Alert(Alert.AlertType.ERROR, "Invalid Time").showAndWait();
            return;
        }

        Time newTime = Time.valueOf(time);

        boolean idBooked = therapySessionBo.update(new TherapySessionDto(
                id,
                newTime,
                date,
                status,
                programIdId,
                therapistId,
                patientId
        ));

        if (idBooked){
            new Alert(Alert.AlertType.INFORMATION, "Booking reschedule successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to reschedule booking").showAndWait();
        }
    }

    @FXML
    void tblSessionsOnMouseClicked(MouseEvent event) {
        TherapySessionTm therapySessionTm = tblSessions.getSelectionModel().getSelectedItem();

        if (therapySessionTm != null){
            lblSessionId.setText(therapySessionTm.getId());
            txtSessionTime.setText(String.valueOf(therapySessionTm.getTime()));
            String date = String.valueOf(therapySessionTm.getDate());
            dpSessionDate.setValue(LocalDate.parse(date));
            cmbSessionStatus.setValue(therapySessionTm.getStatus());
            cmbProgramId.setValue(therapySessionTm.getTherapyProgram());
            cmbTherapistId.setValue(therapySessionTm.getTherapists());
            cmbPatientId.setValue(therapySessionTm.getPatient());

        }

        btnBook.setDisable(true);
        btnReschedule.setDisable(false);

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    void setStatus(){
        ObservableList<String> status = FXCollections.observableArrayList("Booked", "Cancelled", "Rescheduled", "Completed");
        cmbSessionStatus.setItems(status);
    }

    void loadProgramIds() throws SQLException {
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();

        ObservableList<String> programIds = FXCollections.observableArrayList();
        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            programIds.add(therapyProgramDto.getId());
        }
        cmbProgramId.setItems(programIds);

    }

    void loadPatientIds() throws SQLException {
        ArrayList<PatientDto> patientsDtos = patientBo.getAll();

        ObservableList<String> patientIds = FXCollections.observableArrayList();
        for (PatientDto patientDto : patientsDtos) {
            patientIds.add(patientDto.getId());
        }
        cmbPatientId.setItems(patientIds);
    }

    void loadTherapistsIds() throws SQLException {
        ArrayList<TherapistDto> therapistsDtos = therapistBo.getAll();

        ObservableList<String> therapistIds = FXCollections.observableArrayList();
        for (TherapistDto therapistDto : therapistsDtos) {
            therapistIds.add(therapistDto.getId());
        }

        cmbTherapistId.setItems(therapistIds);
    }

    void loadTable() throws SQLException {
        ArrayList<TherapySessionDto> therapySessionDtos = therapySessionBo.getAll();

        ObservableList<TherapySessionTm> therapySessionTms = FXCollections.observableArrayList();

        for (TherapySessionDto therapySessionDto : therapySessionDtos) {
            TherapySessionTm therapySessionTm = new TherapySessionTm();
            therapySessionTm.setId(therapySessionDto.getId());
            therapySessionTm.setTime(therapySessionDto.getTime());
            therapySessionTm.setDate(therapySessionDto.getDate());
            therapySessionTm.setStatus(therapySessionDto.getStatus());
            therapySessionTm.setTherapyProgram(therapySessionDto.getTherapyProgramId());
            therapySessionTm.setPatient(therapySessionDto.getPatientId());
            therapySessionTm.setTherapists(therapySessionDto.getTherapistsId());

            therapySessionTms.add(therapySessionTm);
        }

        tblSessions.setItems(therapySessionTms);
    }

    void reset() throws SQLException {
        String id = therapySessionBo.getNextId();
        lblSessionId.setText(id);

        loadTable();

        cmbPatientId.setValue("");
        cmbProgramId.setValue("");
        cmbSessionStatus.setValue("");
        cmbTherapistId.setValue("");
        txtSessionTime.setText("");
        dpSessionDate.setValue(null);

        btnBook.setDisable(false);
        btnReschedule.setDisable(true);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSessionTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSessionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgram"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patient"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapists"));
        colSessionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            reset();
            loadTherapistsIds();
            loadProgramIds();
            loadPatientIds();
            setStatus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
