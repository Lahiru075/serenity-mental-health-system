package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.custom.PaymentAndInvoiceManageDao;
import lk.ijse.gdse.entity.Payment;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentAndInvoiceManageDaoImpl implements PaymentAndInvoiceManageDao {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();

        String lastId = session
                .createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        return lastId;
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Payment payment) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        return false;
    }

    @Override
    public boolean savePayment(Session session, Payment payment) {
        try {
            session.merge(payment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
