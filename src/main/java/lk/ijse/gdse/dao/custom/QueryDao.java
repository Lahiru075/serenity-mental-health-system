package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.SuperDao;
import lk.ijse.gdse.entity.Patient;

import java.util.ArrayList;

public interface QueryDao extends SuperDao {
    ArrayList<Patient> checkPatientInAllPrograms();
}
