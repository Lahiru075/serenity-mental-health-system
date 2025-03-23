package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBo extends SuperBo {
    UserDto checkUser(String username, String password);
    String getNextId() throws SQLException;
    ArrayList<UserDto> getAll() throws SQLException;
    boolean save(UserDto userDto) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(UserDto userDto) throws SQLException;
}
