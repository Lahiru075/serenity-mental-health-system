package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TherapistDao;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.Therapist;
import lk.ijse.gdse.entity.TherapyProgram;
import lk.ijse.gdse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBoImpl implements TherapistBo {

    TherapistDao therapistDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @Override
    public String getNextId() throws SQLException {
        String id = therapistDao.getNextId();

        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("T%03d", newIdIndex);
        }
        return "T001";
    }

    @Override
    public ArrayList<TherapistDto> getAll() throws SQLException {

        ArrayList<Therapist> therapists = therapistDao.getAll();

        ArrayList<TherapistDto> therapistDtos = new ArrayList<>();

        for (Therapist therapist : therapists) {
            therapistDtos.add(new TherapistDto(
                    therapist.getId(),
                    therapist.getName(),
                    therapist.getEmail(),
                    therapist.getContact()
            ));
        }

        return therapistDtos;
    }

    @Override
    public boolean save(TherapistDto therapistDto, List<String> programNames) throws SQLException {
        Therapist therapist = new Therapist();
        therapist.setId(therapistDto.getId());
        therapist.setName(therapistDto.getName());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setContact(therapistDto.getContact());

        ArrayList<TherapyProgramDto> therapyProgram = therapyProgramBo.getAll();

        List<TherapyProgram> selectedProgramDto = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgram) {
            for (String name : programNames){
                if (name.equals(therapyProgramDto.getName())){
                    TherapyProgram therapyProgram1 = new TherapyProgram();
                    therapyProgram1.setId(therapyProgramDto.getId());
                    therapyProgram1.setName(therapyProgramDto.getName());
                    therapyProgram1.setDuration(therapyProgramDto.getDuration());
                    therapyProgram1.setDescription(therapyProgramDto.getDescription());
                    therapyProgram1.setFee(therapyProgramDto.getFee());

                    selectedProgramDto.add(therapyProgram1);
                }
            }
        }

        therapist.setTherapyPrograms(selectedProgramDto);

        return therapistDao.save(therapist);

    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return therapistDao.delete(Id);
    }

    @Override
    public boolean update(TherapistDto therapistDto, List<String> programNames) throws SQLException {
        Therapist therapist = new Therapist();
        therapist.setId(therapistDto.getId());
        therapist.setName(therapistDto.getName());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setContact(therapistDto.getContact());

        return therapistDao.update(therapist);
    }

    @Override
    public TherapistDto findByName(String therapistName) {
        Therapist therapist = therapistDao.findByName(therapistName);

        return new TherapistDto(therapist.getId(), therapist.getName(), therapist.getEmail(), therapist.getContact());
    }

    @Override
    public TherapistDto findById(String therapistsId) {
        Therapist therapist = therapistDao.findById(therapistsId);

        return new TherapistDto(therapist.getId(), therapist.getName(), therapist.getEmail(), therapist.getContact());
    }
}
