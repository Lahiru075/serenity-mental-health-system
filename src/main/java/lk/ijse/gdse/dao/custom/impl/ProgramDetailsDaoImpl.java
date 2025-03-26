package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.custom.ProgramDetailsDao;
import lk.ijse.gdse.entity.Patient;
import lk.ijse.gdse.entity.ProgramDetails;
import lk.ijse.gdse.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.security.access.method.P;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramDetailsDaoImpl implements ProgramDetailsDao {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<ProgramDetails> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();

        Query<ProgramDetails> query = session.createQuery("FROM ProgramDetails ", ProgramDetails.class);
        List<ProgramDetails> list = query.list();
        return new ArrayList<>(list);
    }

    @Override
    public boolean save(ProgramDetails programDetails) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(programDetails);
            transaction.commit();
            return true;
        } catch (Exception e){
            transaction.rollback();
            return false;
        } finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(ProgramDetails programDetails) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(programDetails);
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
    public boolean update(ProgramDetails programDetails) throws SQLException {
        return false;
    }
}
