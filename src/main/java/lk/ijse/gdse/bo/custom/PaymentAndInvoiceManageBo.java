package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;

import java.util.ArrayList;

public interface PaymentAndInvoiceManageBo extends SuperBo {
    ArrayList<TherapyProgramDto> findProgramsByPatientId(String patientId);

    ArrayList<TherapySessionDto> findSessionsByProgramId(String programId, String patientId);

    String findCurrentStatus(String patientId, String programId);
}
