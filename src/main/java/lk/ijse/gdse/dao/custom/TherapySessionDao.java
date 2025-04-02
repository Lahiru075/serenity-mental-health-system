package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.TherapySession;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public interface TherapySessionDao extends CrudDao<TherapySession, String> {
    ArrayList<TherapySession> checkByTherapistId(String id);

    ArrayList<TherapySession> findByDate(Date sessionDate);

    ArrayList<TherapySession> findByStatus(String sessionStatus);
}