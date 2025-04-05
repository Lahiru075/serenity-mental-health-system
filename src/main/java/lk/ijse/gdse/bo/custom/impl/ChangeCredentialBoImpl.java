package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.ChangeCredentialBo;
import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.User;

import java.sql.SQLException;

public class ChangeCredentialBoImpl implements ChangeCredentialBo {

    UserBo userBo = BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public UserDto checkUser(String username, String password) {
        return userBo.checkUser(username, password);
    }

    @Override
    public boolean changeCredential(UserDto userDto, String username, String password) throws SQLException {
        UserDto userDto1 = new UserDto();
        userDto1.setId(userDto.getId());
        userDto1.setUsername(username);
        userDto1.setPassword(password);
        userDto1.setRole(userDto.getRole());
        userDto1.setEmail(userDto.getEmail());

        return userBo.update(userDto1);


    }
}
