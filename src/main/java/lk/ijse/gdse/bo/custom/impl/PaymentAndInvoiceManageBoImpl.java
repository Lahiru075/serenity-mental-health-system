package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.PaymentAndInvoiceManageBo;
import lk.ijse.gdse.bo.custom.ProgramDetailsBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dto.ProgramDetailsDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.entity.ProgramDetails;

import java.util.ArrayList;

public class PaymentAndInvoiceManageBoImpl implements PaymentAndInvoiceManageBo {

    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    ProgramDetailsBo programDetailsBo = BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM_DETAILS);

    @Override
    public ArrayList<TherapyProgramDto> findProgramsByPatientId(String patientId) {
        return patientBo.getProgramsByPatientId(patientId);
    }

    @Override
    public ArrayList<TherapySessionDto> findSessionsByProgramId(String programId, String patientId) {
        ArrayList<TherapySessionDto> sessionDtos = therapyProgramBo.getSessionsByProgramId(programId);

        ArrayList<TherapySessionDto> filteredSessionDtos = new ArrayList<>();

        for (TherapySessionDto sessionDto : sessionDtos) {
            if (sessionDto.getPatientId().equals(patientId) && sessionDto.getTherapyProgramId().equals(programId)) {
                filteredSessionDtos.add(sessionDto);
            }
        }

        return filteredSessionDtos;
    }

    @Override
    public String findCurrentStatus(String patientId, String programId) {
        ProgramDetailsDto programDetailsDto = programDetailsBo.findProgramDetails(patientId, programId);

        if (programDetailsDto != null) {
            double currentPaymentStatus = programDetailsDto.getCurrentPaymentStatus();
            return String.valueOf(currentPaymentStatus);
        }

        return null;
    }
}
