package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapistBo extends SuperBo {
    String getNextId() throws SQLException;
    ArrayList<TherapistDto> getAll() throws SQLException;
    boolean save(TherapistDto therapistDto, List<String> programNames) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(TherapistDto therapistDto, List<String> list) throws SQLException;
    TherapistDto findByName(String name);

    TherapistDto findById(String therapistsId);
}
