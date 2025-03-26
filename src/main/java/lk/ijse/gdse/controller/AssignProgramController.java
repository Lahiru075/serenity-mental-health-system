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
import lk.ijse.gdse.bo.custom.ProgramDetailsBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.ProgramDetailsDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.tm.ProgramDetailsTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AssignProgramController implements Initializable {

    @FXML
    private Button btnAssign;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox<String> cmbProgramName;

    @FXML
    private ComboBox<String> cmbPatientName;

    @FXML
    private TableColumn<ProgramDetailsTm, String> colProgramId;

    @FXML
    private TableColumn<ProgramDetailsTm, String> colPatientId;

    @FXML
    private TableColumn<ProgramDetailsTm, String> colProgramName;

    @FXML
    private Label lbProgramId;

    @FXML
    private Label lbPatientId;

    @FXML
    private TableView<ProgramDetailsTm> tblAssignments;

    ProgramDetailsBo programDetailsBo = BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM_DETAILS);
    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);



    @FXML
    void btnAssignOnAction(ActionEvent event) throws SQLException {
        String patientId = lbPatientId.getText();
        String programId = lbProgramId.getText();

        boolean isAssigned = programDetailsBo.save(programId,patientId);

        if (isAssigned) {
            new Alert(Alert.AlertType.INFORMATION, "Program assigned successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to assign program").showAndWait();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String programId = lbProgramId.getText();
        String patientId = lbPatientId.getText();

        boolean isDeleted = programDetailsBo.delete(patientId, programId);

        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Program deleted successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete program").showAndWait();
        }
    }

    @FXML
    void cmbProgramNameOnAction(ActionEvent event) {
        String programName = cmbProgramName.getSelectionModel().getSelectedItem();

        if (programName == null || programName.isEmpty()) {
            return;
        }

        TherapyProgramDto therapyProgramDto = therapyProgramBo.findByName(programName);

        if (therapyProgramDto == null){
            return;
        }

        lbProgramId.setText(therapyProgramDto.getId());

    }

    @FXML
    void cmbPatientNameOnAction(ActionEvent event) {
        String therapistName = cmbPatientName.getSelectionModel().getSelectedItem();

        if (therapistName == null || therapistName.isEmpty()) {
            return;
        }

        PatientDto patientDto = patientBo.findByName(therapistName);


        if (patientDto == null){
            return;
        }

        lbPatientId.setText(patientDto.getId());

    }

    @FXML
    void tblAssignmentsOnMouseClicked(MouseEvent event) {
        ProgramDetailsTm therapistTherapyProgramTm = tblAssignments.getSelectionModel().getSelectedItem();

        if (therapistTherapyProgramTm != null){
            lbProgramId.setText(therapistTherapyProgramTm.getTherapyProgram());
            lbPatientId.setText(therapistTherapyProgramTm.getPatient());
        }

        btnAssign.setDisable(true);
        btnDelete.setDisable(false);
    }

    void reset() throws SQLException {

        loadTable();

        lbProgramId.setText("");
        lbPatientId.setText("");
        cmbProgramName.setValue("");
        cmbPatientName.setValue("");

        btnAssign.setDisable(false);
        btnDelete.setDisable(true);
    }

    void loadTable() throws SQLException {
        ArrayList<ProgramDetailsDto> therapistTherapyProgramDtos = programDetailsBo.getAll();

        ObservableList<ProgramDetailsTm> therapistTherapyProgramTms = FXCollections.observableArrayList();

        for (ProgramDetailsDto therapistTherapyProgramDto : therapistTherapyProgramDtos) {
            ProgramDetailsTm therapistTherapyProgramTm = new ProgramDetailsTm();
            therapistTherapyProgramTm.setPatient(therapistTherapyProgramDto.getPatient());
            therapistTherapyProgramTm.setTherapyProgram(therapistTherapyProgramDto.getTherapyProgram());
            therapistTherapyProgramTm.setTherapyProgramName(therapistTherapyProgramDto.getTherapyProgramName());

            therapistTherapyProgramTms.add(therapistTherapyProgramTm);
        }

        tblAssignments.setItems(therapistTherapyProgramTms);

    }

    void loadPatientIds() throws SQLException {
        ArrayList<PatientDto> patientDtos = patientBo.getAll();

        ArrayList<String> patientIds = new ArrayList<>();

        for (PatientDto patientDto : patientDtos) {
            patientIds.add(patientDto.getName());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(patientIds);
        cmbPatientName.setItems(therapists);
    }

    void loadTherapyProgramsId() throws SQLException {
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();

        ArrayList<String> thrapyProgramIds = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            thrapyProgramIds.add(therapyProgramDto.getName());
        }

        ObservableList<String> therapyProgramsId = FXCollections.observableArrayList(thrapyProgramIds);
        cmbProgramName.setItems(therapyProgramsId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgram"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patient"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("therapyProgramName"));

        try {
            reset();
            loadPatientIds();
            loadTherapyProgramsId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
