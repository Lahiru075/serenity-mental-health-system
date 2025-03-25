package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.PatientBo;
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TherapyProgramDao;
import lk.ijse.gdse.dao.custom.TherapySessionDao;
import lk.ijse.gdse.dto.PatientDto;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.Therapist;
import lk.ijse.gdse.entity.TherapyProgram;
import lk.ijse.gdse.entity.TherapySession;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapySessionBoImpl implements TherapySessionBo {

    TherapySessionDao therapySessionDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.SESSION);
    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @Override
    public String getNextId() throws SQLException {
        String id = therapySessionDao.getNextId();

        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";

    }

    @Override
    public ArrayList<TherapySessionDto> getAll() throws SQLException {
        ArrayList<TherapySession> therapySessions = therapySessionDao.getAll();

        ArrayList<TherapySessionDto> therapySessionDtos = new ArrayList<>();

        for (TherapySession therapySession : therapySessions){
            TherapySessionDto therapySessionDto = new TherapySessionDto();
            therapySessionDto.setId(therapySession.getId());
            therapySessionDto.setTime(therapySession.getTime());
            therapySessionDto.setDate(therapySession.getDate());
            therapySessionDto.setStatus(therapySession.getStatus());
            therapySessionDto.setTherapyProgramId(therapySession.getTherapyProgram().getId());
            therapySessionDto.setTherapistsId(therapySession.getTherapists().getId());
            therapySessionDto.setPatientId(therapySession.getPatient().getId());

            therapySessionDtos.add(therapySessionDto);
        }

        return therapySessionDtos;

    }

    @Override
    public boolean save(TherapySessionDto dto) throws SQLException {
        TherapistDto therapistDto = therapistBo.findById(dto.getTherapistsId());
        TherapyProgramDto programDto = therapyProgramBo.findById(dto.getTherapyProgramId());
        PatientDto patientDto = patientBo.findById(dto.getPatientId());

        Therapist therapist = new Therapist();
        therapist.setId(therapistDto.getId());
        therapist.setName(therapistDto.getName());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setContact(therapistDto.getContact());

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setId(programDto.getId());
        therapyProgram.setName(programDto.getName());
        therapyProgram.setDuration(programDto.getDuration());
        therapyProgram.setDescription(programDto.getDescription());
        therapyProgram.setFee(programDto.getFee());

        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setName(patient.getName());
        patient.setEmail(patient.getEmail());
        patient.setRegisterDate(patient.getRegisterDate());
        patient.setContact(patient.getContact());
        patient.setMedical_history(patient.getMedical_history());

        TherapySession therapySession = new TherapySession();
        therapySession.setId(dto.getId());
        therapySession.setTime(dto.getTime());
        therapySession.setDate(dto.getDate());
        therapySession.setStatus(dto.getStatus());
        therapySession.setTherapyProgram(therapyProgram);
        therapySession.setTherapists(therapist);
        therapySession.setPatient(patient);

        return therapySessionDao.save(therapySession);
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return therapySessionDao.delete(Id);
    }

    @Override
    public boolean update(TherapySessionDto dto) throws SQLException {
        TherapistDto therapistDto = therapistBo.findById(dto.getTherapistsId());
        TherapyProgramDto programDto = therapyProgramBo.findById(dto.getTherapyProgramId());
        PatientDto patientDto = patientBo.findById(dto.getPatientId());

        Therapist therapist = new Therapist();
        therapist.setId(therapistDto.getId());
        therapist.setName(therapistDto.getName());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setContact(therapistDto.getContact());

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setId(programDto.getId());
        therapyProgram.setName(programDto.getName());
        therapyProgram.setDuration(programDto.getDuration());
        therapyProgram.setDescription(programDto.getDescription());
        therapyProgram.setFee(programDto.getFee());

        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setName(patient.getName());
        patient.setEmail(patient.getEmail());
        patient.setRegisterDate(patient.getRegisterDate());
        patient.setContact(patient.getContact());
        patient.setMedical_history(patient.getMedical_history());

        TherapySession therapySession = new TherapySession();
        therapySession.setId(dto.getId());
        therapySession.setTime(dto.getTime());
        therapySession.setDate(dto.getDate());
        therapySession.setStatus(dto.getStatus());
        therapySession.setTherapyProgram(therapyProgram);
        therapySession.setTherapists(therapist);
        therapySession.setPatient(patient);

        return therapySessionDao.update(therapySession);
    }
}
