package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.ProgramDetails;
import lk.ijse.gdse.entity.TherapyProgram;
import org.hibernate.Session;

import java.sql.SQLException;

public interface ProgramDetailsDao extends CrudDao<ProgramDetails, String> {
    boolean delete(ProgramDetails programDetails);

    ProgramDetails findProgramDetails(String patientId, String programId);

    boolean updateCurrentPayment(Session session, ProgramDetails programDetails);
}
