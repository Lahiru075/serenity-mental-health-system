package lk.ijse.gdse;

import lk.ijse.gdse.config.FactoryConfiguration;
import org.hibernate.Session;

public class AppInitializer {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.close();
    }
}
