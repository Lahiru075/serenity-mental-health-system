package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.*;
import lk.ijse.gdse.config.FactoryConfiguration;
import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.PaymentAndInvoiceManageDao;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentAndInvoiceManageBoImpl implements PaymentAndInvoiceManageBo {

    PatientBo patientBo = BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    ProgramDetailsBo programDetailsBo = BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM_DETAILS);
    PaymentAndInvoiceManageDao paymentAndInvoiceManageDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.PAYMENT);
    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public ArrayList<TherapyProgramDto> findProgramsByPatientId(String patientId) {
        return patientBo.getProgramsByPatientId(patientId);
    }

    @Override
    public ArrayList<TherapySessionDto> findSessionsByProgramId(String programId, String patientId) {
        ArrayList<TherapySessionDto> sessionDtos = therapyProgramBo.getSessionsByProgramId(programId);

        ArrayList<TherapySessionDto> filteredSessionDtos = new ArrayList<>();

        for (TherapySessionDto sessionDto : sessionDtos) {
            if (sessionDto.getPatientId().equals(patientId) && sessionDto.getTherapyProgramId().equals(programId)) {
                filteredSessionDtos.add(sessionDto);
            }
        }

        return filteredSessionDtos;
    }

    @Override
    public String findCurrentStatus(String patientId, String programId) {
        ProgramDetailsDto programDetailsDto = programDetailsBo.findProgramDetails(patientId, programId);

        if (programDetailsDto != null) {
            double currentPaymentStatus = programDetailsDto.getCurrentPaymentStatus();
            return String.valueOf(currentPaymentStatus);
        }

        return null;
    }

    @Override
    public String getNextId() throws SQLException {
        String id = paymentAndInvoiceManageDao.getNextId();

        if (id != null) {
            String substring = id.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PI%03d", newIdIndex);
        }
        return "PI001";
    }

    @Override
    public boolean save(String paymentId, String patientId, String therapyProgramId, String therapySessionId, double amount, String status, double currentPayment, Date date) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            PatientDto patientDto = patientBo.findById(patientId); // Check if the patient exists

            if (patientDto == null) {
                transaction.rollback();
                return false;
            }

            Patient patient = new Patient();
            patient.setId(patientId);
            patient.setName(patientDto.getName());
            patient.setEmail(patientDto.getEmail());
            patient.setRegisterDate(patientDto.getRegisterDate());
            patient.setContact(patientDto.getContact());
            patient.setMedical_history(patientDto.getMedical_history());


            TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(therapyProgramId); // Check if the program exists

            if (therapyProgramDto == null) {
                transaction.rollback();
                return false;
            }

            TherapyProgram therapyProgram = new TherapyProgram();
            therapyProgram.setId(therapyProgramId);
            therapyProgram.setName(therapyProgramDto.getName());
            therapyProgram.setDescription(therapyProgramDto.getDescription());
            therapyProgram.setDuration(therapyProgramDto.getDuration());
            therapyProgram.setFee(therapyProgramDto.getFee());

            TherapySessionDto therapySessionDto = therapySessionBo.findById(therapySessionId); // Check if the session exists

            if (therapySessionDto == null) {
                transaction.rollback();
                return false;
            }

            TherapistDto therapistDto = therapistBo.findById(therapySessionDto.getTherapistsId()); // Check if the therapist exists

            if (therapistDto == null) {
                transaction.rollback();
                return false;
            }

            Therapist therapist = new Therapist();
            therapist.setId(therapySessionDto.getTherapistsId());
            therapist.setName(therapistDto.getName());
            therapist.setEmail(therapistDto.getEmail());
            therapist.setContact(therapistDto.getContact());

            TherapySession therapySession = new TherapySession();
            therapySession.setId(therapySessionDto.getId());
            therapySession.setTime(therapySessionDto.getTime());
            therapySession.setDate(therapySessionDto.getDate());
            therapySession.setStatus(therapySessionDto.getStatus());
            therapySession.setTherapyProgram(therapyProgram);
            therapySession.setTherapists(therapist);
            therapySession.setPatient(patient);

            Payment payment = new Payment();
            payment.setId(paymentId);
            payment.setAmount(amount);
            payment.setStatus(status);
            payment.setDate(date);
            payment.setPatient(patient);
            payment.setTherapyProgram(therapyProgram);
            payment.setTherapySession(therapySession);

            boolean isSavedPayment = paymentAndInvoiceManageDao.savePayment(session,payment); // Save the payment

            if (!isSavedPayment){
                transaction.rollback();
                return false;
            }

            double newCurrentPayment = currentPayment - amount; // Update the current payment

            ProgramDetails programDetails = new ProgramDetails();
            programDetails.setId(new ProgramDetailsId(therapyProgramId, patientId));
            programDetails.setTherapyProgram(therapyProgram);
            programDetails.setPatient(patient);
            programDetails.setCurrentPaymentStatus(newCurrentPayment);
            programDetails.setTherapyProgramName(therapyProgram.getName());

            if (amount > currentPayment) {
                transaction.rollback();
                return false;
            }

            boolean isUpdatedCurrentPayment = programDetailsBo.updateCurrentPayment(session, programDetails); // Update the ProgramDetails

            if (!isUpdatedCurrentPayment) {
                transaction.rollback();
                return false;
            }

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
