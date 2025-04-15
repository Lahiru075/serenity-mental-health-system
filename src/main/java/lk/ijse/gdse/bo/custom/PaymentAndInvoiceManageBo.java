package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentAndInvoiceManageBo extends SuperBo {
    ArrayList<TherapyProgramDto> findProgramsByPatientId(String patientId);

    ArrayList<TherapySessionDto> findSessionsByProgramId(String programId, String patientId);

    String findCurrentStatus(String patientId, String programId);

    String getNextId() throws SQLException;

    boolean save(String paymentId, String patientId, String therapyProgramId, String therapySessionId, double amount, String status, double currentPayment, Date date) throws SQLException;
}
