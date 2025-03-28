module lk.ijse.gdse.serenity_mental_health_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;

    requires java.naming;
    requires spring.security.crypto;
    requires spring.security.core;

    opens lk.ijse.gdse.dto.tm to javafx.base;
    opens lk.ijse.gdse.entity to org.hibernate.orm.core;
    opens lk.ijse.gdse.config to jakarta.persistence;
    opens lk.ijse.gdse.controller to javafx.fxml;
    opens lk.ijse.gdse to javafx.fxml;
    exports lk.ijse.gdse;
    opens lk.ijse.gdse.dto to javafx.base;
}