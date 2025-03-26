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
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dao.custom.TherapistDao;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.dto.tm.TherapistTm;
import lk.ijse.gdse.dto.tm.UserTm;
import lk.ijse.gdse.entity.Therapist;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAssignProgram;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ListView<String> programList;

    @FXML
    private ComboBox<String> cmbTherapyProgram;

    @FXML
    private TableColumn<TherapistTm, Integer> colContact;

    @FXML
    private TableColumn<TherapistTm, String> colEmail;

    @FXML
    private TableColumn<TherapistTm, String> colTherapistName;

    @FXML
    private TableColumn<TherapistTm, String> colTherapyId;

    @FXML
    private Label labTherapistId;

    @FXML
    private TableView<TherapistTm> tbTherapists;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void btnTrackScheduleOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/trackTherapistSchedule.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Schedule Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        boolean isDelete = therapistBo.delete(labTherapistId.getText());

        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION, "Therapist deleted successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to Therapist user").showAndWait();
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = labTherapistId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        List<String> list = new ArrayList<>();

        for (String s : programList.getSelectionModel().getSelectedItems()) {
            list.add(s);
        }

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").showAndWait();
            return;
        }
        if (!isValidEmail) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").showAndWait();
            return;
        }
        if (!isValidContact) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact").showAndWait();
            return;
        }

        boolean isSave = therapistBo.save(new TherapistDto(id, name, email, contact),list);

        if (isSave) {
            new Alert(Alert.AlertType.INFORMATION, "Therapist Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Therapist Save UnSuccessful").showAndWait();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = labTherapistId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        List<String> list = new ArrayList<>();

        for (String s : programList.getSelectionModel().getSelectedItems()) {
            list.add(s);
        }

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").showAndWait();
            return;
        }
        if (!isValidEmail) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").showAndWait();
            return;
        }
        if (!isValidContact) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact").showAndWait();
            return;
        }

        boolean isSave = therapistBo.update(new TherapistDto(id, name, email, contact),list);

        if (isSave) {
            new Alert(Alert.AlertType.INFORMATION, "Therapist Updated Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Therapist Update UnSuccessful").showAndWait();
        }
    }

    @FXML
    void tbTherapistOnMouseClicked(MouseEvent event) {
        TherapistTm therapistTm = tbTherapists.getSelectionModel().getSelectedItem();

        if (therapistTm != null){
            labTherapistId.setText(therapistTm.getId());
            txtName.setText(therapistTm.getName());
            txtEmail.setText(therapistTm.getEmail());
            txtContact.setText(therapistTm.getContact());
        }

        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);

    }

    void loadTable() throws SQLException {
        ArrayList<TherapistDto> therapistDtos = therapistBo.getAll();

        ObservableList<TherapistTm> therapistTms = FXCollections.observableArrayList();

        for (TherapistDto therapistDto : therapistDtos){
            TherapistTm therapistTm = new TherapistTm();
            therapistTm.setId(therapistDto.getId());
            therapistTm.setName(therapistDto.getName());
            therapistTm.setEmail(therapistDto.getEmail());
            therapistTm.setContact(therapistDto.getContact());

            therapistTms.add(therapistTm);
        }

        tbTherapists.setItems(therapistTms);
    }

    void reset() throws SQLException {
        String id = therapistBo.getNextId();
        labTherapistId.setText(id);

        loadTable();

        txtName.clear();
        txtContact.clear();
        txtEmail.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    void loadTherapistName() throws SQLException {
        ArrayList<TherapyProgramDto> therapistDtos = therapyProgramBo.getAll();

        ArrayList<String> therapistNames = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapistDtos) {
            therapistNames.add(therapyProgramDto.getName());
        }

        programList.setItems(FXCollections.observableArrayList(therapistNames));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTherapyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        programList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        try {
            reset();
            loadTherapistName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
