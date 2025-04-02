package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.custom.QueryDao;
import lk.ijse.gdse.entity.Patient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryDaoImpl implements QueryDao {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public ArrayList<Patient> checkPatientInAllPrograms() {
        Session session = factoryConfiguration.getSession();

        String countHql = "SELECT COUNT(tp) FROM TherapyProgram tp";
        Long totalPrograms = session.createQuery(countHql, Long.class).getSingleResult();

        String hql = "SELECT p FROM Patient p JOIN p.programDetails pd GROUP BY p HAVING COUNT(DISTINCT pd.therapyProgram) = :totalPrograms";
        Query<Patient> query = session.createQuery(hql, Patient.class);
        query.setParameter("totalPrograms", totalPrograms);
        List<Patient> patients = query.list();

        return new ArrayList<>(patients);
    }
}
