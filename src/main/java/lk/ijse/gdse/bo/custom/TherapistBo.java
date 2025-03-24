package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistBo extends SuperBo {
    String getNextId() throws SQLException;
    ArrayList<TherapistDto> getAll() throws SQLException;
    boolean save(TherapistDto therapistDto) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(TherapistDto therapistDto) throws SQLException;
    TherapistDto findByName(String name);
}
