package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.ProgramDetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProgramDetailsBo extends SuperBo {
    ArrayList<ProgramDetailsDto> getAll() throws SQLException;
    boolean save(String programId, String patientId) throws SQLException;
    boolean delete(String patientId, String programId) throws SQLException;
}
