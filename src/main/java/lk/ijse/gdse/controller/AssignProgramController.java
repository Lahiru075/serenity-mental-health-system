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
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapistTherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapistTherapyProgramDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.dto.tm.TherapistTherapyProgramTm;
import lk.ijse.gdse.dto.tm.UserTm;
import lk.ijse.gdse.entity.Therapist;

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
    private ComboBox<String> cmbTherapistName;

    @FXML
    private TableColumn<TherapistTherapyProgramTm, String> colProgramId;

    @FXML
    private TableColumn<TherapistTherapyProgramTm, String> colTherapistId;

    @FXML
    private Label lblProgramId;

    @FXML
    private Label lblTherapistId;

    @FXML
    private TableView<TherapistTherapyProgramTm> tblAssignments;

    TherapistTherapyProgramBo therapistTherapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST_THERAPY_PROGRAM);
    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    private TherapyProgramDto therapyProgramDto = new TherapyProgramDto();

    @FXML
    void btnAssignOnAction(ActionEvent event) throws SQLException {
        String therapistId = lblTherapistId.getText();

        boolean isAssigned = therapistTherapyProgramBo.save(therapyProgramDto,therapistId);

        if (isAssigned) {
            new Alert(Alert.AlertType.INFORMATION, "Program assigned successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to assign program").showAndWait();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String programId = lblProgramId.getText();
        String therapistId = lblTherapistId.getText();

        boolean isDeleted = therapistTherapyProgramBo.delete(programId, therapistId);

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

        TherapyProgramDto therapyProgramDto1 = therapyProgramBo.findByName(programName);
        therapyProgramDto.setId(therapyProgramDto1.getId());
        therapyProgramDto.setName(therapyProgramDto1.getName());
        therapyProgramDto.setDuration(therapyProgramDto1.getDuration());
        therapyProgramDto.setDescription(therapyProgramDto1.getDescription());
        therapyProgramDto.setFee(therapyProgramDto1.getFee());

        lblProgramId.setText(therapyProgramDto1.getId());

    }

    @FXML
    void cmbTherapistNameOnAction(ActionEvent event) {
        String therapistName = cmbTherapistName.getSelectionModel().getSelectedItem();

        if (therapistName == null || therapistName.isEmpty()) {
            return;
        }

        TherapistDto therapistDto = therapistBo.findByName(therapistName);


        if (therapistDto == null){
            return;
        }

        lblTherapistId.setText(therapistDto.getId());

    }

    @FXML
    void tblAssignmentsOnMouseClicked(MouseEvent event) {
        TherapistTherapyProgramTm therapistTherapyProgramTm = tblAssignments.getSelectionModel().getSelectedItem();

        if (therapistTherapyProgramTm != null){
            lblProgramId.setText(therapistTherapyProgramTm.getTherapyProgramId());
            lblTherapistId.setText(therapistTherapyProgramTm.getTherapistId());
        }

        btnAssign.setDisable(true);
        btnDelete.setDisable(false);
    }

    void reset() throws SQLException {

        loadTable();

        lblProgramId.setText("");
        lblTherapistId.setText("");
        cmbProgramName.setValue("");
        cmbTherapistName.setValue("");

        btnAssign.setDisable(false);
        btnDelete.setDisable(true);
    }

    void loadTable() throws SQLException {
        ArrayList<TherapistTherapyProgramDto> therapistTherapyProgramDtos = therapistTherapyProgramBo.getAll();

        ObservableList<TherapistTherapyProgramTm> therapistTherapyProgramTms = FXCollections.observableArrayList();

        for (TherapistTherapyProgramDto therapistTherapyProgramDto : therapistTherapyProgramDtos) {
            TherapistTherapyProgramTm therapistTherapyProgramTm = new TherapistTherapyProgramTm();
            therapistTherapyProgramTm.setTherapyProgramId(therapistTherapyProgramDto.getProgramId());
            therapistTherapyProgramTm.setTherapistId(therapistTherapyProgramDto.getTherapistId());

            therapistTherapyProgramTms.add(therapistTherapyProgramTm);
        }

        tblAssignments.setItems(therapistTherapyProgramTms);

    }

    void loadTherapistId() throws SQLException {
        ArrayList<TherapistDto> therapistDtos = therapistBo.getAll();

        ArrayList<String> therapistIds = new ArrayList<>();

        for (TherapistDto therapistDto : therapistDtos) {
            therapistIds.add(therapistDto.getName());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(therapistIds);
        cmbTherapistName.setItems(therapists);
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
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        try {
            reset();
            loadTherapistId();
            loadTherapyProgramsId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
