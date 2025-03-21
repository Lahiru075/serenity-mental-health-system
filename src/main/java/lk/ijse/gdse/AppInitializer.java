package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.gdse.config.FactoryConfiguration;
import org.hibernate.Session;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.close();

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load =  FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(load);

        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.setResizable(false);
        stage.show();
    }
}
