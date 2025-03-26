package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.custom.UserDao;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public List<User> checkUser() {
        Session session = factoryConfiguration.getSession();
        Query<User> query = session.createQuery("from User", User.class);
        List<User> list = query.list();
        return list;
    }

    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();

        String lastId = session
                .createQuery("SELECT u.id FROM User u ORDER BY u.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        return lastId;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Query<User> query = session.createQuery("from User", User.class);
        List<User> list = query.list();
        return new ArrayList<>(list);
    }

    @Override
    public boolean save(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
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
            User user = session.get(User.class, Id);
            session.remove(user);
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
    public boolean update(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(user);
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
}
