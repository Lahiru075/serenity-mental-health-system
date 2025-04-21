package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.PaymentDto;

import java.sql.Date;
import java.util.ArrayList;

public interface FinanceReportBo extends SuperBo {
    ArrayList<PaymentDto> getPayments(Date firstDay, Date lastDay);

    double[] getPaymentCounts(Date firstDay, Date lastDay);
}
