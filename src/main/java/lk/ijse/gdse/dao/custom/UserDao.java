package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface UserDao extends CrudDao<User, String> {
    List<User> checkUser(String username);
}
