package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dto.UserDto;

public class LoginFormController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    UserBo userBo = BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        UserDto userDto = userBo.checkUser(txtUsername.getText(), txtPassword.getText());
    }

}
