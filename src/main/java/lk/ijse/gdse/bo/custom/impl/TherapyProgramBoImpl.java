package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TherapyProgramDao;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBoImpl implements TherapyProgramBo {

    TherapyProgramDao therapyProgramDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.PROGRAM);

    @Override
    public String getNextId() throws SQLException {
        String id = therapyProgramDao.getNextId();

        if (id != null) {
            String substring = id.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("MT1%03d", newIdIndex);
        }
        return "MT1001";
    }

    @Override
    public ArrayList<TherapyProgramDto> getAll() throws SQLException {
        ArrayList<TherapyProgram> therapyPrograms = therapyProgramDao.getAll();

        ArrayList<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        for (TherapyProgram therapyProgram : therapyPrograms){
            TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
            therapyProgramDto.setId(therapyProgram.getId());
            therapyProgramDto.setName(therapyProgram.getName());
            therapyProgramDto.setDescription(therapyProgram.getDescription());
            therapyProgramDto.setDuration(therapyProgram.getDuration());
            therapyProgramDto.setFee(therapyProgram.getFee());

            therapyProgramDtos.add(therapyProgramDto);
        }

        return therapyProgramDtos;
    }

    @Override
    public boolean save(TherapyProgramDto therapyProgramDto) throws SQLException {
        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setId(therapyProgramDto.getId());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());

        return therapyProgramDao.save(therapyProgram);
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return therapyProgramDao.delete(Id);
    }

    @Override
    public boolean update(TherapyProgramDto therapyProgramDto) throws SQLException {
        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setId(therapyProgramDto.getId());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());

        return therapyProgramDao.update(therapyProgram);
    }

    @Override
    public TherapyProgramDto findByName(String programName) {
        TherapyProgram therapyProgram = therapyProgramDao.findByName(programName);

        TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
        therapyProgramDto.setId(therapyProgram.getId());
        therapyProgramDto.setName(therapyProgram.getName());
        therapyProgramDto.setDuration(therapyProgram.getDuration());
        therapyProgramDto.setDescription(therapyProgram.getDescription());
        therapyProgramDto.setFee(therapyProgram.getFee());

        return therapyProgramDto;
    }

    @Override
    public TherapyProgramDto findById(String therapyProgramId) {
        TherapyProgram therapyProgram = therapyProgramDao.findById(therapyProgramId);

        TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
        therapyProgramDto.setId(therapyProgram.getId());
        therapyProgramDto.setName(therapyProgram.getName());
        therapyProgramDto.setDuration(therapyProgram.getDuration());
        therapyProgramDto.setDescription(therapyProgram.getDescription());
        therapyProgramDto.setFee(therapyProgram.getFee());

        return therapyProgramDto;
    }

    @Override
    public ArrayList<PatientDto> findPatientsListById(String programId) {
        TherapyProgram therapyProgram = therapyProgramDao.findById(programId);

        List<ProgramDetails> programDetails = therapyProgram.getProgramDetails();

        ArrayList<Patient> patients = new ArrayList<>();

        for (ProgramDetails programDetails1 : programDetails){
            patients.add(programDetails1.getPatient());
        }

        ArrayList<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient : patients){
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
    public ArrayList<SessionStatisticsDto> getAllDetails() throws SQLException {
        ArrayList<TherapyProgram> therapyPrograms = therapyProgramDao.getAll();

        ArrayList<SessionStatisticsDto> sessionStatisticsDtos = new ArrayList<>();

        int completedCounts = 0;
        int bookedCounts = 0;
        int rescheduleCounts = 0;
        int canceledCounts = 0;

        for (TherapyProgram therapyProgram : therapyPrograms){
            SessionStatisticsDto sessionStatisticsDto = new SessionStatisticsDto();
            sessionStatisticsDto.setId(therapyProgram.getId());
            sessionStatisticsDto.setName(therapyProgram.getName());

            List<TherapySession> therapySessionDtos = therapyProgram.getTherapySessions();

            for (TherapySession therapySession : therapySessionDtos){
                if (therapySession.getStatus().equals("Completed")){
                    completedCounts++;
                } else if (therapySession.getStatus().equals("Booked")){
                    bookedCounts++;
                } else if (therapySession.getStatus().equals("Rescheduled")){
                    rescheduleCounts++;
                } else if (therapySession.getStatus().equals("Cancelled")){
                    canceledCounts++;
                }
            }

            sessionStatisticsDto.setCompletedSessionCount(completedCounts);
            sessionStatisticsDto.setBookedSessionCount(bookedCounts);
            sessionStatisticsDto.setRescheduleSessionCount(rescheduleCounts);
            sessionStatisticsDto.setCanceledSessionCount(canceledCounts);

            completedCounts = 0;
            bookedCounts = 0;
            rescheduleCounts = 0;
            canceledCounts = 0;


            sessionStatisticsDtos.add(sessionStatisticsDto);
        }

        return sessionStatisticsDtos;
    }
}
