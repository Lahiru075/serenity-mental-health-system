package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.ViewPatientHistoryBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.ViewPatientHistoryDto;
import lk.ijse.gdse.dto.tm.ViewPatientHistoryTm;
import lk.ijse.gdse.entity.Patient;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewPatientHistoryController implements Initializable {

    ViewPatientHistoryBo viewPatientHistoryBo = BOFactory.getInstance().getBO(BOFactory.BOType.VIEW_PATIENT_HISTORY);
    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private TableColumn<ViewPatientHistoryTm, Date> colDate;

    @FXML
    private TableColumn<ViewPatientHistoryTm, String> colProgramId;

    @FXML
    private TableColumn<ViewPatientHistoryTm, String> colProgramName;

    @FXML
    private TableColumn<ViewPatientHistoryTm, String> colSessionId;

    @FXML
    private TableColumn<ViewPatientHistoryTm, String> colStatus;

    @FXML
    private TableColumn<ViewPatientHistoryTm, Time> colTime;

    @FXML
    private Label lblPatientName;

    @FXML
    private TableView<ViewPatientHistoryTm> tblPatientHistory;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbPatientId.setValue(null);
        lblPatientName.setText("");
        tblPatientHistory.setItems(null);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String id = cmbPatientId.getValue();

        if (id == null){
            new Alert(Alert.AlertType.ERROR, "Please select a patient").show();
            return;
        }

        ArrayList<ViewPatientHistoryDto> viewPatientHistoryDtos = viewPatientHistoryBo.loadPatientHistory(id);

        if (viewPatientHistoryDtos == null){
            new Alert(Alert.AlertType.ERROR, "No data").showAndWait();
            return;
        }

        ObservableList<ViewPatientHistoryTm> obList = FXCollections.observableArrayList();

        for (ViewPatientHistoryDto dto : viewPatientHistoryDtos) {
            ViewPatientHistoryTm tm = new ViewPatientHistoryTm();
            tm.setProgramId(dto.getProgramId());
            tm.setDate(dto.getDate());
            tm.setTime(dto.getTime());
            tm.setProgramName(dto.getProgramName());
            tm.setStatus(dto.getStatus());
            tm.setSessionId(dto.getSessionId());

            obList.add(tm);
        }

        tblPatientHistory.setItems(obList);

    }

    private void loadPatientIds() throws SQLException {
        ArrayList<String> patientList = viewPatientHistoryBo.getAllPatientIds();

        ObservableList<String> therapists = FXCollections.observableArrayList(patientList);
        cmbPatientId.setItems(therapists);

    }

    @FXML
    void cmbPatientIdOnAction(ActionEvent event) {
        String id = cmbPatientId.getValue();

        if (id == null){
            return;
        }

        PatientDto patientDto  = patientBo.findById(id);

        if (patientDto == null){
            return;
        }

        lblPatientName.setText(patientDto.getName());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));

        try {
            loadPatientIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
