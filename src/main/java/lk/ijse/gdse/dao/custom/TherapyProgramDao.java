package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.TherapyProgram;

public interface TherapyProgramDao extends CrudDao<TherapyProgram, String> {
    TherapyProgram findByName(String programName);

    TherapyProgram findById(String therapyProgramId);
}
