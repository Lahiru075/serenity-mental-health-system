package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapistTherapyProgramDto;
import lk.ijse.gdse.dto.TherapyProgramDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistTherapyProgramBo extends SuperBo {
    ArrayList<TherapistTherapyProgramDto> getAll() throws SQLException;
    boolean save(TherapyProgramDto therapyProgramDto, String id) throws SQLException;
    boolean delete(String programId, String therapistId) throws SQLException;
}
