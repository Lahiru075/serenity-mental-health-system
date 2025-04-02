package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.ViewPatientInAllProgramsBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.QueryDao;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class ViewPatientInAllProgramsBoImpl implements ViewPatientInAllProgramsBo {

    QueryDao queryDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.QUERY);

    @Override
    public ArrayList<PatientDto> checkPatientInAllPrograms() {
        ArrayList<Patient> patients = queryDao.checkPatientInAllPrograms();

        ArrayList<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient : patients) {
            patientDtos.add(new PatientDto(
                    patient.getId(),
                    patient.getName(),
                    patient.getEmail(),
                    patient.getRegisterDate(),
                    patient.getContact(),
                    patient.getMedical_history()
            ));
        }

        return patientDtos;
    }
}
