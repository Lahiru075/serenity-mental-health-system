package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.UserDto;

import java.sql.SQLException;

public interface ChangeCredentialBo extends SuperBo {
    UserDto checkUser(String text, String text1);

    boolean changeCredential(UserDto userDto, String username, String password) throws SQLException;
}
