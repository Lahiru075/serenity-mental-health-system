package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.FilterPatientBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dto.PatientDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class FilterPatientBoImpl implements FilterPatientBo {

    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    @Override
    public ArrayList<PatientDto> findPatientByProgramId(String programId) {
        return therapyProgramBo.findPatientsListById(programId);
    }

    @Override
    public ArrayList<PatientDto> findPatientByDate(Date sessionDate) {
        return therapySessionBo.findByDate(sessionDate);
    }

    @Override
    public ArrayList<PatientDto> findPatientByStatus(String sessionStatus) {
        return therapySessionBo.findByStatus(sessionStatus);
    }
}
