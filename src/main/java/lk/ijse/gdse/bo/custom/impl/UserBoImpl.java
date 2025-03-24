package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.EncryptAndDecrypt;
import lk.ijse.gdse.bo.custom.UserBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.UserDao;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.User;

import java.sql.SQLException;
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

    @Override
    public String getNextId() throws SQLException {
        String id = userDao.getNextId();

        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";

    }

    @Override
    public ArrayList<UserDto> getAll() throws SQLException {
        ArrayList<User> users = userDao.getAll();

        ArrayList<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    user.getEmail()
            ));
        }

        return userDtos;
    }

    @Override
    public boolean save(UserDto userDto) throws SQLException {
        String encryptedPassword = encryptAndDecrypt.encryptPassword(userDto.getPassword());

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(encryptedPassword);
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());

        return userDao.save(user);

    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return userDao.delete(Id);
    }

    @Override
    public boolean update(UserDto userDto) throws SQLException {
        String encryptedPassword = encryptAndDecrypt.encryptPassword(userDto.getPassword());

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(encryptedPassword);
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());

        return userDao.update(user);
    }
}
