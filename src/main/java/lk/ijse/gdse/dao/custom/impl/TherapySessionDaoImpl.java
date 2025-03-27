package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.custom.TherapySessionDao;
import lk.ijse.gdse.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionDaoImpl implements TherapySessionDao {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();

        String lastId = session
                .createQuery("SELECT ts.id FROM TherapySession ts ORDER BY ts.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        return lastId;
    }

    @Override
    public ArrayList<TherapySession> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<TherapySession> query = session.createQuery("from TherapySession", TherapySession.class);
        List<TherapySession> list = query.list();
        return new ArrayList<>(list);

    }

    @Override
    public boolean save(TherapySession therapySession) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.persist(therapySession);
            transaction.commit();
            return true;
        } catch (Exception e){
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
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            TherapySession therapySession = session.get(TherapySession.class, Id);
            session.remove(therapySession);
            transaction.commit();
            return true;
        } catch (Exception e){
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(TherapySession therapySession) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.merge(therapySession);
            transaction.commit();
            return true;
        } catch (Exception e){
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ArrayList<TherapySession> checkByTherapistId(String id) {
        Session session = factoryConfiguration.getSession();

        Query<TherapySession> query = session.createQuery("FROM TherapySession ts WHERE ts.therapists.id = :id", TherapySession.class);
        query.setParameter("id", id);
        List<TherapySession> therapySessions = query.list();
        return new ArrayList<>(therapySessions);
    }

    @Override
    public ArrayList<TherapySession> findByDate(Date sessionDate) {
        Session session = factoryConfiguration.getSession();

        Query<TherapySession> query = session.createQuery("FROM TherapySession ts WHERE ts.date = :sessionDate", TherapySession.class);
        query.setParameter("sessionDate", sessionDate);
        List<TherapySession> list = query.list();
        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<TherapySession> findByStatus(String sessionStatus) {
        Session session = factoryConfiguration.getSession();

        Query<TherapySession> query = session.createQuery("FROM TherapySession ts WHERE ts.status = :sessionStatus", TherapySession.class);
        query.setParameter("sessionStatus", sessionStatus);
        List<TherapySession> list = query.list();
        return new ArrayList<>(list);
    }
}
