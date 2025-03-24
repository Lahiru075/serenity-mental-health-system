package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapistTherapyProgramDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistTherapyProgramBo extends SuperBo {
    ArrayList<TherapistTherapyProgramDto> getAll() throws SQLException;
    boolean save(TherapistTherapyProgramDto therapistTherapyProgramDto) throws SQLException;
    boolean delete(String programId, String therapistId) throws SQLException;
}
