package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.ViewPatientInAllProgramsBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.tm.PatientTm;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPatientInAllProgramsController implements Initializable {

    @FXML
    private TableColumn<PatientTm, Date> colDate;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String>  colId;

    @FXML
    private TableColumn<PatientTm, String>  colName;

    @FXML
    private TableView<PatientTm> tblPatients;

    ViewPatientInAllProgramsBo viewPatientInAllProgramsBo = BOFactory.getInstance().getBO(BOFactory.BOType.VIEW_PATIENT_IN_ALL_PROGRAMS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));

        try {
            loadTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTable() {
        ArrayList<PatientDto> patientDtos = viewPatientInAllProgramsBo.checkPatientInAllPrograms();

        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : patientDtos) {
            PatientTm patientTm = new PatientTm();
            patientTm.setId(patientDto.getId());
            patientTm.setName(patientDto.getName());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setRegisterDate(patientDto.getRegisterDate());

            patientTms.add(patientTm);
        }

        tblPatients.setItems(patientTms);
    }
}
