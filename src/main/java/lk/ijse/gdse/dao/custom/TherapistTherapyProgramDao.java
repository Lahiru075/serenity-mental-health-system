package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.TherapistTherapyPrograms;

public interface TherapistTherapyProgramDao extends CrudDao<TherapistTherapyPrograms, String> {

    boolean deleteTherapistTherapyProgram(String programId, String therapistId);
}
