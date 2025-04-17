package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.bo.custom.ViewPatientHistoryBo;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.dto.ViewPatientHistoryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewPatientHistoryBoImpl implements ViewPatientHistoryBo {

    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @Override
    public ArrayList<String> getAllPatientIds() throws SQLException {
        ArrayList<PatientDto> patientsDtos = patientBo.getAll();

        ArrayList<String> patientIds = new ArrayList<>();

        for (PatientDto patientDto : patientsDtos) {
            patientIds.add(patientDto.getId());
        }

        return patientIds;
    }

    @Override
    public ArrayList<ViewPatientHistoryDto> loadPatientHistory(String id) throws SQLException {
        ArrayList<TherapySessionDto> viewPatientHistoryDtos = therapySessionBo.getSessionByPatientId(id);

        ArrayList<ViewPatientHistoryDto> viewPatientHistoryDtos1 = new ArrayList<>();

        for (TherapySessionDto dto : viewPatientHistoryDtos) {
            ViewPatientHistoryDto viewPatientHistoryDto = new ViewPatientHistoryDto();
            viewPatientHistoryDto.setProgramId(dto.getTherapyProgramId());
            viewPatientHistoryDto.setDate(dto.getDate());
            viewPatientHistoryDto.setTime(dto.getTime());
            viewPatientHistoryDto.setStatus(dto.getStatus());
            viewPatientHistoryDto.setSessionId(dto.getId());

            TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(dto.getTherapyProgramId());
            viewPatientHistoryDto.setProgramName(therapyProgramDto.getName());

            viewPatientHistoryDtos1.add(viewPatientHistoryDto);

        }

        return viewPatientHistoryDtos1;

    }
}
