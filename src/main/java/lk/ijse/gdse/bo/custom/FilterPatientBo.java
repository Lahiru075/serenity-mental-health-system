package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.PatientDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public interface FilterPatientBo extends SuperBo {
    ArrayList<PatientDto> findPatientByProgramId(String programId);

    ArrayList<PatientDto> findPatientByDate(Date sessionDate);

    ArrayList<PatientDto> findPatientByStatus(String sessionStatus);
}
