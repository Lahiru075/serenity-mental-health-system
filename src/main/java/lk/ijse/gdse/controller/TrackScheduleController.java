package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TrackTherapyScheduleBo;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.dto.tm.TherapySessionTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TrackScheduleController implements Initializable {

    @FXML
    private Button btnClearSelection;

    @FXML
    private Button btnSearch;

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
    private Label lblTherapistName;

    @FXML
    private TableView<TherapySessionTm> tblTherapistSessions;

    TrackTherapyScheduleBo trackTherapyScheduleBo = BOFactory.getInstance().getBO(BOFactory.BOType.TRACK_THERAPY_SCHEDULE);
    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @FXML
    void btnClearSelectionOnAction(ActionEvent event) {
        cmbTherapistId.setValue(null);
        lblTherapistName.setText("");
        tblTherapistSessions.setItems(null);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws Exception {
        String therapistId = cmbTherapistId.getValue();

        if (therapistId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please select a therapist").showAndWait();
            return;
        }

        ArrayList<TherapySessionDto> therapySessionDtos = trackTherapyScheduleBo.checkTherapySchedule(therapistId);

        if (therapySessionDtos.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "No sessions found").showAndWait();
            return;
        }

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

        tblTherapistSessions.setItems(therapySessionTms);

    }


    void loadTherapistIds() throws SQLException {
        ArrayList<TherapistDto> therapistDtos = therapistBo.getAll();

        ArrayList<String> therapistIds = new ArrayList<>();

        for (TherapistDto therapistDto : therapistDtos) {
            therapistIds.add(therapistDto.getId());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(therapistIds);
        cmbTherapistId.setItems(therapists);
    }

    @FXML
    void cmbTherapistIdOnAction(ActionEvent event) {
        String therapistId = cmbTherapistId.getValue();

        if (therapistId == null){
            return;
        }

        TherapistDto therapistDto = therapistBo.findById(therapistId);
        lblTherapistName.setText(therapistDto.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSessionTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSessionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgram"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patient"));
        colSessionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try{
            loadTherapistIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
