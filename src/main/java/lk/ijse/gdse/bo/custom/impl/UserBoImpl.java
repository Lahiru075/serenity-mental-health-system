package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.EncryptAndDecrypt;
import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.UserDao;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {

    UserDao userDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.USER);
    EncryptAndDecrypt encryptAndDecrypt = BOFactory.getInstance().getBO(BOFactory.BOType.ENCRYPT);

    @Override
    public UserDto checkUser(String username, String password) {
        List<User> users = userDao.checkUser();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (encryptAndDecrypt.verifyUser(user.getEmail(), password)) {
                    return new UserDto(
                            user.getId(),
                            user.getUsername(),
                            user.getPassword(),
                            user.getRole(),
                            user.getEmail()
                    );
                }
            }
        }

        return null;

    }
}
