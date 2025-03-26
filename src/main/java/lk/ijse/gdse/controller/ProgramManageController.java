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
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.tm.TherapyProgramTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProgramManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLinkTherapist;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapyProgramTm, String> colDescription;

    @FXML
    private TableColumn<TherapyProgramTm, String> colDuration;

    @FXML
    private TableColumn<TherapyProgramTm, Double> colFee;

    @FXML
    private TableColumn<TherapyProgramTm, String> colProgramId;

    @FXML
    private TableColumn<TherapyProgramTm, String> colProgramName;

    @FXML
    private Label labTherapistId;

    @FXML
    private TableView<TherapyProgramTm> tbProgram;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtName;

    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        boolean isDelete = therapyProgramBo.delete(labTherapistId.getText());

        if(isDelete){
            new Alert(Alert.AlertType.INFORMATION, "Program deleted successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete program").showAndWait();
        }

    }

    @FXML
    void btnLinkTherapistOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = labTherapistId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        String fee = txtFee.getText();
        String description = txtDescription.getText();
        double newFee = Double.parseDouble(fee);

        if (id.isEmpty() || name.isEmpty() || duration.isEmpty() || fee.isEmpty() || description.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z\\s-]+$";
        String durationPattern = "^\\d+\\s+(weeks|months)$";
        String feePattern = "^\\d+(\\.\\d{1,2})?$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidDuration = duration.matches(durationPattern);
        boolean isValidFee = fee.matches(feePattern);
        boolean isValidDescription = description.matches(descriptionPattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").showAndWait();
            return;
        }

        if (!isValidDuration) {
            new Alert(Alert.AlertType.ERROR, "Invalid duration").showAndWait();
            return;
        }

        if (!isValidFee) {
            new Alert(Alert.AlertType.ERROR, "Invalid fee").showAndWait();
            return;
        }

        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").showAndWait();
            return;
        }

        boolean isSaved = therapyProgramBo.save(new TherapyProgramDto(
                id,
                name,
                duration,
                description,
                newFee
        ));

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Program Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Program Save UnSuccessful").showAndWait();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = labTherapistId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        String fee = txtFee.getText();
        String description = txtDescription.getText();
        double newFee = Double.parseDouble(fee);

        if (id.isEmpty() || name.isEmpty() || duration.isEmpty() || fee.isEmpty() || description.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z\\s-]+$";
        String durationPattern = "^\\d+\\s+(weeks|months)$";
        String feePattern = "^\\d+(\\.\\d{1,2})?$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidDuration = duration.matches(durationPattern);
        boolean isValidFee = fee.matches(feePattern);
        boolean isValidDescription = description.matches(descriptionPattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").showAndWait();
            return;
        }

        if (!isValidDuration) {
            new Alert(Alert.AlertType.ERROR, "Invalid duration").showAndWait();
            return;
        }

        if (!isValidFee) {
            new Alert(Alert.AlertType.ERROR, "Invalid fee").showAndWait();
            return;
        }

        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").showAndWait();
            return;
        }

        boolean isSaved = therapyProgramBo.update(new TherapyProgramDto(
                id,
                name,
                duration,
                description,
                newFee
        ));

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Program Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Program Save UnSuccessful").showAndWait();
        }
    }

    void load() throws SQLException {
        ArrayList<TherapyProgramDto> therapistDtos = therapyProgramBo.getAll();

        ObservableList<TherapyProgramTm> programTms = FXCollections.observableArrayList();

        for (TherapyProgramDto therapyProgramDto : therapistDtos){
            TherapyProgramTm therapyProgramTm = new TherapyProgramTm();
            therapyProgramTm.setId(therapyProgramDto.getId());
            therapyProgramTm.setName(therapyProgramDto.getName());
            therapyProgramTm.setDuration(therapyProgramDto.getDuration());
            therapyProgramTm.setFee(therapyProgramDto.getFee());
            therapyProgramTm.setDescription(therapyProgramDto.getDescription());

            programTms.add(therapyProgramTm);
        }

        tbProgram.setItems(programTms);
    }

    void reset() throws SQLException {
        String id = therapyProgramBo.getNextId();
        labTherapistId.setText(id);

        load();

        txtName.clear();
        txtDescription.clear();
        txtDuration.clear();
        txtFee.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @FXML
    void tbProgramOnMouseClicked(MouseEvent event) {
        TherapyProgramTm therapistTm = tbProgram.getSelectionModel().getSelectedItem();

        if (therapistTm != null){
            labTherapistId.setText(therapistTm.getId());
            txtName.setText(therapistTm.getName());
            txtDuration.setText(therapistTm.getDuration());
            txtFee.setText(String.valueOf(therapistTm.getFee()));
            txtDescription.setText(therapistTm.getDescription());
        }

        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
