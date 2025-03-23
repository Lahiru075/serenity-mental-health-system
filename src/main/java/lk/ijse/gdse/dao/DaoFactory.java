package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.custom.impl.TherapistDaoImpl;
import lk.ijse.gdse.dao.custom.impl.UserDaoImpl;

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
        USER,THERAPIST
    }

    public <T extends SuperDao> T getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return (T) new UserDaoImpl();
            case THERAPIST:
                return (T) new TherapistDaoImpl();
            default:
                return null;
        }
    }
}
