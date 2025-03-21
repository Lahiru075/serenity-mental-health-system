package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.UserDto;

public interface UserBo extends SuperBo {
    UserDto checkUser(String username, String password);
}
