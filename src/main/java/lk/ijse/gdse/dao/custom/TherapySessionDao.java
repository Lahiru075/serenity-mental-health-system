package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.TherapySession;

import java.util.ArrayList;

public interface TherapySessionDao extends CrudDao<TherapySession, String> {
    ArrayList<TherapySession> checkByTherapistId(String id);
}
