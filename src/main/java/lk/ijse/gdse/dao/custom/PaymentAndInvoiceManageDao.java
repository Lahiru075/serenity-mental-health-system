package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDao;
import lk.ijse.gdse.dto.PaymentDto;
import lk.ijse.gdse.entity.Payment;
import org.hibernate.Session;

public interface PaymentAndInvoiceManageDao extends CrudDao<Payment, String> {
    boolean savePayment(Session session, Payment payment);

    Payment findById(String id);

    boolean updatePayment(Session session, Payment payment);
}
