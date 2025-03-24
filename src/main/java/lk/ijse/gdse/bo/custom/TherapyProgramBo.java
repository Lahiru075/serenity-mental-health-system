package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramBo extends SuperBo {
    String getNextId() throws SQLException;

    ArrayList<TherapyProgramDto> getAll() throws SQLException;

    boolean save(TherapyProgramDto dto) throws SQLException;

    boolean delete(String Id) throws SQLException;

    boolean update(TherapyProgramDto dto) throws SQLException;
}
