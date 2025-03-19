module lk.ijse.gdse.serenity_mental_health_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;


    opens lk.ijse.gdse to javafx.fxml;
    exports lk.ijse.gdse;
}