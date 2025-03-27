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
import lk.ijse.gdse.bo.custom.FilterPatientBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.dto.tm.PatientTm;
import lk.ijse.gdse.dto.tm.TherapyProgramTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FilterPatientController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private DatePicker dates;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbSessionStatus;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colPatientId;

    @FXML
    private TableView<PatientTm> tblPatients;

    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    FilterPatientBo filterPatientBo = BOFactory.getInstance().getBO(BOFactory.BOType.FILTER_PATIENT);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbProgramId.setValue(null);
        dates.setValue(null);
        cmbSessionStatus.setValue(null);
        tblPatients.setItems(null);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String programId = cmbProgramId.getValue();
        String sessionStatus = cmbSessionStatus.getValue();

        ArrayList<PatientDto> patientDtos = new ArrayList<>();

        if (programId != null && sessionStatus == null & dates.getValue() == null) {
            patientDtos = filterPatientBo.findPatientByProgramId(programId);
        } else if (dates.getValue() != null && programId == null && sessionStatus == null) {
            Date date = Date.valueOf(dates.getValue());
            patientDtos = filterPatientBo.findPatientByDate(date);
        } else if (sessionStatus != null && dates.getValue() == null && programId == null) {
            patientDtos = filterPatientBo.findPatientByStatus(sessionStatus);
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select one of this").showAndWait();
            cmbProgramId.setValue(null);
            dates.setValue(null);
            cmbSessionStatus.setValue(null);
        }

        if (patientDtos.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Not found patient").showAndWait();
            cmbProgramId.setValue(null);
            dates.setValue(null);
            cmbSessionStatus.setValue(null);
            return;
        }

        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : patientDtos) {
            PatientTm patientTm = new PatientTm();
            patientTm.setId(patientDto.getId());
            patientTm.setName(patientDto.getName());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setContact(patientDto.getContact());

            patientTms.add(patientTm);
        }

        tblPatients.setItems(patientTms);
    }

    void loadAllPrograms() throws SQLException {
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();

        ArrayList<String> programIds = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            programIds.add(therapyProgramDto.getId());
        }

        ObservableList<String> Ids = FXCollections.observableArrayList(programIds);
        cmbProgramId.setItems(Ids);
    }

    void setStatus() {
        ObservableList<String> status = FXCollections.observableArrayList("Booked", "Cancelled", "Rescheduled", "Completed");
        cmbSessionStatus.setItems(status);
    }

    @FXML
    void tblPatientsOnMouseClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            loadAllPrograms();
            setStatus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
