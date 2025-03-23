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
import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.dto.tm.UserTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminUserManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbUserRole;

    @FXML
    private TableColumn<UserTm, String> colEmail;

    @FXML
    private TableColumn<UserTm, String> colRole;

    @FXML
    private TableColumn<UserTm, String> colUserId;

    @FXML
    private TableColumn<UserTm, String> colUsername;

    @FXML
    private Label labUserId;

    @FXML
    private TableView<UserTm> tblUsers;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    UserBo userBo = BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        boolean isDelete = userBo.delete(labUserId.getText());

        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION, "User deleted successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete user").showAndWait();
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String userId = labUserId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String role = cmbUserRole.getValue();

        if (userId.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidUsername){
            new Alert(Alert.AlertType.ERROR, "Invalid username").showAndWait();
            return;
        }

        if (!isValidPassword){
            new Alert(Alert.AlertType.ERROR, "Invalid password").showAndWait();
            return;
        }

        if (!isValidEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid email").showAndWait();
            return;
        }

        boolean isSaveUser = userBo.save(new UserDto(userId, username, password, role, email));

        if (isSaveUser){
            new Alert(Alert.AlertType.INFORMATION, "User saved successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to save user").showAndWait();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String userId = labUserId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String role = cmbUserRole.getValue();

        if (userId.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidUsername){
            new Alert(Alert.AlertType.ERROR, "Invalid username").showAndWait();
            return;
        }

        if (!isValidPassword){
            new Alert(Alert.AlertType.ERROR, "Invalid password").showAndWait();
            return;
        }

        if (!isValidEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid email").showAndWait();
            return;
        }

        boolean isUpdate = userBo.update(new UserDto(userId, username, password, role, email));

        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "User updated successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to update user").showAndWait();
        }
    }

    @FXML
    void tbUserOnMouseClicked(MouseEvent event) {
        UserTm userTm = tblUsers.getSelectionModel().getSelectedItem();

        if (userTm != null){
            labUserId.setText(userTm.getId());
            txtUsername.setText(userTm.getUsername());
            txtEmail.setText(userTm.getEmail());
            cmbUserRole.setValue(userTm.getRole());
        }

        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    void loadTable() throws SQLException {
        ArrayList<UserDto> userDtos = userBo.getAll();

        ObservableList<UserTm> userTms = FXCollections.observableArrayList();

        for (UserDto userDto : userDtos) {
            UserTm userTm = new UserTm();
            userTm.setId(userDto.getId());
            userTm.setUsername(userDto.getUsername());
            userTm.setEmail(userDto.getEmail());
            userTm.setRole(userDto.getRole());

            userTms.add(userTm);
        }
        tblUsers.setItems(userTms);
    }

    void loadUserRoles(){
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Receptionist");
        cmbUserRole.setItems(roles);
    }

    void reset() throws SQLException {
        String id = userBo.getNextId();
        labUserId.setText(id);
        loadTable();

        txtUsername.clear();
        txtPassword.clear();
        txtEmail.clear();
        cmbUserRole.setValue("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUserRoles();

        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
