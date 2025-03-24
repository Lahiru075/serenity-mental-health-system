package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.custom.TherapistTherapyProgramDao;
import lk.ijse.gdse.entity.TherapistTherapyPrograms;
import lk.ijse.gdse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapistTherapyProgramDaoImpl implements TherapistTherapyProgramDao {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<TherapistTherapyPrograms> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(TherapistTherapyPrograms therapistTherapyPrograms) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(therapistTherapyPrograms);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(TherapistTherapyPrograms therapistTherapyPrograms) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteTherapistTherapyProgram(String programId, String therapistId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query<TherapistTherapyPrograms> query = session.createQuery("FROM TherapistTherapyPrograms ttp WHERE ttp.therapistId = :therapistId AND ttp.therapyProgramId = :therapyProgramId", TherapistTherapyPrograms.class);
            query.setParameter("therapyProgramId", programId);
            query.setParameter("therapistId", therapistId);
            TherapistTherapyPrograms therapistTherapyPrograms = query.uniqueResult();

            session.remove(therapistTherapyPrograms);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
