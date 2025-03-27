package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.entity.TherapySession;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface TherapySessionBo extends SuperBo {
    String getNextId() throws SQLException;
    ArrayList<TherapySessionDto> getAll() throws SQLException;
    boolean save(TherapySessionDto dto) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(TherapySessionDto dto) throws SQLException;
    ArrayList<TherapySessionDto> checkByTherapistId(String id);

    ArrayList<PatientDto> findByDate(Date sessionDate);

    ArrayList<PatientDto> findByStatus(String sessionStatus);
}
