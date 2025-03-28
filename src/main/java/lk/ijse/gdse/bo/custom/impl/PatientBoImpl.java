package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.PatientDao;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.ProgramDetails;
import lk.ijse.gdse.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBoImpl implements PatientBo {

    PatientDao patientDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.PATIENT);

    @Override
    public String getNextId() throws SQLException {
        String id = patientDao.getNextId();

        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public ArrayList<PatientDto> getAll() throws SQLException {
        ArrayList<Patient> patients = patientDao.getAll();

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

    @Override
    public boolean save(PatientDto dto) throws SQLException {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setRegisterDate(dto.getRegisterDate());
        patient.setContact(dto.getContact());
        patient.setMedical_history(dto.getMedical_history());

        return patientDao.save(patient);
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return patientDao.delete(Id);
    }

    @Override
    public boolean update(PatientDto dto) throws SQLException {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setRegisterDate(dto.getRegisterDate());
        patient.setContact(dto.getContact());
        patient.setMedical_history(dto.getMedical_history());

        return patientDao.update(patient);
    }

    @Override
    public PatientDto findById(String patientId) {
        Patient patient = patientDao.findById(patientId);

        return new PatientDto(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getRegisterDate(),
                patient.getContact(),
                patient.getMedical_history()
        );
    }

    @Override
    public PatientDto findByName(String therapistName) {
        Patient patient = patientDao.findByName(therapistName);

        return new PatientDto(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getRegisterDate(),
                patient.getContact(),
                patient.getMedical_history()
        );
    }

    @Override
    public ArrayList<TherapyProgramDto> getProgramsByPatientId(String patientId) {
        Patient patient = patientDao.findById(patientId);

        List<ProgramDetails> therapyPrograms = patient.getProgramDetails();


        ArrayList<TherapyProgram> programs = new ArrayList<>();

        for (ProgramDetails programDetails : therapyPrograms) {
            programs.add(programDetails.getTherapyProgram());
        }

        ArrayList<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        for (TherapyProgram therapyProgram : programs) {
            therapyProgramDtos.add(new TherapyProgramDto(
                    therapyProgram.getId(),
                    therapyProgram.getName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getDescription(),
                    therapyProgram.getFee()
            ));
        }

        return therapyProgramDtos;
    }
}
