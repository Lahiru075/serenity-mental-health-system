package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.ProgramDetails;
import lk.ijse.gdse.entity.TherapyProgram;

import java.sql.SQLException;

public interface ProgramDetailsDao extends CrudDao<ProgramDetails, String> {
    boolean delete(ProgramDetails programDetails);
}
