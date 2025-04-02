package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public enum DaoType {
        USER, THERAPIST, PROGRAM, PATIENT, SESSION, PROGRAM_DETAILS, QUERY
    }

    public <T extends SuperDao> T getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return (T) new UserDaoImpl();
            case THERAPIST:
                return (T) new TherapistDaoImpl();
            case PROGRAM:
                return (T) new TherapyProgramDaoImpl();
            case PATIENT:
                return (T) new PatientDaoImpl();
            case SESSION:
                return (T) new TherapySessionDaoImpl();
            case PROGRAM_DETAILS:
                return (T) new ProgramDetailsDaoImpl();
            case QUERY:
                return (T) new QueryDaoImpl();
            default:
                return null;
        }
    }
}
