package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.PatientDto;

import java.util.ArrayList;

public interface ViewPatientInAllProgramsBo extends SuperBo {
    ArrayList<PatientDto> checkPatientInAllPrograms();
}
