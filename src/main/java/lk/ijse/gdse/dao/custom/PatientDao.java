package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.Patient;

public interface PatientDao extends CrudDao<Patient, String> {
    Patient findById(String patientId);

    Patient findByName(String name);
}
