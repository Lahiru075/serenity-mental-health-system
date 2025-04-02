package lk.ijse.gdse.dao;

import lk.ijse.gdse.entity.SuperEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao<T extends SuperEntity, ID> extends SuperDao{
    String getNextId() throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    boolean save(T t) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(T t) throws SQLException;
}
