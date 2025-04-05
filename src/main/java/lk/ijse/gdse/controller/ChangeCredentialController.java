package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.ChangeCredentialBo;
import lk.ijse.gdse.dto.UserDto;

import java.sql.SQLException;

public class ChangeCredentialController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private PasswordField txtCurrentPassword;

    @FXML
    private TextField txtCurrentUsername;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtNewUsername;

    ChangeCredentialBo changeCredentialBo = BOFactory.getInstance().getBO(BOFactory.BOType.CHANGE_CREDENTIAL);

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        reset();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String username = txtNewUsername.getText();
        String password = txtNewPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);

        if (!isValidUsername) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").showAndWait();
            return;
        }

        if (!isValidPassword) {
            new Alert(Alert.AlertType.ERROR, "Invalid password").showAndWait();
            return;
        }

        UserDto userDto = changeCredentialBo.checkUser(txtCurrentUsername.getText(), txtCurrentPassword.getText());

        if (userDto == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid current username or password").showAndWait();
            return;
        }

        boolean isChanged = changeCredentialBo.changeCredential(userDto, username, password);

        if (isChanged) {
            new Alert(Alert.AlertType.INFORMATION, "Credentials changed successfully").showAndWait();txtCurrentUsername.clear();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to change credentials").showAndWait();
        }

    }

    private void reset(){
        txtCurrentUsername.clear();
        txtCurrentPassword.clear();
        txtNewUsername.clear();
        txtNewPassword.clear();
    }

}
