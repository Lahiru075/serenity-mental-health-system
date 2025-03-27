package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.ViewPatientProgramBo;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.TherapyProgram;

import java.util.ArrayList;

public class ViewPatientProgramBoImpl implements ViewPatientProgramBo {
    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    @Override
    public ArrayList<TherapyProgramDto> getProgramsByPatientId(String patientId) {
        return patientBo.getProgramsByPatientId(patientId);
    }
}
