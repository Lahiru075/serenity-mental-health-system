package lk.ijse.gdse.bo;

import lk.ijse.gdse.bo.custom.impl.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }

        return boFactory;
    }

    public enum BOType {
        USER, THERAPIST_THERAPY_PROGRAM, PATIENT, PROGRAM_DETAILS, THERAPIST, THERAPY_PROGRAM, THERAPY_SESSION, PAYMENT, ENCRYPT
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperBo>T getBO(BOType boType) {
        switch (boType) {
            case USER:
                return (T) new UserBoImpl();
            case ENCRYPT:
                return (T) new EncryptAndDecryptImpl(new BCryptPasswordEncoder());
            case THERAPIST_THERAPY_PROGRAM:
                return (T) new TherapistTherapyProgramBoImpl();
//            case PATIENT:
//                return new PatientBOImpl();
//            case PROGRAM_DETAILS:
//                return new ProgramDetailsBOImpl();
            case THERAPIST:
                return (T) new TherapistBoImpl();
            case THERAPY_PROGRAM:
                return (T) new TherapyProgramBoImpl();
//            case THERAPY_SESSION:
//                return new TherapySessionBOImpl();
//            case PAYMENT:
//                return new PaymentBOImpl();
            default:
                return null;
        }
    }
}
