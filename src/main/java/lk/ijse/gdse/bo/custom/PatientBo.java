package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PatientBo extends SuperBo {
    String getNextId() throws SQLException;
    ArrayList<PatientDto> getAll() throws SQLException;
    boolean save(PatientDto dto) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(PatientDto dto) throws SQLException;
}
