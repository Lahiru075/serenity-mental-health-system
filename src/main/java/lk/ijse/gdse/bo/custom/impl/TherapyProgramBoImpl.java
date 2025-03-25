package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TherapyProgramDao;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.entity.Therapist;
import lk.ijse.gdse.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

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
}
