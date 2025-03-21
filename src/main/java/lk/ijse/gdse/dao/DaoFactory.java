package lk.ijse.gdse.dao;

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
        USER
    }

    public <T extends SuperDao> T getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return (T) new UserDaoImpl();
            default:
                return null;
        }
    }
}
