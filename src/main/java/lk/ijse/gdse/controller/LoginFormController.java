package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dto.UserDto;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane mainAnchorpane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private CheckBox showPasswordBox;

    @FXML
    private TextField txtSecondPassword;

    @FXML
    private TextField txtUsername;

    UserBo userBo = BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {

        if (showPasswordBox.isSelected()){
            new Alert(Alert.AlertType.ERROR, "Please uncheck show password box").showAndWait();
            return;
        }

        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter username and password").showAndWait();
        } else {
            try{
                UserDto userDto = userBo.checkUser(txtUsername.getText(), txtPassword.getText());

                if (userDto == null) {
                    new Alert(Alert.AlertType.ERROR, "Invalid username or password").showAndWait();
                    txtUsername.clear();
                    txtPassword.clear();
                    txtSecondPassword.clear();
                    return;
                }

                Stage currentStage = (Stage) mainAnchorpane.getScene().getWindow();
                currentStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adminDashBoard.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Admin Dashboard");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "loading error").showAndWait();
            }
        }
    }

    @FXML
    void showPasswordBoxOnAction(ActionEvent event) {

        if (showPasswordBox.isSelected()) {
            txtSecondPassword.setText(txtPassword.getText());
            txtSecondPassword.setVisible(true);
            txtPassword.setVisible(false);
        } else {
            txtPassword.setText(txtSecondPassword.getText());
            txtPassword.setVisible(true);
            txtSecondPassword.setVisible(false);
        }
    }
}
