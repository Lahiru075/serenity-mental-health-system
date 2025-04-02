package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientBo extends SuperBo {
    String getNextId() throws SQLException;
    ArrayList<PatientDto> getAll() throws SQLException;
    boolean save(PatientDto dto) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(PatientDto dto) throws SQLException;

    PatientDto findById(String patientId);

    PatientDto findByName(String therapistName);

    ArrayList<TherapyProgramDto> getProgramsByPatientId(String patientId);
}
