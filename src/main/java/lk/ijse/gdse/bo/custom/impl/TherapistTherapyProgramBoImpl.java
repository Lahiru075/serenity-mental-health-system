package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.TherapistTherapyProgramBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TherapistDao;
import lk.ijse.gdse.dao.custom.TherapistTherapyProgramDao;
import lk.ijse.gdse.dto.TherapistTherapyProgramDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.Therapist;
import lk.ijse.gdse.entity.TherapistTherapyPrograms;
import lk.ijse.gdse.entity.TherapyProgram;
import lk.ijse.gdse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistTherapyProgramBoImpl implements TherapistTherapyProgramBo {

    TherapistTherapyProgramDao therapistTherapyProgramDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPIST_THERAPY_PROGRAM);
    TherapistDao therapistDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPIST);

    @Override
    public ArrayList<TherapistTherapyProgramDto> getAll() throws SQLException {
        ArrayList<TherapistTherapyPrograms> therapyPrograms = therapistTherapyProgramDao.getAll();

        ArrayList<TherapistTherapyProgramDto> therapistTherapyProgramDtos = new ArrayList<>();

        if (therapyPrograms == null) {
            return therapistTherapyProgramDtos;
        }

        for (TherapistTherapyPrograms therapistTherapyPrograms : therapyPrograms) {
            TherapistTherapyProgramDto therapistTherapyProgramDto = new TherapistTherapyProgramDto();
            therapistTherapyProgramDto.setProgramId(therapistTherapyPrograms.getTherapyProgramId());
            therapistTherapyProgramDto.setTherapistId(therapistTherapyPrograms.getTherapistId());

            therapistTherapyProgramDtos.add(therapistTherapyProgramDto);
        }

        return therapistTherapyProgramDtos;
    }

    @Override
    public boolean save(TherapyProgramDto therapyProgramDto, String therapistId) throws SQLException {

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setId(therapyProgramDto.getId());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());


        Therapist therapist1 = therapistDao.findById(therapistId);

        Therapist therapist = new Therapist();
        therapist.setId(therapist1.getId());
        therapist.setName(therapist1.getName());
        therapist.setEmail(therapist1.getEmail());
        therapist.setContact(therapist1.getContact());

        List<Therapist> therapyProgramList = new ArrayList<>();
        therapyProgramList.add(therapist);

        therapyProgram.setTherapists(therapyProgramList);

        return therapistTherapyProgramDao.save(therapyProgram);
    }

    @Override
    public boolean delete(String programId, String therapistId) throws SQLException {
        return therapistTherapyProgramDao.deleteTherapistTherapyProgram(programId, therapistId);
    }

}
