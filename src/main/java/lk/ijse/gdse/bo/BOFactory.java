package lk.ijse.gdse.bo;

import lk.ijse.gdse.bo.custom.TrackTherapyScheduleBo;
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
        USER, PROGRAM_DETAILS, PATIENT, THERAPIST, THERAPY_PROGRAM, SESSION, PAYMENT, ENCRYPT, TRACK_THERAPY_SCHEDULE, VIEW_PATIENT_PROGRAM, FILTER_PATIENT, VIEW_PATIENT_IN_ALL_PROGRAMS
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperBo>T getBO(BOType boType) {
        switch (boType) {
            case USER:
                return (T) new UserBoImpl();
            case ENCRYPT:
                return (T) new EncryptAndDecryptImpl(new BCryptPasswordEncoder());
            case PROGRAM_DETAILS:
                return (T) new ProgramDetailsBoImpl();
            case PATIENT:
                return (T) new PatientBoImpl();
            case THERAPIST:
                return (T) new TherapistBoImpl();
            case THERAPY_PROGRAM:
                return (T) new TherapyProgramBoImpl();
            case SESSION:
                return (T) new TherapySessionBoImpl();
            case TRACK_THERAPY_SCHEDULE:
                return (T) new TrackTherapyScheduleBoImpl();
            case VIEW_PATIENT_PROGRAM:
                return (T) new ViewPatientProgramBoImpl();
            case FILTER_PATIENT:
                return (T) new FilterPatientBoImpl();
            case VIEW_PATIENT_IN_ALL_PROGRAMS:
                return (T) new ViewPatientInAllProgramsBoImpl();
//            case PAYMENT:
//                return new PaymentBOImpl();
            default:
                return null;
        }
    }
}
