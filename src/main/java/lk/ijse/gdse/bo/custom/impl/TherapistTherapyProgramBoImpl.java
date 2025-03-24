package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.TherapistTherapyProgramBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TherapistTherapyProgramDao;
import lk.ijse.gdse.dto.TherapistTherapyProgramDto;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.TherapistTherapyPrograms;
import lk.ijse.gdse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapistTherapyProgramBoImpl implements TherapistTherapyProgramBo {

    TherapistTherapyProgramDao therapistTherapyProgramDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPIST_THERAPY_PROGRAM);

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
    public boolean save(TherapistTherapyProgramDto therapistTherapyProgramDto) throws SQLException {
        return therapistTherapyProgramDao.save(new TherapistTherapyPrograms(therapistTherapyProgramDto.getProgramId(), therapistTherapyProgramDto.getTherapistId()));
    }

    @Override
    public boolean delete(String programId, String therapistId) throws SQLException {
        return therapistTherapyProgramDao.deleteTherapistTherapyProgram(programId, therapistId);
    }

}
