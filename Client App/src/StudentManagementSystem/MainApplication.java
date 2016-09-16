package StudentManagementSystem;

import StudentManagementSystem.Stages.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.util.List;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Login login = new Login();
        login.display(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
