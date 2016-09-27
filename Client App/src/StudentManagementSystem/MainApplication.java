package StudentManagementSystem;

import StudentManagementSystem.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        DisplayMethods displayMethods = DisplayMethods.getInstance();
        displayMethods.LoginDisplay(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
