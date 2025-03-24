package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.entity.Therapist;

public interface TherapistDao extends CrudDao<Therapist, String> {
    Therapist findByName(String therapistName);

    Therapist findById(String therapistId);
}
