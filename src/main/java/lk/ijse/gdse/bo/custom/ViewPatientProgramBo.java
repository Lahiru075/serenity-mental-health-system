package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapyProgramDto;

import java.util.ArrayList;

public interface ViewPatientProgramBo extends SuperBo {
    ArrayList<TherapyProgramDto> getProgramsByPatientId(String patientId);
}
